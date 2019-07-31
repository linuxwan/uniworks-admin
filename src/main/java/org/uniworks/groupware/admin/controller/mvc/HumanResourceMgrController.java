/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.controller.mvc;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
import org.uniworks.groupware.admin.common.util.SecurityUtil;
import org.uniworks.groupware.admin.common.util.StringUtil;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.domain.HumanResource;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;
import org.uniworks.groupware.admin.service.HumanResourceService;

/**
 * @author Park Chung Wan
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class HumanResourceMgrController {
	private static final Logger logger = LoggerFactory.getLogger(HumanResourceMgrController.class);
	
	@Autowired CommonService commonService;
	@Autowired Hr001mService hr001mService;
	@Autowired HumanResourceService hrResourceService;
	
	/**
	 * 인사 관리
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hrMgr", method = RequestMethod.GET)
	public ModelAndView humanResourceMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("hr/human_resource_mgr_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String adminType = SecurityUtil.getAuthority();
		
		Map<String, Object> map = new HashMap<String, Object>();
		//지원 언어 목록 체크
		map.put("coId", userSession.getCoId());
		map.put("lang", userSession.getLang());
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
				
		map.put("adminType", adminType);
		List<Hr001m> coList = hr001mService.getHr001mList(map);
		
		mav.addObject("coList", coList);
		mav.addObject("langList", langList);

		return mav;		
	}
	
	/**
	 * 조직 구성원을 등록하기 위한 화면을 호출한다.
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/hrAddForm", method = RequestMethod.GET)
	public ModelAndView humanResourceAddForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("hr/human_resource_add_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String oganLev = StringUtil.null2void(request.getParameter("oganLev"));
		String oganCode = StringUtil.null2void(request.getParameter("oganCode"));
		String oganDesc = StringUtil.null2void(request.getParameter("oganDesc"));
		//근무자유형 정보를 일반코드에서 가져온다. MAJ_CODE : CD003
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", "CD003"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> workIndcList = commonService.getCommonSubCodeList(map);
		
		//직위 정보를 일반코드에서 가져온다. MAJ_CODE : CD004
		map.put("majCode", "CD004");
		List<CommonCode> dutyList = commonService.getCommonSubCodeList(map);
		
		//보직 정보를 일반코드에서 가져온다. MAJ_CODE : CD006
		map.put("majCode", "CD006");
		List<CommonCode> pstnList = commonService.getCommonSubCodeList(map);
		
		//결혼구분 정보를 일반코드에서 가져온다. MAJ_CODE : CD005
		map.put("majCode", "CD005");
		List<CommonCode> mrgList = commonService.getCommonSubCodeList(map);
		
		mav.addObject("workIndcList", workIndcList);
		mav.addObject("dutyList", dutyList);
		mav.addObject("pstnList", pstnList);
		mav.addObject("mrgList", mrgList);
		mav.addObject("coId", coId);
		mav.addObject("oganLev", oganLev);
		mav.addObject("oganCode", oganCode);
		mav.addObject("oganDesc", oganDesc);
				
		return mav;
	}
	
	/**
	 * 조직 구성원을 수정하기 위한 화면을 호출한다.
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/hrModifyForm", method = RequestMethod.GET)
	public ModelAndView humanResourceModifyForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("hr/human_resource_modify_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String empNo = StringUtil.null2void(request.getParameter("empNo"));
		
		//근무자유형 정보를 일반코드에서 가져온다. MAJ_CODE : CD003
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", "CD003"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> workIndcList = commonService.getCommonSubCodeList(map);
		
		//직위 정보를 일반코드에서 가져온다. MAJ_CODE : CD004
		map.put("majCode", "CD004");
		List<CommonCode> dutyList = commonService.getCommonSubCodeList(map);
		
		//보직 정보를 일반코드에서 가져온다. MAJ_CODE : CD006
		map.put("majCode", "CD006");
		List<CommonCode> pstnList = commonService.getCommonSubCodeList(map);
		
		//결혼구분 정보를 일반코드에서 가져온다. MAJ_CODE : CD005
		map.put("majCode", "CD005");
		List<CommonCode> mrgList = commonService.getCommonSubCodeList(map);
		
		//직원 정보를 가져온다.(Hr010m)
		map.put("lang", userSession.getLang());
		map.put("empNo", empNo);
		HumanResource hr = hrResourceService.getByHumanResource(map);
		
		mav.addObject("workIndcList", workIndcList);
		mav.addObject("dutyList", dutyList);
		mav.addObject("pstnList", pstnList);
		mav.addObject("mrgList", mrgList);		
		mav.addObject("hr", hr);
				
		return mav;
	}
}
