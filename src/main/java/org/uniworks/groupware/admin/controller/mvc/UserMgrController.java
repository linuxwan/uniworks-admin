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
import org.uniworks.groupware.admin.domain.Nw100m;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;
import org.uniworks.groupware.admin.service.HumanResourceService;
import org.uniworks.groupware.admin.service.Nw100mService;

/**
 * @author Park Chung Wan
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class UserMgrController {
	private static final Logger logger = LoggerFactory.getLogger(UserMgrController.class);
	
	@Autowired CommonService commonService;
	@Autowired Hr001mService hr001mService;
	@Autowired HumanResourceService hrResourceService;
	@Autowired Nw100mService nw100mService;
	
	/**
	 * 사용자 목록 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userMgr", method = RequestMethod.GET)
	public ModelAndView userMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("user/user_mgr_01");
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
	 * 사용자 등록 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/userAddForm", method = RequestMethod.GET)
	public ModelAndView userAddForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("user/user_add_form_01");
		String coId = StringUtil.null2void(request.getParameter("coId"));
		
		mav.addObject("coId", coId);
		return mav;
	}
	
	/**
	 * 사용자 정보 수정 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/userModifyForm", method = RequestMethod.GET)
	public ModelAndView userModifyForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("user/user_modify_form_01");
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String userId = StringUtil.null2void(request.getParameter("userId"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("userId", userId);
		
		Nw100m nw100m = nw100mService.getNw100m(map);
		
		mav.addObject("nw100m", nw100m);
		return mav;
	}
	
	/**
	 * 사용자 검색 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userSearchForm", method = RequestMethod.GET)
	public ModelAndView userSearchForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("user/user_search_form_01");		
		String userId = StringUtil.null2void(request.getParameter("userId"));
		String userName = StringUtil.null2void(request.getParameter("userName"));
		String targetObj = StringUtil.null2void(request.getParameter("targetObj"));
		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
				
		Map<String, Object> map = new HashMap<String, Object>();
		//지원 언어 목록 체크
		map.put("coId", userSession.getCoId());
		map.put("lang", userSession.getLang());
		map.put("majCode", "CD019"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> sortTypeList = commonService.getCommonSubCodeList(map);	
		
		mav.addObject("searchTypeList", sortTypeList);
		mav.addObject("userSession", userSession);
		mav.addObject("userId", userId);
		mav.addObject("userName", userName);
		mav.addObject("targetObj", targetObj);
		return mav;
	}
}
