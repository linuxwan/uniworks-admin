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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
import org.uniworks.groupware.admin.common.util.ApplicationConfigReader;
import org.uniworks.groupware.admin.common.util.StringUtil;
import org.uniworks.groupware.admin.domain.ApprTypeByApprInfo;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.Nw013m;
import org.uniworks.groupware.admin.domain.Nw014m;
import org.uniworks.groupware.admin.domain.Nw015m;
import org.uniworks.groupware.admin.service.ApprovalMasterService;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Nw013mService;
import org.uniworks.groupware.admin.service.Nw015mService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class ApprovalTypeController {
	private static final Logger logger = LoggerFactory.getLogger(ApprovalTypeController.class);	
	@Autowired ApprovalMasterService apprMstService;
	@Autowired CommonService commonService;
	@Autowired Nw013mService nw013mService;
	@Autowired Nw015mService nw015mService;
	@Autowired private MessageSource messageSource;
	
	/**
	 * 결재 유형 목록을 가져온다.
	 * @param request
	 * @param coId
	 * @return
	 */
	@GetMapping(value = "/approvalType/coId/{coId}")
	public ResponseEntity<List<Nw013m>> getApprovalTypeList(HttpServletRequest request, @PathVariable("coId") String coId) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("lang", userSession.getLang());
		
		List<Nw013m> apprTypeList = apprMstService.getApprTypeList(map);
		return new ResponseEntity<List<Nw013m>>(apprTypeList, HttpStatus.OK);
	}
	
	/**
	 * 결재 유형별 결재 마스터 목록을 가져온다.
	 * @param request
	 * @param coId
	 * @param apprItemId
	 * @return
	 */
	@GetMapping(value = "/apprTypeByApprInfo/coId/{coId}/apprItemId/{apprItemId}")
	public ResponseEntity<List<ApprTypeByApprInfo>> getApprTypeByApprInfo(HttpServletRequest request, @PathVariable("coId") String coId, 
					@PathVariable("apprItemId") String apprItemId) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("lang", userSession.getLang());
		map.put("apprItemId", apprItemId);
		
		List<ApprTypeByApprInfo> apprTypeByApprInfo = apprMstService.getApprTypeByApprInfo(map);
		return new ResponseEntity<List<ApprTypeByApprInfo>>(apprTypeByApprInfo, HttpStatus.OK);
	}
	
	/**
	 * 결재 유형 정보를 생성한다.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/approvalType/create")
	public ResponseEntity<String> createApprovalType(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String result = "";		
		String coId = (String)model.get("coId");
		String seqNo = (String)model.get("seqNo");			
		String apprItemId = (String)model.get("apprItemId");
		String createDate = StringUtil.delDash((String)model.get("createDate"));
		String imgUrl = StringUtil.null2void((String)model.get("imgUrl"));
		
		Nw013m nw013m = new Nw013m();
		nw013m.setCoId(coId);
		nw013m.setSeqNo(Integer.parseInt(seqNo));
		nw013m.setApprItemId(apprItemId);		
		nw013m.setCreateDate(createDate);
		nw013m.setImgUrl(imgUrl);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw013m.getCoId());
		map.put("apprItemId", nw013m.getApprItemId());
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", coId);
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		ArrayList<Nw014m> arrList = new ArrayList<Nw014m>();
		
		for (CommonCode commonCode : langList) {
			String apprItemNameLang = StringUtil.null2void((String)model.get("apprItemName_" + commonCode.getRescKeyValue()));			
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				nw013m.setApprItemName(apprItemNameLang);
				Nw013m chkNw013m = nw013mService.getNw013m(map);
				if (chkNw013m != null && chkNw013m.getApprItemId().equalsIgnoreCase(nw013m.getApprItemId())) {
					result = messageSource.getMessage("resc.msg.apprTypeExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
			
			Nw014m nw014m = new Nw014m();
			nw014m.setCoId(coId);
			nw014m.setApprItemId(nw013m.getApprItemId());
			nw014m.setApprItemName(apprItemNameLang);
			nw014m.setLocale(commonCode.getRescKeyValue());
			
			arrList.add(nw014m);
		}
		
		int cnt = apprMstService.addApprovalTypeInfo(nw013m, arrList);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.addOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.addFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * 결재 유형 정보를 수정한다.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/approvalType/modify")
	public ResponseEntity<String> modifyApprovalType(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String result = "";		
		String coId = (String)model.get("coId");
		String seqNo = (String)model.get("seqNo");			
		String apprItemId = (String)model.get("apprItemId");
		String createDate = StringUtil.delDash((String)model.get("createDate"));
		String imgUrl = StringUtil.null2void((String)model.get("imgUrl"));
		
		Nw013m nw013m = new Nw013m();
		nw013m.setCoId(coId);
		nw013m.setSeqNo(Integer.parseInt(seqNo));
		nw013m.setApprItemId(apprItemId);		
		nw013m.setCreateDate(createDate);
		nw013m.setImgUrl(imgUrl);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw013m.getCoId());
		map.put("apprItemId", nw013m.getApprItemId());
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", coId);
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		ArrayList<Nw014m> arrList = new ArrayList<Nw014m>();
		
		for (CommonCode commonCode : langList) {
			String apprItemNameLang = StringUtil.null2void((String)model.get("apprItemName_" + commonCode.getRescKeyValue()));			
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				nw013m.setApprItemName(apprItemNameLang);
				Nw013m chkNw013m = nw013mService.getNw013m(map);
				if (chkNw013m == null || !chkNw013m.getApprItemId().equalsIgnoreCase(nw013m.getApprItemId())) {
					result = messageSource.getMessage("resc.msg.apprTypeNotExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
			
			Nw014m nw014m = new Nw014m();
			nw014m.setCoId(coId);
			nw014m.setApprItemId(nw013m.getApprItemId());
			nw014m.setApprItemName(apprItemNameLang);
			nw014m.setLocale(commonCode.getRescKeyValue());
			
			arrList.add(nw014m);
		}
		
		int cnt = apprMstService.updateApprovalTypeInfo(nw013m, arrList);
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
	@DeleteMapping(value = "/approvalType/delete/coId/{coId}/apprItemId/{apprItemId}")
	public ResponseEntity<String> deleteMasterCode(@PathVariable("coId") String coId, @PathVariable("apprItemId") String apprItemId, 
			HttpServletRequest request, HttpServletResponse response) {	
		String result = "";		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId",  coId);
		map.put("apprItemId", apprItemId);
		
		result = apprMstService.deleteApprovalTypeInfo(map, response.getLocale());
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * 결재 유형별 결재 마스터 정보를 생성한다.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/apprMstByApprovalType/create")
	public ResponseEntity<String> createApprMstByApprovalType(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String coId = (String)model.get("coId");
		String apprMstId = (String)model.get("apprMstId");			
		String apprItemId = (String)model.get("apprItemId");
		int seqNo = Integer.parseInt((String)model.get("seqNo"));
		
		Nw015m nw015m = new Nw015m();
		nw015m.setCoId(coId);
		nw015m.setApprItemId(apprItemId);
		nw015m.setApprMstId(apprMstId);
		nw015m.setSeqNo(seqNo);
		
		int cnt = nw015mService.addNw015m(nw015m);
		
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.addOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.addFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * 결재 유형별 결재 마스터 정보를 수정한다.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/apprMstByApprovalType/modify")
	public ResponseEntity<String> modifyApprMstByApprovalType(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String coId = (String)model.get("coId");
		String apprMstId = (String)model.get("apprMstId");			
		String apprItemId = (String)model.get("apprItemId");
		int seqNo = Integer.parseInt((String)model.get("seqNo"));
		
		Nw015m nw015m = new Nw015m();
		nw015m.setCoId(coId);
		nw015m.setApprItemId(apprItemId);
		nw015m.setApprMstId(apprMstId);
		nw015m.setSeqNo(seqNo);
		
		int cnt = nw015mService.updateNw015m(nw015m);
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
	 * @param apprMstId
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping(value = "/apprMstByApprovalType/delete/coId/{coId}/apprItemId/{apprItemId}/apprMstId/{apprMstId}")
	public ResponseEntity<String> deleteApprMstByApprovalType(@PathVariable("coId") String coId, @PathVariable("apprItemId") String apprItemId, 
			@PathVariable("apprMstId") String apprMstId, HttpServletRequest request, HttpServletResponse response) {	
		String result = "";		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId",  coId);
		map.put("apprItemId", apprItemId);
		map.put("apprMstId", apprMstId);
		
		int cnt = nw015mService.deleteNw015m(map);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.deleteOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.deleteFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
	
