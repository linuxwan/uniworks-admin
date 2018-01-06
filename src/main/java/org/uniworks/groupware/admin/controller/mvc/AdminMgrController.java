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
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.service.Hr001mService;

/**
 * @author Park Chung Wan
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminMgrController {
	private static final Logger logger = LoggerFactory.getLogger(AdminMgrController.class);
	@Autowired Hr001mService hr001mService;
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
		
		mav.addObject("userSession", userSession);
		mav.addObject("coList", coList);
		mav.addObject("adminType", adminType);
		return mav;
	}
}
