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
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.domain.Nw001m;
import org.uniworks.groupware.admin.domain.Nw002m;
import org.uniworks.groupware.admin.domain.Nw003m;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;
import org.uniworks.groupware.admin.service.Nw001mService;
import org.uniworks.groupware.admin.service.Nw002mService;
import org.uniworks.groupware.admin.service.Nw003mService;

/**
 * @author Park Chung Wan
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class BoardMasterMgrController {
	private static final Logger logger = LoggerFactory.getLogger(BoardMasterMgrController.class);
	@Autowired CommonService commonService;
	@Autowired Hr001mService hr001mService;
	@Autowired Nw001mService nw001mService;
	@Autowired Nw002mService nw002mService;
	@Autowired Nw003mService nw003mService;
	
	/**
	 * 게시판 마스터 목록
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/boardMasterMgr", method = RequestMethod.GET)
	public ModelAndView boardMasterMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("boardMaster/board_master_mgr_01");
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
	 * 게시판 마스터 등록 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/boardMasterMgr/boardMasterAddForm", method = RequestMethod.GET)
	public ModelAndView boardMasterAddForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		ModelAndView mav = new ModelAndView("boardMaster/board_master_add_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
		
		map.put("majCode", "CD007"); //게시판 마스터 타입
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> boardTypeList = commonService.getCommonSubCodeList(map);
		
		map.put("majCode",  "CD008"); //문서 유효기간을 코드성 정보에서 가져온다.
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> prsvTermList = commonService.getCommonSubCodeList(map);
		
		List<Hr001m> companyList = hr001mService.getGroupCompanyListAll(map);
		
		mav.addObject("coId", coId);
		mav.addObject("langList", langList);
		mav.addObject("prsvTermList", prsvTermList);			
		mav.addObject("boardTypeList", boardTypeList);
		mav.addObject("companyList", companyList);
		return mav;
	}
	
	/**
	 * 게시판 마스터 수정 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/boardMasterMgr/boardMasterModifyForm", method = RequestMethod.GET)
	public ModelAndView boardMasterModifyForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String boardId = StringUtil.null2void(request.getParameter("boardId"));
		ModelAndView mav = new ModelAndView("boardMaster/board_master_modify_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
		
		ArrayList<Nw002m> nw002mList = new ArrayList<Nw002m>();
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap.put("coId", coId);
		tmpMap.put("boardId", boardId);
		for (CommonCode commonCode : langList) {
			tmpMap.put("locale", commonCode.getRescKeyValue());
			
			Nw002m nw002m = nw002mService.getNw002m(tmpMap);			
			nw002mList.add(nw002m);
		}
		
		map.put("majCode", "CD007"); //게시판 마스터 타입
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> boardTypeList = commonService.getCommonSubCodeList(map);
		
		map.put("majCode",  "CD008"); //문서 유효기간을 코드성 정보에서 가져온다.
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> prsvTermList = commonService.getCommonSubCodeList(map);
		
		List<Hr001m> companyList = hr001mService.getGroupCompanyListAll(map);
		
		Nw001m nw001m = nw001mService.getNw001m(tmpMap);
		List<Nw003m> nw003mList = nw003mService.getNw003mList(tmpMap);
		String nw003ms = "";
		int i = 0;
		for (Nw003m nw003m : nw003mList) {
			if (i == 0) nw003ms = nw003m.getCoId();
			else nw003ms += "," + nw003m.getCoId();
			i++;
		}
		
		mav.addObject("coId", coId);		
		mav.addObject("prsvTermList", prsvTermList);			
		mav.addObject("boardTypeList", boardTypeList);
		mav.addObject("companyList", companyList);
		mav.addObject("nw002mList", nw002mList);
		mav.addObject("nw001m", nw001m);
		mav.addObject("nw003ms", nw003ms);
		return mav;
	}
	
	/**
	 * 게시판 마스터 조회 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/boardMasterMgr/boardMasterRetrieveForm", method = RequestMethod.GET)
	public ModelAndView boardMasterRetrieveForm(HttpServletRequest request, HttpServletResponse response) {
		String coId = StringUtil.null2void(request.getParameter("coId"));
		String boardId = StringUtil.null2void(request.getParameter("boardId"));
		ModelAndView mav = new ModelAndView("boardMaster/board_master_retrieve_form_01");
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		//지원 언어 정보를 일반코드에서 가져온다. MAJ_CODE : CD001
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("coId", coId);
		map.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		map.put("orderBy", "rescKeyValue");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(map);
		
		ArrayList<Nw002m> nw002mList = new ArrayList<Nw002m>();
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap.put("coId", coId);
		tmpMap.put("boardId", boardId);
		for (CommonCode commonCode : langList) {
			tmpMap.put("locale", commonCode.getRescKeyValue());
			
			Nw002m nw002m = nw002mService.getNw002m(tmpMap);			
			nw002mList.add(nw002m);
		}
		
		map.put("majCode", "CD007"); //게시판 마스터 타입
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> boardTypeList = commonService.getCommonSubCodeList(map);
		
		map.put("majCode",  "CD008"); //문서 유효기간을 코드성 정보에서 가져온다.
		map.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> prsvTermList = commonService.getCommonSubCodeList(map);
		
		List<Hr001m> companyList = hr001mService.getGroupCompanyListAll(map);
		
		Nw001m nw001m = nw001mService.getNw001m(tmpMap);
		List<Nw003m> nw003mList = nw003mService.getNw003mList(tmpMap);
		String nw003ms = "";
		int i = 0;
		for (Nw003m nw003m : nw003mList) {
			if (i == 0) nw003ms = nw003m.getCoId();
			else nw003ms += "," + nw003m.getCoId();
			i++;
		}
		
		mav.addObject("coId", coId);		
		mav.addObject("prsvTermList", prsvTermList);			
		mav.addObject("boardTypeList", boardTypeList);
		mav.addObject("companyList", companyList);
		mav.addObject("nw002mList", nw002mList);
		mav.addObject("nw001m", nw001m);
		mav.addObject("nw003ms", nw003ms);
		return mav;
	}
}
