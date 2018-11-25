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
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;

/**
 * @author Park Chung Wan
 * 결재 마스터 정보를 관리하기 위한 컨트롤 클래스 (JSP파일과 연동)
 * 결재 마스터 정보의 목록/등록/수정/조회 화면을 제공하는 클래스
 */
@Controller
@RequestMapping(value = "/admin")
public class ApprMasterMgrController {
	private static final Logger logger = LoggerFactory.getLogger(ApprMasterMgrController.class);
	@Autowired CommonService commonService;
	@Autowired Hr001mService hr001mService;
	/**
	 * 결재 마스트 목록
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/apprMasterMgr", method = RequestMethod.GET)
	public ModelAndView approvalMasterMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("apprMaster/appr_master_mgr_01");
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
}
