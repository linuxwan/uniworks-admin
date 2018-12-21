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
import org.uniworks.groupware.admin.common.util.DateUtil;
import org.uniworks.groupware.admin.common.util.SecurityUtil;
import org.uniworks.groupware.admin.common.util.StringUtil;
import org.uniworks.groupware.admin.domain.ApprovalMasterInfo;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.domain.Nw011m;
import org.uniworks.groupware.admin.domain.Nw013m;
import org.uniworks.groupware.admin.service.ApprovalMasterService;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;
import org.uniworks.groupware.admin.service.Nw010mService;
import org.uniworks.groupware.admin.service.Nw011mService;
import org.uniworks.groupware.admin.service.Nw012mService;
import org.uniworks.groupware.admin.service.Nw015mService;

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
	@Autowired ApprovalMasterService apprMstService;
	@Autowired Nw010mService nw010mService;
	@Autowired Nw011mService nw011mService;
	@Autowired Nw012mService nw012mService;
	@Autowired Nw015mService nw015mService;
	
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
	
	/**
	 * 결재 마스터 등록 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/apprMasterMgr/apprMasterAddForm", method = RequestMethod.GET)
	public ModelAndView apprMasterAddForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		ModelAndView mav = new ModelAndView("apprMaster/appr_master_add_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
		
		map.put("majCode",  "CD008"); //문서 유효기간을 코드성 정보에서 가져온다.
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> prsvTermList = commonService.getCommonSubCodeList(map);
		
		map.put("majCode", "CD013");	//협조결재 유형
		List<CommonCode> cprtnTypeList = commonService.getCommonSubCodeList(map);
		
		List<Nw013m> apprItemList = apprMstService.getApprTypeList(map);
		
		mav.addObject("coId", coId);
		mav.addObject("langList", langList);
		mav.addObject("prsvTermList", prsvTermList);
		mav.addObject("cprtnTypeList", cprtnTypeList);
		mav.addObject("apprItemList", apprItemList);
		
		return mav;
	}
	
	/**
	 * 결재 마스터 수정 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/apprMasterMgr/apprMasterModifyForm", method = RequestMethod.GET)
	public ModelAndView apprMasterModifyForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String apprMstId = StringUtil.null2void(request.getParameter("apprMstId"));
		ModelAndView mav = new ModelAndView("apprMaster/appr_master_modify_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap.put("coId", coId);
		tmpMap.put("apprMstId", apprMstId);
		tmpMap.put("crntDate", DateUtil.getCurrentDateToString());
		
		ApprovalMasterInfo apprMstInfo = apprMstService.getApprMasterInfo(tmpMap);		
		
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
		
		ArrayList<Nw011m> arrList = new ArrayList<Nw011m>();
		for (CommonCode commonCode : langList) {
			tmpMap.put("locale", commonCode.getRescKeyValue());
			
			Nw011m nw011m = nw011mService.getNw011m(tmpMap);			
			arrList.add(nw011m);
		}
		
		map.put("majCode",  "CD008"); //문서 유효기간을 코드성 정보에서 가져온다.
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> prsvTermList = commonService.getCommonSubCodeList(map);
		
		map.put("majCode", "CD013");	//협조결재 유형
		List<CommonCode> cprtnTypeList = commonService.getCommonSubCodeList(map);
		
		List<Nw013m> apprItemList = apprMstService.getApprTypeList(map);
						
		mav.addObject("prsvTermList", prsvTermList);
		mav.addObject("cprtnTypeList", cprtnTypeList);
		mav.addObject("apprItemList", apprItemList);
		mav.addObject("apprMstInfo", apprMstInfo);
		mav.addObject("nw011mList", arrList);		
		
		return mav;
	}
}
