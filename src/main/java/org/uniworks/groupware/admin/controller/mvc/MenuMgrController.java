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
import org.uniworks.groupware.admin.domain.MenuInfo;
import org.uniworks.groupware.admin.domain.Nw021m;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;
import org.uniworks.groupware.admin.service.MenuService;
import org.uniworks.groupware.admin.service.Nw021mService;

/**
 * @author Park Chung Wan
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class MenuMgrController {
	private static final Logger logger = LoggerFactory.getLogger(MenuMgrController.class);
	@Autowired Hr001mService hr001mService;
	@Autowired CommonService commonService;
	@Autowired MenuService menuService;
	@Autowired Nw021mService nw021mService;
	
	/**
	 * Menu 관리 화면 호출
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/menuMgr", method = RequestMethod.GET)
	public ModelAndView menuMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("menuMgr/menu_mgr_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String adminType = SecurityUtil.getAuthority();
		
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId", userSession.getCoId());
		map.put("lang", userSession.getLang());		
		map.put("adminType", adminType);
		List<Hr001m> coList = hr001mService.getHr001mList(map);
				
		//지원 언어 목록 체크		
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);		
		
		mav.addObject("coList", coList);
		mav.addObject("coId", userSession.getCoId());
		mav.addObject("langList", langList);
		return mav;
	}
	
	/**
	 * Menu 추가 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/menuMgr/menuAddForm", method = RequestMethod.GET)
	public ModelAndView menuMgrMenuAddForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("menuMgr/menu_add_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String coId = StringUtil.null2void(request.getParameter("coId"));			
		String highMenuId = StringUtil.null2void(request.getParameter("highMenuId"));			
		String menuLevel = StringUtil.null2void(request.getParameter("menuLevel"));
		
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
		
		map.put("majCode", "CD014");
		List<CommonCode> linkTypeList = commonService.getCommonSubCodeList(map);
						
		mav.addObject("langList", langList);				
		mav.addObject("coId", coId);
		mav.addObject("highMenuId", highMenuId);
		mav.addObject("menuLevel", menuLevel);
		mav.addObject("linkTypeList", linkTypeList);
		return mav;
	}
	
	/**
	 * 메뉴 수정 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/menuMgr/menuModifyForm", method = RequestMethod.GET)
	public ModelAndView menuMgrMenuModifyForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("menuMgr/menu_modify_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String coId = StringUtil.null2void(request.getParameter("coId"));			
		String menuId = StringUtil.null2void(request.getParameter("menuId"));					
		
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("menuId", menuId);
		map.put("coId", coId);		
		List<Nw021m> langList = nw021mService.getNw021mList(map);
		
		map.put("majCode", "CD014");
		List<CommonCode> linkTypeList = commonService.getCommonSubCodeList(map);

		map.put("menuId", menuId);
		MenuInfo menuInfo = menuService.selectByMenuId(map);
		
		mav.addObject("langList", langList);				
		mav.addObject("coId", coId);
		mav.addObject("menuId", menuId);
		mav.addObject("menuInfo", menuInfo);
		mav.addObject("linkTypeList", linkTypeList);
		return mav;
	}
}
