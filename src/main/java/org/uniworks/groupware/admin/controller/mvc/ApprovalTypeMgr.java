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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
import org.uniworks.groupware.admin.common.util.ApplicationConfigReader;
import org.uniworks.groupware.admin.common.util.DateUtil;
import org.uniworks.groupware.admin.common.util.SecurityUtil;
import org.uniworks.groupware.admin.common.util.StringUtil;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.domain.Nw013m;
import org.uniworks.groupware.admin.domain.Nw014m;
import org.uniworks.groupware.admin.service.ApprovalMasterService;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;
import org.uniworks.groupware.admin.service.Nw014mService;

/**
 * @author Park Chung Wan
 * 결재 유형별로 분류한다.
 * 결재 양식함에 Display하기 위해서 관리.
 */
@Controller
@RequestMapping(value = "/admin")
public class ApprovalTypeMgr {
	private static final Logger logger = LoggerFactory.getLogger(ApprovalTypeMgr.class);
	@Autowired CommonService commonService;
	@Autowired Hr001mService hr001mService;
	@Autowired ApprovalMasterService apprMstService;
	@Autowired Nw014mService nw014mService;
	
	/**
	 * 결재 마스트 목록
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/apprTypeMgr", method = RequestMethod.GET)
	public ModelAndView apprTypeMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("apprType/appr_type_mgr_01");
		
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
	 * 결재유형 입력 폼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/apprTypeMgr/apprTypeAddForm", method = RequestMethod.GET)
	public ModelAndView apprTypeAddForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		ModelAndView mav = new ModelAndView("apprType/appr_type_add_form_01");
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
	 * 결재유형 수정 폼
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/apprTypeMgr/apprTypeModifyForm", method = RequestMethod.GET)
	public ModelAndView apprTypeModifyForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("apprType/appr_type_modify_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String apprItemId = StringUtil.null2void(request.getParameter("apprItemId"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("apprItemId", apprItemId);
		
		Nw013m nw013m = apprMstService.getApprTypeInfo(map);
		nw013m.setCreateDate(DateUtil.getDateFormat(nw013m.getCreateDate(), "-"));
		
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", coId);
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
				
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		
		ArrayList<Nw014m> arrList = new ArrayList<Nw014m>();
		for (CommonCode commonCode : langList) {
			map.put("locale", commonCode.getRescKeyValue());
			
			Nw014m nw014m = nw014mService.getNw014m(map);			
			arrList.add(nw014m);
		}
		
		mav.addObject("nw013m", nw013m);
		mav.addObject("nw014mList", arrList);
		return mav;
	}
}
