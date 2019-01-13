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
import org.uniworks.groupware.admin.common.util.StringUtil;
import org.uniworks.groupware.admin.domain.Cm001c;
import org.uniworks.groupware.admin.domain.Cm002c;
import org.uniworks.groupware.admin.domain.Cm003m;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.service.Cm001cService;
import org.uniworks.groupware.admin.service.Cm002cService;
import org.uniworks.groupware.admin.service.CommonService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class CodeManagerController {
	private static final Logger logger = LoggerFactory.getLogger(CodeManagerController.class);
	@Autowired Cm001cService cm001cService;
	@Autowired Cm002cService cm002cService;
	@Autowired CommonService commonService;
	@Autowired private MessageSource messageSource;
	
	/**
	 * 주코드 목록
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/mastercode/coId/{coId}")
	public ResponseEntity<List<Cm001c>> getMasterCodeList(@PathVariable("coId") String coId, HttpServletRequest request) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId",  coId);
		map.put("lang", userSession.getLang());
		// 주코드 목록을 가져온다.
		List<Cm001c> returnList = cm001cService.getCm001cList(map);
		return new ResponseEntity<List<Cm001c>>(returnList, HttpStatus.OK);
	}	
	
	/**
	 * 주코드 정보 가져오기
	 * @param coId
	 * @param majCode
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/mastercode/coId/{coId}/majCode/{majCode}")
	public ResponseEntity<Cm001c> getMasterCodeInfo(@PathVariable("coId") String coId, 
			@PathVariable("majCode") String majCode, HttpServletRequest request) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId",  coId);
		map.put("majCode", majCode);
		map.put("lang", userSession.getLang());
		//주코드 정보를 가져온다
		Cm001c cm001c = cm001cService.getCm001c(map);
		
		return new ResponseEntity<Cm001c>(cm001c, HttpStatus.OK);
	}
	
	/**
	 * 주코드 정보 등록
	 * @param cm001c
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/mastercode/create")
	public ResponseEntity<String> createMasterCode(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {	
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String result = "";						
		
		Cm001c cm001c = new Cm001c();
		cm001c.setCoId((String)model.get("coId"));
		cm001c.setMajCode((String)model.get("majCode"));
		cm001c.setRescKey((String)model.get("rescKey"));
		cm001c.setUseIndc((String)model.get("useIndc"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", cm001c.getCoId());
		map.put("majCode", cm001c.getMajCode());
		map.put("lang", userSession.getLang());
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", cm001c.getCoId());
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		ArrayList<Cm003m> arr = new ArrayList<Cm003m>();
		
		for (CommonCode commonCode : langList) {
			String majCodeName = StringUtil.null2void((String)model.get("majCodeName_" + commonCode.getRescKeyValue()));
			String majCodeDesc = StringUtil.null2void((String)model.get("majCodeDesc_" + commonCode.getRescKeyValue()));
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				cm001c.setMajCodeName(majCodeName);
				cm001c.setMajCodeDesc(majCodeDesc);
				
				// 등록된 주코드가 있는지 체크. 있으면 메시지 리턴
				Cm001c chkCm001c = cm001cService.getCm001c(map);
				if (chkCm001c != null && chkCm001c.getMajCode().equalsIgnoreCase(cm001c.getMajCode())) {
					result = messageSource.getMessage("resc.msg.majCodeExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}								
			}
			
			Cm003m cm003m = new Cm003m();
			cm003m.setCoId(cm001c.getCoId());
			cm003m.setLocale(commonCode.getRescKeyValue());
			cm003m.setRescKey(cm001c.getRescKey());
			cm003m.setRescKeyDesc(majCodeDesc);
			cm003m.setRescKeyValue(majCodeName);
			
			arr.add(cm003m);
		}				
		
		// 주코드 등록
		int cnt = cm001cService.addMasterCodeInfo(cm001c, arr);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.addOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.addFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * 주코드 정보를 수정 한다.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PutMapping(value = "/mastercode/update")
	public ResponseEntity<String> updateMasterCode(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {	
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String result = "";						
		
		Cm001c cm001c = new Cm001c();
		cm001c.setCoId((String)model.get("coId"));
		cm001c.setMajCode((String)model.get("majCode"));
		cm001c.setRescKey((String)model.get("rescKey"));
		cm001c.setUseIndc((String)model.get("useIndc"));
		cm001c.setPrntMajCode((String)model.get("prntMajCode"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", cm001c.getCoId());
		map.put("majCode", cm001c.getMajCode());
		map.put("lang", userSession.getLang());
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", cm001c.getCoId());
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		ArrayList<Cm003m> arr = new ArrayList<Cm003m>();
		
		for (CommonCode commonCode : langList) {
			String majCodeName = StringUtil.null2void((String)model.get("majCodeName_" + commonCode.getRescKeyValue()));
			String majCodeDesc = StringUtil.null2void((String)model.get("majCodeDesc_" + commonCode.getRescKeyValue()));
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				cm001c.setMajCodeName(majCodeName);
				cm001c.setMajCodeDesc(majCodeDesc);
				
				// 등록된 주코드가 있는지 체크. 없으면 메시지 리턴
				Cm001c chkCm001c = cm001cService.getCm001c(map);
				if (chkCm001c == null) {
					result = messageSource.getMessage("resc.msg.majCodeNotExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}								
			}
			
			Cm003m cm003m = new Cm003m();
			cm003m.setCoId(cm001c.getCoId());
			cm003m.setLocale(commonCode.getRescKeyValue());
			cm003m.setRescKey(cm001c.getRescKey());
			cm003m.setRescKeyDesc(majCodeDesc);
			cm003m.setRescKeyValue(majCodeName);
			
			arr.add(cm003m);
		}				
		
		// 주코드 등록
		int cnt = cm001cService.updateMasterCodeInfo(cm001c, arr);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.modifyOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.modifyFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * 주코드 정보를 삭제한다.
	 * @param coId
	 * @param majCode
	 * @param rescKey
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping(value = "/mastercode/delete/coId/{coId}/majCode/{majCode}/rescKey/{rescKey}")
	public ResponseEntity<String> deleteMasterCode(@PathVariable("coId") String coId, @PathVariable("majCode") String majCode, 
			@PathVariable("rescKey") String rescKey, HttpServletRequest request, HttpServletResponse response) {	
		String result = "";		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId",  coId);
		map.put("majCode", majCode);
		map.put("rescKey", rescKey);
		map.put("lang", userSession.getLang());
		
		//주코드에 해당하는 부코드 목록을 가져온다.
		List<Cm002c> returnList = cm002cService.getCm002cList(map);
		
		//등록된 부코드가 있으면 삭제를 하지 않고 빠져 나간다.
		if (returnList.size() > 0) {
			result = messageSource.getMessage("resc.msg.subCodeExist", null, response.getLocale());
		} else {	//등록된 부코드가 없을 경우 삭제한다.
			cm001cService.deleteMasterCodeAndMultiLanguage(map);
			result = messageSource.getMessage("resc.msg.deleteOk", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * 주코드에 해당하는 부코드 록록을 가져온다.
	 * @param coId
	 * @param majCode
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/subcode/coId/{coId}/majCode/{majCode}")
	public ResponseEntity<List<Cm002c>> getSubCodeList(@PathVariable("coId") String coId, 
			@PathVariable("majCode") String majCode, HttpServletRequest request) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId",  coId);
		map.put("majCode", majCode);
		map.put("lang", userSession.getLang());
		
		//주코드에 해당하는 부코드 목록을 가져온다.
		List<Cm002c> returnList = cm002cService.getCm002cList(map);
		
		return new ResponseEntity<List<Cm002c>>(returnList, HttpStatus.OK);
	}
	
	/**
	 * 부코드 정보를 가져온다
	 * @param coId
	 * @param majCode
	 * @param subCode
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/subcode/coId/{coId}/majCode/{majCode}/subCode/{subCode}")
	public ResponseEntity<Cm002c> getSubCodeInfo(@PathVariable("coId") String coId, 
			@PathVariable("majCode") String majCode, @PathVariable("subCode") String subCode, HttpServletRequest request) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId",  coId);
		map.put("majCode", majCode);
		map.put("subCode", subCode);
		map.put("lang", userSession.getLang());
		
		//부코드 정보를 가져온다.
		Cm002c cm002c = cm002cService.getCm002c(map);
		
		return new ResponseEntity<Cm002c>(cm002c, HttpStatus.OK);
	}
	
	/**
	 * 부코드 정보 등록
	 * @param cm001c
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/subcode/create")
	public ResponseEntity<String> createSubCode(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String result = "";						
		
		Cm002c cm002c = new Cm002c();
		cm002c.setCoId((String)model.get("coId"));
		cm002c.setMajCode((String)model.get("majCode"));
		cm002c.setRescKey((String)model.get("rescKey"));
		cm002c.setSubCode((String)model.get("subCode"));
		cm002c.setUseIndc((String)model.get("useIndc"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", cm002c.getCoId());
		map.put("majCode", cm002c.getMajCode());
		map.put("lang", userSession.getLang());
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", cm002c.getCoId());
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		ArrayList<Cm003m> arr = new ArrayList<Cm003m>();
		
		for (CommonCode commonCode : langList) {
			String subCodeName = StringUtil.null2void((String)model.get("subCodeName_" + commonCode.getRescKeyValue()));
			String subCodeDesc = StringUtil.null2void((String)model.get("subCodeDesc_" + commonCode.getRescKeyValue()));
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				cm002c.setSubCodeName(subCodeName);
				cm002c.setSubCodeDesc(subCodeDesc);
				
				// 등록된 주코드가 있는지 체크. 있으면 메시지 리턴
				Cm002c chkCm002c = cm002cService.getCm002c(map);
				if (chkCm002c != null && chkCm002c.getMajCode().equalsIgnoreCase(cm002c.getMajCode())) {
					result = messageSource.getMessage("resc.msg.subCodeExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}								
			}
			
			Cm003m cm003m = new Cm003m();
			cm003m.setCoId(cm002c.getCoId());
			cm003m.setLocale(commonCode.getRescKeyValue());
			cm003m.setRescKey(cm002c.getRescKey());
			cm003m.setRescKeyDesc(subCodeDesc);
			cm003m.setRescKeyValue(subCodeName);
			
			arr.add(cm003m);
		}				
		
		// 주코드 등록
		int cnt = cm002cService.addSubCodeInfo(cm002c, arr);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.addOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.addFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}		
	
	/**
	 * 부코드 정보를 수정 한다.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PutMapping(value = "/subcode/update")
	public ResponseEntity<String> updateSubCode(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {	
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String result = "";						
		
		Cm002c cm002c = new Cm002c();
		cm002c.setCoId((String)model.get("coId"));
		cm002c.setMajCode((String)model.get("majCode"));
		cm002c.setRescKey((String)model.get("rescKey"));
		cm002c.setSubCode((String)model.get("subCode"));
		cm002c.setUseIndc((String)model.get("useIndc"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", cm002c.getCoId());
		map.put("majCode", cm002c.getMajCode());
		map.put("lang", userSession.getLang());
		map.put("subCode", cm002c.getSubCode());
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", cm002c.getCoId());
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		ArrayList<Cm003m> arr = new ArrayList<Cm003m>();
		
		for (CommonCode commonCode : langList) {
			String subCodeName = StringUtil.null2void((String)model.get("subCodeName_" + commonCode.getRescKeyValue()));
			String subCodeDesc = StringUtil.null2void((String)model.get("subCodeDesc_" + commonCode.getRescKeyValue()));
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				cm002c.setSubCodeName(subCodeName);
				cm002c.setSubCodeDesc(subCodeDesc);
				
				// 등록된 부코드가 있는지 체크. 등록된 부코드가 있으면 메시지 리턴
				logger.debug("map : " + map.toString());
				Cm002c chkCm002c = cm002cService.getCm002c(map);
				if (chkCm002c == null) {
					result = messageSource.getMessage("resc.msg.subCodeNotExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}								
			}
			
			Cm003m cm003m = new Cm003m();
			cm003m.setCoId(cm002c.getCoId());
			cm003m.setLocale(commonCode.getRescKeyValue());
			cm003m.setRescKey(cm002c.getRescKey());
			cm003m.setRescKeyDesc(subCodeDesc);
			cm003m.setRescKeyValue(subCodeName);
			
			arr.add(cm003m);
		}				
		
		// 주코드 등록
		int cnt = cm002cService.updateSubCodeInfo(cm002c, arr);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.modifyOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.modifyFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * 부코드 정보를 삭제한다.
	 * @param coId
	 * @param majCode
	 * @param subCode
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping(value = "/subcode/delete/coId/{coId}/majCode/{majCode}/subCode/{subCode}/rescKey/{rescKey}")
	public ResponseEntity<String> deleteSubCode(@PathVariable("coId") String coId, @PathVariable("majCode") String majCode, 
			@PathVariable("subCode") String subCode, @PathVariable("rescKey") String rescKey, HttpServletRequest request, HttpServletResponse response) {	
		String result = "";		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("coId",  coId);
		map.put("majCode", majCode);
		map.put("subCode", subCode);
		map.put("rescKey", rescKey);
		map.put("lang", userSession.getLang());
		
		cm002cService.deleteSubCodeAndMultiLanguage(map);
		result = messageSource.getMessage("resc.msg.deleteOk", null, response.getLocale());		
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
