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
import org.uniworks.groupware.admin.domain.ApprovalMasterInfo;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.Nw010m;
import org.uniworks.groupware.admin.domain.Nw011m;
import org.uniworks.groupware.admin.domain.Nw012m;
import org.uniworks.groupware.admin.domain.Nw015m;
import org.uniworks.groupware.admin.service.ApprovalMasterService;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Nw010mService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class ApprovalMasterController {
	private static final Logger logger = LoggerFactory.getLogger(ApprovalMasterController.class);
	
	@Autowired ApprovalMasterService apprMstService;
	@Autowired CommonService commonService;
	@Autowired Nw010mService nw010mService;
	@Autowired private MessageSource messageSource;
	
	/**
	 * 결재 마스터 목록 가져오기
	 * @param request
	 * @param coId
	 * @param searchKind
	 * @param searchWord
	 * @param orderBy
	 * @return
	 */
	@GetMapping(value = "/approvalMaster/coId/{coId}/searchKind/{searchKind}/searchWord/{searchWord}/orderBy/{orderBy}")
	public ResponseEntity<List<ApprovalMasterInfo>> getApprovalMaster(HttpServletRequest request, @PathVariable("coId") String coId, 
				@PathVariable("searchKind") String searchKind, @PathVariable("searchWord") String searchWord, @PathVariable("orderBy") String orderBy) {		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());
		map.put("crntDate", DateUtil.getCurrentDateToString());
		map.put("coId", coId);
		map.put("searchKind", searchKind);
		if (searchWord.equals("0")) searchWord = "%";
		map.put("searchWord", searchWord);
		map.put("orderBy", orderBy);
		
		List<ApprovalMasterInfo> apprMst = apprMstService.getApprMasterList(map);
		return new ResponseEntity<List<ApprovalMasterInfo>>(apprMst, HttpStatus.OK);
	}
	
	/**
	 * 결재 마스터 등록
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/approvalMaster/create")
	public ResponseEntity<String> createApprovalMaster(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		int apprLevel = Integer.parseInt((String)model.get("apprLevel"));
		
		Nw010m nw010m = new Nw010m();
		WebUtil.bind(model, nw010m);
		
		nw010m.setCrtId(userSession.getAdminId());
		nw010m.setCreateDate(DateUtil.getCurrentDate());
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", nw010m.getCoId());
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		ArrayList<Nw011m> nw011mList = new ArrayList<Nw011m>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw010m.getCoId());
		map.put("apprMstId", nw010m.getApprMstId());
		
		for (CommonCode commonCode : langList) {
			String apprDesc = StringUtil.null2void((String)model.get("apprDesc_" + commonCode.getRescKeyValue()));	
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				nw010m.setApprDesc(apprDesc);
				Nw010m chkNw010m = nw010mService.getNw010m(map);
				if (chkNw010m != null && chkNw010m.getApprMstId().equalsIgnoreCase(nw010m.getApprMstId())) {
					result = messageSource.getMessage("resc.msg.apprMstIdExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
			
			Nw011m nw011m = new Nw011m();
			nw011m.setCoId(nw010m.getCoId());
			nw011m.setApprMstId(nw010m.getApprMstId());
			nw011m.setApprDesc(apprDesc);
			nw011m.setLocale(commonCode.getRescKeyValue());
			
			nw011mList.add(nw011m);
		}
		
		Nw012m nw012m = new Nw012m();
		nw012m.setCoId(nw010m.getCoId());
		nw012m.setApprMstId(nw010m.getApprMstId());
		nw012m.setCrtDate(DateUtil.getCurrentDateToString());
		nw012m.setClsDate("99991231");
		nw012m.setApprLevel(apprLevel);			
		
		int cnt = apprMstService.addApprovalMasterInfo(nw010m, nw011mList, nw012m);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.addOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.addFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * 결재 마스터 수정
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PutMapping(value = "/approvalMaster/modify")
	public ResponseEntity<String> modifyApprovalMaster(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		int apprLevel = Integer.parseInt((String)model.get("apprLevel"));
		
		Nw010m nw010m = new Nw010m();
		WebUtil.bind(model, nw010m);
		
		nw010m.setCrtId(userSession.getAdminId());
		nw010m.setCreateDate(DateUtil.getCurrentDate());
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", nw010m.getCoId());
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		ArrayList<Nw011m> nw011mList = new ArrayList<Nw011m>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw010m.getCoId());
		map.put("apprMstId", nw010m.getApprMstId());
		
		for (CommonCode commonCode : langList) {
			String apprDesc = StringUtil.null2void((String)model.get("apprDesc_" + commonCode.getRescKeyValue()));	
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				nw010m.setApprDesc(apprDesc);
				Nw010m chkNw010m = nw010mService.getNw010m(map);
				if (chkNw010m == null) {
					result = messageSource.getMessage("resc.msg.apprMstIdNotExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
			
			Nw011m nw011m = new Nw011m();
			nw011m.setCoId(nw010m.getCoId());
			nw011m.setApprMstId(nw010m.getApprMstId());
			nw011m.setApprDesc(apprDesc);
			nw011m.setLocale(commonCode.getRescKeyValue());
			
			nw011mList.add(nw011m);
		}
		
		Nw012m nw012m = new Nw012m();
		nw012m.setCoId(nw010m.getCoId());
		nw012m.setApprMstId(nw010m.getApprMstId());
		nw012m.setCrtDate(DateUtil.getCurrentDateToString());
		nw012m.setClsDate("99991231");
		nw012m.setApprLevel(apprLevel);				
		
		int cnt = apprMstService.modifyApprovalMasterInfo(nw010m, nw011mList, nw012m);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.modifyOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.modifyFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * 결재 유형 정보를 삭제한다.
	 * @param coId
	 * @param apprItemId
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping(value = "/approvalMaster/delete/coId/{coId}/apprMstId/{apprMstId}")
	public ResponseEntity<String> deleteMasterCode(@PathVariable("coId") String coId, @PathVariable("apprMstId") String apprMstId, 
			HttpServletRequest request, HttpServletResponse response) {	
		String result = "";		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId",  coId);
		map.put("apprMstId", apprMstId);
		
		//생성된 결재 문서들이 있는지 확인. 결재문서들이 존재할 경우에는 결재마스터 정보를 삭제할 수 없다.
		int apprDocCount = apprMstService.getApprovalDocCount(map);
		if (apprDocCount > 0) {
			result = messageSource.getMessage("resc.msg.apprDocsExist", null, response.getLocale());
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
		
		int cnt = apprMstService.deleteApprovalMasterInfo(map);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.deleteOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.deleteFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
