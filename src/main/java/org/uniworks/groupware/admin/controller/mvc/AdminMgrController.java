/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.controller.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
import org.uniworks.groupware.admin.common.util.SecurityUtil;
import org.uniworks.groupware.admin.common.util.StringUtil;
import org.uniworks.groupware.admin.domain.Cm010c;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.service.Cm010cService;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminMgrController {
	private static final Logger logger = LoggerFactory.getLogger(AdminMgrController.class);
	@Autowired Hr001mService hr001mService;
	@Autowired CommonService commonService;
	@Autowired Cm010cService cm010cService;
	/**
	 * 회사 관리
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/companyMgr", method = RequestMethod.GET)
	public ModelAndView companyMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("company/company_mgr_01");
		
		return mav;
	}
	
	/**
	 * 관리자 관리 (시스템관리자, 회사별 관리자를 위한 페이지)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/adminMgr", method = RequestMethod.GET)
	public ModelAndView adminMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("admin/admin_mgr_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		mav.addObject("userSession", userSession);
		return mav;
	}
	
	/**
	 * 관리자 등록화면을 호출한다.
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/adminAddForm", method = RequestMethod.GET)
	public ModelAndView adminAddForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("admin/admin_add_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		//로그인한 사용자의 UserName과 Authority 정보를 가져온다.
		String coId = SecurityUtil.getUserName().substring(0,  4);
		String adminType = SecurityUtil.getAuthority();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adminType", adminType);
		map.put("coId",  coId);
		List<Hr001m> coList = hr001mService.getHr001mList(map);
		
		//공통 코드에서 관리자 유형을 가져온다.
		map.put("majCode", "CD000");
		map.put("lang", userSession.getLang());
		map.put("orderBy", "rescKey");
		List<CommonCode> codeList = commonService.getCommonSubCodeList(map);

		//관리자 유형 목록을 정리
		codeList = getGrantedAdminTypeList(adminType, codeList);
		
		mav.addObject("userSession", userSession);
		mav.addObject("coList", coList);
		mav.addObject("adminType", adminType);
		mav.addObject("codeList", codeList);
		return mav;
	}
	
	/**
	 * 관리자 수정화면을 호출한다.
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/adminModifyForm", method = RequestMethod.GET)
	public ModelAndView adminModifyForm(HttpServletRequest request, HttpServletResponse response) {		
		ModelAndView mav = new ModelAndView("admin/admin_modify_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
			
		//로그인한 사용자의 UserName과 Authority 정보를 가져온다.
		String coId = StringUtil.null2void(request.getParameter("coId"));					
		String adminId = StringUtil.null2void(request.getParameter("adminId"));
		String adminType = SecurityUtil.getAuthority();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("adminId", adminId);
		
		Cm010c cm010c = cm010cService.getCm010c(map);		
		
		//공통 코드에서 관리자 유형을 가져온다.
		map.put("majCode", "CD000");
		map.put("lang", userSession.getLang());
		map.put("orderBy", "rescKey");
		List<CommonCode> codeList = commonService.getCommonSubCodeList(map);

		//관리자 유형 목록을 정리
		codeList = getGrantedAdminTypeList(adminType, codeList);
				
		mav.addObject("cm010c", cm010c);
		mav.addObject("codeList", codeList);
		return mav;
	}
	
	/**
	 * 로그인한 관리자의 타입에 따라 선택할 수 있는 관리자 유형 목록을 재설정한다.
	 * @param adminType
	 * @param codeList
	 * @return
	 */
	private List<CommonCode> getGrantedAdminTypeList(String adminType, List<CommonCode> codeList) {
		ArrayList<CommonCode> selCodeList = new ArrayList<CommonCode>();
		
		for (int i = 0; i < codeList.size(); i++) {
			CommonCode code = codeList.get(i);
			if (adminType.equalsIgnoreCase("GRP_ADM")) {	//그룹 관리자의 경우 SYS_ADM(시스템 관리자)를 제외하고 목록을 구성
				if (!code.getSubCode().equalsIgnoreCase("SYS_ADM")) {
					selCodeList.add(code);
				}
			} else if (adminType.equalsIgnoreCase("CNT_ADM")) {	//컨텐츠 관리자의 경우 SYS_ADM(시스템 관리자), GRP_ADM(그룹 관리자)를 제외하고 목록을 구성
				if (code.getSubCode().equalsIgnoreCase("CNT_ADM")) {
					selCodeList.add(code);
				}
			} else {	//시스템 관리자(SYS_ADM) 일 경우 그대로 리턴
				return codeList;
			}
		}
		
		return selCodeList;
	}
	
	/**
	 * 코드 관리
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/codeMgr", method = RequestMethod.GET)
	public ModelAndView codeMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("code/code_mgr_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		//로그인한 사용자의 coId와 Authority(관리자유형) 정보를 가져온다.
		String coId = userSession.getCoId();					
		String adminType = SecurityUtil.getAuthority();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adminType", adminType);
		map.put("coId",  coId);
		List<Hr001m> coList = hr001mService.getHr001mList(map);
		
		//지원 언어 목록 체크
		map.put("lang", userSession.getLang());
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
		
		mav.addObject("coList", coList);
		mav.addObject("userSession", userSession);
		mav.addObject("langList", langList);
		return mav;
	}
	
	/**
	 * 주코드 입력 폼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/codeMgr/masterCodeAddForm", method = RequestMethod.GET)
	public ModelAndView codeMasterAddForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		ModelAndView mav = new ModelAndView("code/master_code_add_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
		
		mav.addObject("langList", langList);
		return mav;
	}
}
