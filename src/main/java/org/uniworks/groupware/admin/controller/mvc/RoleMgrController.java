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
import org.uniworks.groupware.admin.domain.Nw105m;
import org.uniworks.groupware.admin.domain.Nw106m;
import org.uniworks.groupware.admin.domain.Nw107m;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;
import org.uniworks.groupware.admin.service.Nw105mService;
import org.uniworks.groupware.admin.service.Nw106mService;
import org.uniworks.groupware.admin.service.Nw107mService;

/**
 * @author Park Chung Wan
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class RoleMgrController {
	private static final Logger logger = LoggerFactory.getLogger(RoleMgrController.class);
	@Autowired CommonService commonService;
	@Autowired Hr001mService hr001mService;
	@Autowired Nw106mService nw106mService;
	@Autowired Nw107mService nw107mService;
	@Autowired Nw105mService nw105mService;
	
	/**
	 * Role 목록 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/roleMgr", method = RequestMethod.GET)
	public ModelAndView roleMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("roleMgr/role_mgr_01");
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
	 * Role 등록 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/roleMgr/roleAddForm", method = RequestMethod.GET)
	public ModelAndView roleAddForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		ModelAndView mav = new ModelAndView("roleMgr/role_add_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);	
				
		mav.addObject("coId", coId);
		mav.addObject("langList", langList);
		return mav;
	}
	
	/**
	 * Role 수정화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/roleMgr/roleModifyForm", method = RequestMethod.GET)
	public ModelAndView roleModifyForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String role = StringUtil.null2void(request.getParameter("role"));
		ModelAndView mav = new ModelAndView("roleMgr/role_modify_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("role", role);
		
		Nw106m nw106m = nw106mService.getNw106m(map);
		List<Nw107m> nw107mList = nw107mService.getNw107mList(map);			
				
		mav.addObject("nw106m", nw106m);
		mav.addObject("nw107mList", nw107mList);
		return mav;
	}
	
	/**
	 * Role별 사용자 관리
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/roleMgr/roleUserManagerForm", method = RequestMethod.GET)
	public ModelAndView roleUserMangerForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("roleMgr/role_user_mgr_form_01");
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
	 * 사용자 Role 등록 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/roleMgr/roleUserRegistrationForm", method = RequestMethod.GET)
	public ModelAndView userRoleRegistrationForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String role = StringUtil.null2void(request.getParameter("role"));
		String roleName = StringUtil.null2void(request.getParameter("roleName"));
		
		ModelAndView mav = new ModelAndView("roleMgr/user_role_add_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		mav.addObject("coId", coId);
		mav.addObject("role", role);
		mav.addObject("roleName", roleName);
		return mav;
	}
	
	/**
	 * 사용자 Role 수정 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/roleMgr/roleUserModifyForm", method = RequestMethod.GET)
	public ModelAndView userRoleModifyForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String role = StringUtil.null2void(request.getParameter("role"));
		String userId = StringUtil.null2void(request.getParameter("userId"));
		String roleName = StringUtil.null2void(request.getParameter("roleName"));
		String empName = StringUtil.null2void(request.getParameter("empName"));
		
		ModelAndView mav = new ModelAndView("roleMgr/user_role_modify_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("role", role);
		map.put("userId", userId);
		
		Nw105m nw105m = nw105mService.getNw105m(map);
		
		mav.addObject("coId", coId);
		mav.addObject("roleName", roleName);
		mav.addObject("userName", empName);
		if (nw105m != null) mav.addObject("nw105m", nw105m);
		return mav;
	}
	
	/**
	 * Role 검색 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/roleMgr/roleSearchForm", method = RequestMethod.GET)
	public ModelAndView roleSearchForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("roleMgr/role_search_form_01");
		String targetObj = StringUtil.null2void(request.getParameter("targetObj"));
		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
				
		Map<String, Object> map = new HashMap<String, Object>();
		//지원 언어 목록 체크
		map.put("coId", userSession.getCoId());
		map.put("lang", userSession.getLang());
		map.put("majCode", "CD021"); //그룹 검색 항목(CD020)
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> searchTypeList = commonService.getCommonSubCodeList(map);	
		
		mav.addObject("searchTypeList", searchTypeList);
		mav.addObject("targetObj", targetObj);
		return mav;
	}
}
