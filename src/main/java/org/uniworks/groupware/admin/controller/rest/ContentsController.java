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
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.ContentInfo;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.domain.Nw003m;
import org.uniworks.groupware.admin.domain.Nw030m;
import org.uniworks.groupware.admin.domain.Nw031m;
import org.uniworks.groupware.admin.domain.Nw032m;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.ContentService;
import org.uniworks.groupware.admin.service.Hr001mService;
import org.uniworks.groupware.admin.service.Nw030mService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class ContentsController {
	private static final Logger logger = LoggerFactory.getLogger(ContentsController.class);
	@Autowired ContentService cntnService;
	@Autowired CommonService commonService;
	@Autowired Hr001mService hr001mService;
	@Autowired Nw030mService nw030mService;
	@Autowired private MessageSource messageSource;
	
	/**
	 * 컨텐츠 목록 가져오기
	 * @param request
	 * @param coId
	 * @param searchKind
	 * @param searchWord
	 * @param orderBy
	 * @return
	 */
	@GetMapping(value = "/contents/coId/{coId}/searchKind/{searchKind}/searchWord/{searchWord}/orderBy/{orderBy}")
	public ResponseEntity<List<ContentInfo>> getContents(HttpServletRequest request, @PathVariable("coId") String coId, 
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
		
		List<ContentInfo> contents = cntnService.getContentList(map);
		return new ResponseEntity<List<ContentInfo>>(contents, HttpStatus.OK);
	}
	
	/**
	 * 컨텐츠 등록
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/contents/create")
	public ResponseEntity<String> createContent(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Nw030m nw030m = new Nw030m();
		WebUtil.bind(model, nw030m);
		nw030m.setCntnRgsrDate(DateUtil.getCurrentDate());
		nw030m.setCntnRgsrId(userSession.getAdminId());
						
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", model.get("coId"));
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		ArrayList<Nw031m> nw031mList = new ArrayList<Nw031m>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", model.get("coId"));
		map.put("cntnId", model.get("cntnId"));
		
		for (CommonCode commonCode : langList) {
			String cntnName = StringUtil.null2void((String)model.get("cntnName_" + commonCode.getRescKeyValue()));	
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				Nw030m tempNw030m = nw030mService.getNw030m(map);
				if (tempNw030m != null && tempNw030m.getCntnId().equalsIgnoreCase(nw030m.getCntnId())) {
					result = messageSource.getMessage("resc.msg.cntnIdExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
			
			Nw031m nw031m = new Nw031m();
			nw031m.setCntnId(nw030m.getCntnId());
			nw031m.setLocale(commonCode.getRescKeyValue());
			nw031m.setCntnName(cntnName);
			
			nw031mList.add(nw031m);
		}
		
		//그룹사 모두 가져오기		
		List<Hr001m> hr001mList = hr001mService.getGroupCompanyListAll(map);
		ArrayList<Nw032m> nw032mList = new ArrayList<Nw032m>();
		DateUtil clsDate = new DateUtil(9999, 12, 31);
		if (nw030m.getCntnJointFlag().equalsIgnoreCase("Y")) {
			for (Hr001m hr001m : hr001mList) {
				Nw032m nw032m = new Nw032m();
				nw032m.setCoId(hr001m.getCoId());
				nw032m.setCntnId(nw030m.getCntnId());
				nw032m.setStDate(DateUtil.getCurrentDate());
				nw032m.setFinDate(clsDate.getDate());
				nw032m.setUseIndc("Y");
				
				nw032mList.add(nw032m);
			}
		} else {			
			if (model.get("company") instanceof String) {
				String company = (String) model.get("company");
				Nw032m nw032m = new Nw032m();
				nw032m.setCoId(company);
				nw032m.setCntnId(nw030m.getCntnId());
				nw032m.setStDate(DateUtil.getCurrentDate());
				nw032m.setFinDate(clsDate.getDate());
				nw032m.setUseIndc("Y");
				
				nw032mList.add(nw032m);
			} else {
				@SuppressWarnings("unchecked")
				ArrayList<String> company = (ArrayList<String>) model.get("company");
				if (company != null && company.size() > 0) {
					for (String strCoId : company) {
						Nw032m nw032m = new Nw032m();
						nw032m.setCoId(strCoId);
						nw032m.setCntnId(nw030m.getCntnId());
						nw032m.setStDate(DateUtil.getCurrentDate());
						nw032m.setFinDate(clsDate.getDate());
						nw032m.setUseIndc("Y");
						
						nw032mList.add(nw032m);
					}
				}
			}
		}
		
		int cnt = cntnService.addContentInfo(nw030m, nw031mList, nw032mList);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.addOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.addFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}
	
	/**
	 * 컨텐츠 수정
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PutMapping(value = "/contents/modify")
	public ResponseEntity<String> modifyContent(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Nw030m nw030m = new Nw030m();
		WebUtil.bind(model, nw030m);
		nw030m.setCntnChngDate(DateUtil.getCurrentDate());
		nw030m.setCntnChngId(userSession.getAdminId());
						
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", model.get("coId"));
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		ArrayList<Nw031m> nw031mList = new ArrayList<Nw031m>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", model.get("coId"));
		map.put("cntnId", model.get("cntnId"));
		
		for (CommonCode commonCode : langList) {
			String cntnName = StringUtil.null2void((String)model.get("cntnName_" + commonCode.getRescKeyValue()));	
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				Nw030m tempNw030m = nw030mService.getNw030m(map);
				if (tempNw030m == null || !tempNw030m.getCntnId().equalsIgnoreCase(nw030m.getCntnId())) {
					result = messageSource.getMessage("resc.msg.cntnIdNotExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
			
			Nw031m nw031m = new Nw031m();
			nw031m.setCntnId(nw030m.getCntnId());
			nw031m.setLocale(commonCode.getRescKeyValue());
			nw031m.setCntnName(cntnName);
			
			nw031mList.add(nw031m);
		}
		
		//그룹사 모두 가져오기		
		List<Hr001m> hr001mList = hr001mService.getGroupCompanyListAll(map);
		ArrayList<Nw032m> nw032mList = new ArrayList<Nw032m>();
		DateUtil clsDate = new DateUtil(9999, 12, 31);
		if (nw030m.getCntnJointFlag().equalsIgnoreCase("Y")) {
			for (Hr001m hr001m : hr001mList) {
				Nw032m nw032m = new Nw032m();
				nw032m.setCoId(hr001m.getCoId());
				nw032m.setCntnId(nw030m.getCntnId());
				nw032m.setStDate(DateUtil.getCurrentDate());
				nw032m.setFinDate(clsDate.getDate());
				nw032m.setUseIndc("Y");
				
				nw032mList.add(nw032m);
			}
		} else {			
			if (model.get("company") instanceof String) {
				String company = (String) model.get("company");
				Nw032m nw032m = new Nw032m();
				nw032m.setCoId(company);
				nw032m.setCntnId(nw030m.getCntnId());
				nw032m.setStDate(DateUtil.getCurrentDate());
				nw032m.setFinDate(clsDate.getDate());
				nw032m.setUseIndc("Y");
				
				nw032mList.add(nw032m);
			} else {
				@SuppressWarnings("unchecked")
				ArrayList<String> company = (ArrayList<String>) model.get("company");
				if (company != null && company.size() > 0) {
					for (String strCoId : company) {
						Nw032m nw032m = new Nw032m();
						nw032m.setCoId(strCoId);
						nw032m.setCntnId(nw030m.getCntnId());
						nw032m.setStDate(DateUtil.getCurrentDate());
						nw032m.setFinDate(clsDate.getDate());
						nw032m.setUseIndc("Y");
						
						nw032mList.add(nw032m);
					}
				}
			}
		}
		
		int cnt = cntnService.modifyContentInfo(nw030m, nw031mList, nw032mList);
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
	@DeleteMapping(value = "/contents/delete/cntnId/{cntnId}")
	public ResponseEntity<String> deleteBoardMaster(@PathVariable("cntnId") String cntnId, 
			HttpServletRequest request, HttpServletResponse response) {	
		String result = "";		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("cntnId", cntnId);
		
		int cnt = cntnService.removeContentInfo(map);
		
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.deleteOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.deleteFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}
}
