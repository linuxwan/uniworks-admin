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
import org.uniworks.groupware.admin.common.util.StringUtil;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.service.CommonService;

/**
 * @author Park Chung Wan
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class GroupMgrController {
	private static final Logger logger = LoggerFactory.getLogger(GroupMgrController.class);
	@Autowired CommonService commonService;
	
	/**
	 * 그룹 검색 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/groupMgr/searchGroupForm", method = RequestMethod.GET)
	public ModelAndView searchGroupForm(HttpServletRequest request, HttpServletResponse response) {		
		ModelAndView mav = new ModelAndView("groupMgr/group_search_form_01");
		String targetObj = StringUtil.null2void(request.getParameter("targetObj"));
		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
				
		Map<String, Object> map = new HashMap<String, Object>();
		//지원 언어 목록 체크
		map.put("coId", userSession.getCoId());
		map.put("lang", userSession.getLang());
		map.put("majCode", "CD020"); //그룹 검색 항목(CD020)
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> searchTypeList = commonService.getCommonSubCodeList(map);	
		
		mav.addObject("searchTypeList", searchTypeList);
		mav.addObject("targetObj", targetObj);
		return mav;
	}
}
