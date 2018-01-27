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
import org.uniworks.groupware.admin.domain.Cm001c;
import org.uniworks.groupware.admin.domain.Cm002c;
import org.uniworks.groupware.admin.domain.Cm003m;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.service.Cm001cService;
import org.uniworks.groupware.admin.service.Cm002cService;
import org.uniworks.groupware.admin.service.Cm003mService;
import org.uniworks.groupware.admin.service.Cm010cService;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;

/**
 * @author Park Chung Wan
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class CodeMgrController {
	private static final Logger logger = LoggerFactory.getLogger(CodeMgrController.class);
	@Autowired Hr001mService hr001mService;
	@Autowired CommonService commonService;
	@Autowired Cm001cService cm001cService;
	@Autowired Cm002cService cm002cService;
	@Autowired Cm010cService cm010cService;
	@Autowired Cm003mService cm003mService;
	
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
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
		
		mav.addObject("langList", langList);
		return mav;
	}
	
	/**
	 * 주코드 수정 폼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/codeMgr/masterCodeModifyForm", method = RequestMethod.GET)
	public ModelAndView codeMasterModifyForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String majCode = StringUtil.null2void(request.getParameter("majCode"));
		ModelAndView mav = new ModelAndView("code/master_code_modify_form_01");
	
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);		
		map.put("majCode", majCode);
		Cm001c cm001c = cm001cService.getCm001c(map);			
		
		//다국어로 등록된 명칭 목록을 가져온다.
		Map<String, Object> multiLangMap = new HashMap<String, Object>();
		multiLangMap.put("coId", coId);
		multiLangMap.put("rescKey", majCode);
		List<Cm003m> cm003mList = cm003mService.getCm003mList(multiLangMap);		
		
		mav.addObject("cm001c", cm001c);
		mav.addObject("cm003mList", cm003mList);
		return mav;
	}
	
	/**
	 * 부코드 등록 폼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/codeMgr/subCodeAddForm", method = RequestMethod.GET)
	public ModelAndView subCodeAddForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String majCode = StringUtil.null2void(request.getParameter("majCode"));
		ModelAndView mav = new ModelAndView("code/sub_code_add_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
		
		mav.addObject("langList", langList);
		mav.addObject("coId", coId);
		mav.addObject("majCode", majCode);
		return mav;
	}
	
	/**
	 * 부코드 수정 폼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/codeMgr/subCodeModifyForm", method = RequestMethod.GET)
	public ModelAndView subCodeModifyForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String majCode = StringUtil.null2void(request.getParameter("majCode"));
		String subCode = StringUtil.null2void(request.getParameter("subCode"));
		ModelAndView mav = new ModelAndView("code/sub_code_modify_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", majCode); 
		map.put("subCode", subCode);
		map.put("lang", userSession.getLang());
		
		Cm002c cm002c = cm002cService.getCm002c(map);			
		
		map.put("rescKey", cm002c.getRescKey());
		List<Cm003m> cm003mList = cm003mService.getCm003mList(map);	
		
		mav.addObject("cm002c", cm002c);		
		mav.addObject("cm003mList", cm003mList);
		return mav;
	}
}
