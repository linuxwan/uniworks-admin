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
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;

/**
 * @author Park Chung Wan
 * 회사별 조직 정보를 관리한다.
 */
@Controller
@RequestMapping(value = "/admin")
public class OganMgrController {
	private static final Logger logger = LoggerFactory.getLogger(OganMgrController.class);
	
	@Autowired CommonService commonService;
	@Autowired Hr001mService hr001mService;
	
	/**
	 * 조직 관리
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/oganMgr", method = RequestMethod.GET)
	public ModelAndView oganMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("ogan/ogan_mgr_01");
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
		
		//조직유형 가져오기
		map.put("majCode", "CD002");
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> oganTypeList = commonService.getCommonSubCodeList(map);
		
		map.put("adminType", adminType);
		List<Hr001m> coList = hr001mService.getHr001mList(map);
		
		mav.addObject("coList", coList);
		mav.addObject("langList", langList);
		mav.addObject("oganTypeList", oganTypeList);
		return mav;
	}
	
	/**
	 * 조직 선택 Popup 창
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/selectOgan", method = RequestMethod.GET)
	public ModelAndView selectOgan(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("ogan/ogan_select_popup_01");
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String targetObj = StringUtil.null2void(request.getParameter("targetObj"));
		
		mav.addObject("coId", coId);
		mav.addObject("targetObj", targetObj);
		return mav;
	}
}
