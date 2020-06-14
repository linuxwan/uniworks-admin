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
import org.uniworks.groupware.admin.common.util.SecurityUtil;
import org.uniworks.groupware.admin.common.util.StringUtil;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.ContentInfo;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.domain.Nw031m;
import org.uniworks.groupware.admin.domain.Nw032m;
import org.uniworks.groupware.admin.domain.Nw033m;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.ContentService;
import org.uniworks.groupware.admin.service.Hr001mService;
import org.uniworks.groupware.admin.service.Nw031mService;
import org.uniworks.groupware.admin.service.Nw032mService;
import org.uniworks.groupware.admin.service.Nw033mService;

/**
 * @author Park Chung Wan
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class ContentsMgrController {
	private static final Logger logger = LoggerFactory.getLogger(ContentsMgrController.class);
	@Autowired CommonService commonService;
	@Autowired Hr001mService hr001mService;
	@Autowired Nw031mService nw031mService;
	@Autowired Nw032mService nw032mService;
	@Autowired Nw033mService nw033mService;
	@Autowired ContentService contentService;		
	
	/**
	 * 컨텐츠 관리 목록
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentsMgr", method = RequestMethod.GET)
	public ModelAndView contentsMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("contentsMgr/contents_mgr_01");
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
	 * 컨텐츠 조회
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentsMgr/contentRetrieveForm", method = RequestMethod.GET)
	public ModelAndView contentRetrieveForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String cntnId = StringUtil.null2void(request.getParameter("cntnId"));
		ModelAndView mav = new ModelAndView("contentsMgr/content_retrieve_form_01");
		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("cntnId", cntnId);
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		
		ContentInfo contentInfo = contentService.getContentInfo(map);
		
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
		
		ArrayList<Nw031m> nw031mList = new ArrayList<Nw031m>();
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap.put("coId", coId);
		tmpMap.put("cntnId", cntnId);
		for (CommonCode commonCode : langList) {
			tmpMap.put("locale", commonCode.getRescKeyValue());
			
			Nw031m nw031m = nw031mService.getNw031m(tmpMap);			
			nw031mList.add(nw031m);
		}			
		
		map.put("majCode", "CD009"); //컨텐츠 제공 유형
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> cntnOfferTypeList = commonService.getCommonSubCodeList(map);
		
		map.put("majCode", "CD011"); //컨텐츠 유형
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> cntnTypeList = commonService.getCommonSubCodeList(map);
		
		List<Hr001m> companyList = hr001mService.getGroupCompanyListAll(map);
		
		List<Nw032m> nw032mList = nw032mService.getNw032mList(tmpMap);
		String nw032ms = "";
		int i = 0;
		for (Nw032m nw032m : nw032mList) {
			if (i == 0) nw032ms = nw032m.getCoId();
			else nw032ms += "," + nw032m.getCoId();
			i++;
		}
		
		mav.addObject("cntnOfferTypeList", cntnOfferTypeList);
		mav.addObject("contentInfo", contentInfo);
		mav.addObject("nw031mList", nw031mList);
		mav.addObject("cntnTypeList", cntnTypeList);
		mav.addObject("companyList", companyList);
		mav.addObject("nw032ms", nw032ms);
		return mav;
	}
	
	/**
	 * 컨텐츠 등록 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentsMgr/contentAddForm", method = RequestMethod.GET)
	public ModelAndView contentAddForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		ModelAndView mav = new ModelAndView("contentsMgr/content_add_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);	

		map.put("majCode", "CD009"); //컨텐츠 제공 유형
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> cntnOfferTypeList = commonService.getCommonSubCodeList(map);
		
		map.put("majCode", "CD011"); //컨텐츠 유형
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> cntnTypeList = commonService.getCommonSubCodeList(map);
		
		List<Hr001m> companyList = hr001mService.getGroupCompanyListAll(map);
		
		mav.addObject("coId", coId);
		mav.addObject("langList", langList);
		mav.addObject("cntnOfferTypeList", cntnOfferTypeList);
		mav.addObject("cntnTypeList", cntnTypeList);
		mav.addObject("companyList", companyList);
		return mav;
	}
	
	/**
	 * 컨텐츠 수정화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentsMgr/contentModifyForm", method = RequestMethod.GET)
	public ModelAndView contentModifyForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String cntnId = StringUtil.null2void(request.getParameter("cntnId"));
		ModelAndView mav = new ModelAndView("contentsMgr/content_modify_form_01");
		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("cntnId", cntnId);
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		
		ContentInfo contentInfo = contentService.getContentInfo(map);
		
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
		
		ArrayList<Nw031m> nw031mList = new ArrayList<Nw031m>();
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap.put("coId", coId);
		tmpMap.put("cntnId", cntnId);
		for (CommonCode commonCode : langList) {
			tmpMap.put("locale", commonCode.getRescKeyValue());
			
			Nw031m nw031m = nw031mService.getNw031m(tmpMap);			
			nw031mList.add(nw031m);
		}			
		
		map.put("majCode", "CD009"); //컨텐츠 제공 유형
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> cntnOfferTypeList = commonService.getCommonSubCodeList(map);
		
		map.put("majCode", "CD011"); //컨텐츠 유형
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> cntnTypeList = commonService.getCommonSubCodeList(map);
		
		List<Hr001m> companyList = hr001mService.getGroupCompanyListAll(map);
		
		List<Nw032m> nw032mList = nw032mService.getNw032mList(tmpMap);
		String nw032ms = "";
		int i = 0;
		for (Nw032m nw032m : nw032mList) {
			if (i == 0) nw032ms = nw032m.getCoId();
			else nw032ms += "," + nw032m.getCoId();
			i++;
		}
		
		mav.addObject("cntnOfferTypeList", cntnOfferTypeList);
		mav.addObject("contentInfo", contentInfo);
		mav.addObject("nw031mList", nw031mList);
		mav.addObject("cntnTypeList", cntnTypeList);
		mav.addObject("companyList", companyList);
		mav.addObject("nw032ms", nw032ms);
		return mav;
	}
	
	/**
	 * 마스터 ID 목록을 가져온다.
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentsMgr/masterIdSelectPopup", method = RequestMethod.GET)
	public ModelAndView masterIdSelectPopup(HttpServletRequest request, HttpServletResponse response) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		ModelAndView mav = new ModelAndView("contentsMgr/masterid_select_form_01");
		String cntnType = StringUtil.null2void(request.getParameter("cntnType"));
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String typeCodeName = StringUtil.null2void(request.getParameter("typeCodeName"));
		
		mav.addObject("coId", coId);
		mav.addObject("cntnType", cntnType);
		mav.addObject("typeCodeName", typeCodeName);
		return mav;
	}
	
	/**
	 * 컨텐츠 권한 관리 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentsMgr/contentRightsMgr", method = RequestMethod.GET)
	public ModelAndView contentRightsMgr(HttpServletRequest request, HttpServletResponse response) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String adminType = SecurityUtil.getAuthority();
		
		ModelAndView mav = new ModelAndView("contentsMgr/content_auth_mgr_form_01");
		
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId", userSession.getCoId());
		map.put("adminType", adminType);
		
		List<Hr001m> coList = hr001mService.getHr001mList(map);		
		
		mav.addObject("coList", coList);
		return mav;
	}
	
	/**
	 * 컨텐츠 권한 등록 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentsMgr/contentRightsAddForm", method = RequestMethod.GET)
	public ModelAndView contentRightsAddForm(HttpServletRequest request, HttpServletResponse response) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String cntnId = StringUtil.null2void(request.getParameter("cntnId"));
		
		ModelAndView mav = new ModelAndView("contentsMgr/content_auth_add_form_01");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("lang", userSession.getLang());
		map.put("majCode", "CD018"); //컨텐츠 유형
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> useAuthTypeList = commonService.getCommonSubCodeList(map);
		
		mav.addObject("useAuthTypeList", useAuthTypeList);
		mav.addObject("coId", coId);
		mav.addObject("cntnId", cntnId);
		return mav;
	}		
	
	/**
	 * 컨텐츠 권한 수정 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentsMgr/contentRightsModifyForm", method = RequestMethod.GET)
	public ModelAndView contentRightsModifyForm(HttpServletRequest request, HttpServletResponse response) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String cntnId = StringUtil.null2void(request.getParameter("cntnId"));
		String useAuthType = StringUtil.null2void(request.getParameter("useAuthType"));
		String useAuthGrpCode = StringUtil.null2void(request.getParameter("useAuthGrpCode"));
		
		ModelAndView mav = new ModelAndView("contentsMgr/content_auth_modify_form_01");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("lang", userSession.getLang());
		map.put("majCode", "CD018"); //컨텐츠 유형
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> useAuthTypeList = commonService.getCommonSubCodeList(map);
		
		map.put("cntnId", cntnId);
		map.put("useAuthType", useAuthType);
		map.put("useAuthGrpCode", useAuthGrpCode);
		Nw033m nw033m = nw033mService.getNw033m(map);
		
		mav.addObject("useAuthTypeList", useAuthTypeList);
		mav.addObject("coId", coId);
		mav.addObject("cntnId", cntnId);
		mav.addObject("nw033m", nw033m);
		return mav;
	}
	
	/**
	 * 컨텐츠 담당자 관리 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentsMgr/contentChargeMgr", method = RequestMethod.GET)
	public ModelAndView contentChargeMgr(HttpServletRequest request, HttpServletResponse response) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String adminType = SecurityUtil.getAuthority();
		
		ModelAndView mav = new ModelAndView("contentsMgr/content_charge_mgr_form_01");
		
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId", userSession.getCoId());
		map.put("adminType", adminType);
		
		List<Hr001m> coList = hr001mService.getHr001mList(map);		
		
		mav.addObject("coList", coList);
		return mav;
	}
	
	/**
	 * 컨텐츠 담당자 등록화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentsMgr/contentChargeAddForm", method = RequestMethod.GET) 
	public ModelAndView contentChargeAddForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String cntnId = StringUtil.null2void(request.getParameter("cntnId"));
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		ModelAndView mav = new ModelAndView("contentsMgr/content_charge_add_form_01");
		mav.addObject("coId", coId);
		mav.addObject("cntnId", cntnId);
		return mav;
	}
	
	/**
	 * Content 검색 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentsMgr/contentSearchForm", method = RequestMethod.GET)
	public ModelAndView roleSearchForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("contentsMgr/content_search_form_01");
		String targetObj = StringUtil.null2void(request.getParameter("targetObj"));
		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
				
		Map<String, Object> map = new HashMap<String, Object>();
		//지원 언어 목록 체크
		map.put("coId", userSession.getCoId());
		map.put("lang", userSession.getLang());
		map.put("majCode", "CD023"); //컨텐츠 검색 항목(CD023)
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> searchTypeList = commonService.getCommonSubCodeList(map);	
		
		mav.addObject("searchTypeList", searchTypeList);
		mav.addObject("targetObj", targetObj);
		return mav;
	}
}
