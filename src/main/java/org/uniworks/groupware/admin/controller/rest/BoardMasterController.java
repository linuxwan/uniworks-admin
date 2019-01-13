/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
import org.uniworks.groupware.admin.common.util.ApplicationConfigReader;
import org.uniworks.groupware.admin.common.util.DateUtil;
import org.uniworks.groupware.admin.common.util.StringUtil;
import org.uniworks.groupware.admin.common.util.WebUtil;
import org.uniworks.groupware.admin.domain.BoardMasterInfo;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.domain.Nw001m;
import org.uniworks.groupware.admin.domain.Nw002m;
import org.uniworks.groupware.admin.domain.Nw003m;
import org.uniworks.groupware.admin.service.BoardMasterService;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;
import org.uniworks.groupware.admin.service.Nw001mService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class BoardMasterController {
	private static final Logger logger = LoggerFactory.getLogger(BoardMasterController.class);
	@Autowired BoardMasterService boardMstService;		
	@Autowired CommonService commonService;
	@Autowired Nw001mService nw001mService;
	@Autowired Hr001mService hr001mService;
	@Autowired private MessageSource messageSource;
	
	/**
	 * 게시판 마스터 목록 가져오기
	 * @param request
	 * @param coId
	 * @param searchKind
	 * @param searchWord
	 * @param orderBy
	 * @return
	 */
	@GetMapping(value = "/boardMaster/coId/{coId}/searchKind/{searchKind}/searchWord/{searchWord}/orderBy/{orderBy}")
	public ResponseEntity<List<BoardMasterInfo>> getBoardMaster(HttpServletRequest request, @PathVariable("coId") String coId, 
				@PathVariable("searchKind") String searchKind, @PathVariable("searchWord") String searchWord, @PathVariable("orderBy") String orderBy) {		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());		
		map.put("coId", coId);
		map.put("searchKind", searchKind);
		if (searchWord.equals("0")) searchWord = "%";
		map.put("searchWord", searchWord);
		map.put("orderBy", orderBy);
		
		List<BoardMasterInfo> boardMst = boardMstService.getBoardMasterList(map);
		return new ResponseEntity<List<BoardMasterInfo>>(boardMst, HttpStatus.OK);
	}
	
	/**
	 * 게시판 마스터 등록
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/boardMaster/create")
	public ResponseEntity<String> createBoardMaster(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		Nw001m nw001m = new Nw001m();
		WebUtil.bind(model, nw001m);
		
		nw001m.setCrtDate(DateUtil.getCurrentDate());
		nw001m.setCrtId(userSession.getAdminId());
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", nw001m.getCoId());
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		ArrayList<Nw002m> nw002mList = new ArrayList<Nw002m>();
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw001m.getCoId());
		map.put("boardId", nw001m.getBoardId());
		
		for (CommonCode commonCode : langList) {
			String boardName = StringUtil.null2void((String)model.get("boardName_" + commonCode.getRescKeyValue()));	
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				Nw001m tempNw001m = nw001mService.getNw001m(map);
				if (tempNw001m != null && tempNw001m.getBoardId().equalsIgnoreCase(nw001m.getBoardId())) {
					result = messageSource.getMessage("resc.msg.boardMstIdExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
			
			Nw002m nw002m = new Nw002m();
			nw002m.setCoId(nw001m.getCoId());
			nw002m.setBoardId(nw001m.getBoardId());
			nw002m.setBoardName(boardName);
			nw002m.setLocale(commonCode.getRescKeyValue());
			
			nw002mList.add(nw002m);
		}
		
		//그룹사 모두 가져오기		
		List<Hr001m> hr001mList = hr001mService.getGroupCompanyListAll(map);
		ArrayList<Nw003m> nw003mList = new ArrayList<Nw003m>();
		DateUtil clsDate = new DateUtil(9999, 12, 31);
		if (nw001m.getEntrOpenIndc().equalsIgnoreCase("Y")) {
			for (Hr001m hr001m : hr001mList) {
				Nw003m nw003m = new Nw003m();
				nw003m.setCoId(hr001m.getCoId());
				nw003m.setBoardId(nw001m.getBoardId());
				nw003m.setCrtDate(DateUtil.getCurrentDate());
				nw003m.setClsDate(clsDate.getDate());
				
				nw003mList.add(nw003m);
			}
		} else {			
			if (model.get("company") instanceof String) {
				String company = (String) model.get("company");
				Nw003m nw003m = new Nw003m();
				nw003m.setCoId(company);
				nw003m.setBoardId(nw001m.getBoardId());
				nw003m.setCrtDate(DateUtil.getCurrentDate());
				nw003m.setClsDate(clsDate.getDate());
				
				nw003mList.add(nw003m);
			} else {
				@SuppressWarnings("unchecked")
				ArrayList<String> company = (ArrayList<String>) model.get("company");
				if (company != null && company.size() > 0) {
					for (String strCoId : company) {
						Nw003m nw003m = new Nw003m();
						nw003m.setCoId(strCoId);
						nw003m.setBoardId(nw001m.getBoardId());
						nw003m.setCrtDate(DateUtil.getCurrentDate());
						nw003m.setClsDate(clsDate.getDate());
						
						nw003mList.add(nw003m);
					}
				}
			}
		}
		
		int cnt = boardMstService.addBoardMasterInfo(nw001m, nw002mList, nw003mList);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.addOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.addFail", null, response.getLocale());
		}
						
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}
	
	/**
	 * 게시판 마스터 수정
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PutMapping(value = "/boardMaster/modify")
	public ResponseEntity<String> modifyBoardMaster(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		Nw001m nw001m = new Nw001m();
		WebUtil.bind(model, nw001m);
		
		nw001m.setCrtDate(DateUtil.getCurrentDate());
		nw001m.setCrtId(userSession.getAdminId());
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", nw001m.getCoId());
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		ArrayList<Nw002m> nw002mList = new ArrayList<Nw002m>();
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw001m.getCoId());
		map.put("boardId", nw001m.getBoardId());
		
		for (CommonCode commonCode : langList) {
			String boardName = StringUtil.null2void((String)model.get("boardName_" + commonCode.getRescKeyValue()));	
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				Nw001m tempNw001m = nw001mService.getNw001m(map);
				if (tempNw001m == null) {
					result = messageSource.getMessage("resc.msg.boardMstIdNotExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
			
			Nw002m nw002m = new Nw002m();
			nw002m.setCoId(nw001m.getCoId());
			nw002m.setBoardId(nw001m.getBoardId());
			nw002m.setBoardName(boardName);
			nw002m.setLocale(commonCode.getRescKeyValue());
			
			nw002mList.add(nw002m);
		}
		
		//그룹사 모두 가져오기		
		List<Hr001m> hr001mList = hr001mService.getGroupCompanyListAll(map);
		ArrayList<Nw003m> nw003mList = new ArrayList<Nw003m>();
		DateUtil clsDate = new DateUtil(9999, 12, 31);
		if (nw001m.getEntrOpenIndc().equalsIgnoreCase("Y")) {
			for (Hr001m hr001m : hr001mList) {
				Nw003m nw003m = new Nw003m();
				nw003m.setCoId(hr001m.getCoId());
				nw003m.setBoardId(nw001m.getBoardId());
				nw003m.setCrtDate(DateUtil.getCurrentDate());
				nw003m.setClsDate(clsDate.getDate());
				
				nw003mList.add(nw003m);
			}
		} else {			
			if (model.get("company") instanceof String) {
				String company = (String) model.get("company");
				Nw003m nw003m = new Nw003m();
				nw003m.setCoId(company);
				nw003m.setBoardId(nw001m.getBoardId());
				nw003m.setCrtDate(DateUtil.getCurrentDate());
				nw003m.setClsDate(clsDate.getDate());
				
				nw003mList.add(nw003m);
			} else {
				@SuppressWarnings("unchecked")
				ArrayList<String> company = (ArrayList<String>) model.get("company");
				if (company != null && company.size() > 0) {
					for (String strCoId : company) {
						Nw003m nw003m = new Nw003m();
						nw003m.setCoId(strCoId);
						nw003m.setBoardId(nw001m.getBoardId());
						nw003m.setCrtDate(DateUtil.getCurrentDate());
						nw003m.setClsDate(clsDate.getDate());
						
						nw003mList.add(nw003m);
					}
				}
			}
		}
		
		int cnt = boardMstService.modifyBoardMasterInfo(nw001m, nw002mList, nw003mList);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.modifyOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.modifyFail", null, response.getLocale());
		}
						
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}
	
	/**
	 * 게시판 마스터 삭제
	 * @param coId
	 * @param boardId
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping(value = "/boardMaster/delete/coId/{coId}/boardId/{boardId}")
	public ResponseEntity<String> deleteBoardMaster(@PathVariable("coId") String coId, @PathVariable("boardId") String boardId, 
			HttpServletRequest request, HttpServletResponse response) {	
		String result = "";		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId",  coId);
		map.put("boardId", boardId);
		
		result = boardMstService.deleteBoardMasterInfo(map, response.getLocale());
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
