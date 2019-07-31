/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.controller.rest;

import java.util.ArrayList;
import java.util.Date;
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
import org.uniworks.groupware.admin.domain.Ogan;
import org.uniworks.groupware.admin.domain.Om001m;
import org.uniworks.groupware.admin.domain.Om002m;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Om001mService;
import org.uniworks.groupware.admin.service.Om002mService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class OganController {
	private static final Logger logger = LoggerFactory.getLogger(OganController.class);
	
	@Autowired Om001mService om001mService;
	@Autowired Om002mService om002mService;
	@Autowired CommonService commonService;
	@Autowired private MessageSource messageSource;
	
	/**
	 * 조직 목록을 모두 다 가져온다.
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/ogan")
	public ResponseEntity<List<Om001m>> getOganListAll(HttpServletRequest request) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();	
		String coId = StringUtil.null2void(request.getParameter("coId"));
		map.put("coId", coId);
		map.put("lang", userSession.getLang());
		
		List<Om001m> om001mList = om001mService.getOm001mList(map);				
		
		return new ResponseEntity<List<Om001m>>(om001mList, HttpStatus.OK);
	}
	
	/**
	 * 조직 정보를 가져온다. 등록된 다국어별 정보를 모두 가져온다.
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/ogan/oganInfo/coId/{coId}/oganCode/{oganCode}/oganLev/{oganLev}")
	public ResponseEntity<List<Ogan>> getOganLangList(@PathVariable("coId") String coId, @PathVariable("oganCode") String oganCode,  
			@PathVariable("oganLev") String oganLev, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();	
		Date crntDate = DateUtil.getCurrentDate();
		map.put("coId", coId);
		map.put("oganCode", oganCode);
		map.put("oganLev", oganLev);
		map.put("crntDate", crntDate);
		
		List<Ogan> oganInfoList = om001mService.getOm001mListLang(map);
		
		return new ResponseEntity<List<Ogan>>(oganInfoList, HttpStatus.OK);
	}
	
	/**
	 * 조직 정보를 등록한다.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/ogan/create")
	public ResponseEntity<String> createOgan(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {	
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String result = "";
		Date crntDate = DateUtil.getCurrentDate();
				
		//yyyy-MM-dd 형식으로 넘어온 날짜를 yyyyMMdd형식으로 변환
		String oganEstbDate = StringUtil.delDash((String)model.get("oganEstbDate"));
		String oganClsDate = StringUtil.delDash((String)model.get("oganClsDate"));
		
		//yyyyMMdd형식의 문자열을 Date형으로 변환.
		model.put("oganEstbDate", new DateUtil(oganEstbDate).getDate());
		model.put("oganClsDate", new DateUtil(oganClsDate).getDate());		
		
		Om001m om001m = new Om001m();
		WebUtil.bind(model, om001m);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", om001m.getCoId());
		map.put("oganCode", om001m.getOganCode());
		map.put("oganLev", om001m.getOganLev());
		map.put("crntDate", crntDate);
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", om001m.getCoId());
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		ArrayList<Om002m> arr = new ArrayList<Om002m>();
		
		for (CommonCode commonCode : langList) {
			String oganName = StringUtil.null2void((String)model.get("oganName_" + commonCode.getRescKeyValue()));
			String oganDesc = StringUtil.null2void((String)model.get("oganDesc_" + commonCode.getRescKeyValue()));
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				om001m.setOganName(oganName);
				om001m.setOganDesc(oganDesc);
				
				// 등록된 조직 코드가 있는지 체크. 있으면 메시지 리턴
				Om001m chkOm001m = om001mService.getOm001m(map);
				if (chkOm001m != null && chkOm001m.getOganCode().equalsIgnoreCase(om001m.getOganCode())) {
					result = messageSource.getMessage("resc.msg.oganCodeExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}								
			}
			
			Om002m om002m = new Om002m();
			om002m.setCoId(om001m.getCoId());
			om002m.setRescKey(om001m.getRescKey());
			om002m.setLocale(commonCode.getRescKeyValue());
			om002m.setRescKeyValue(oganName);
			om002m.setRescKeyValueDesc(oganDesc);
			arr.add(om002m);
		}
		
		// 조직 정보를 등록한다.
		int cnt = om001mService.addOm001mOm002m(om001m, arr);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.addOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.addFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}
	
	/**
	 * 조직 정보를 삭제한다.
	 * @param coId
	 * @param oganCode
	 * @param oganEstbDate
	 * @param oganLev
	 * @param rescKey
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping(value = "/ogan/delete/coId/{coId}/oganCode/{oganCode}/oganLev/{oganLev}")
	public ResponseEntity<String> deleteOganInfo(@PathVariable("coId") final String coId, @PathVariable("oganCode") final String oganCode,
			@PathVariable("oganLev") final String oganLev, HttpServletRequest request, HttpServletResponse response) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String result = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("oganCode", oganCode);		
		map.put("oganLev", oganLev);
		map.put("locale", userSession.getLang());
		DateUtil dt = new DateUtil();
		map.put("crntDate", dt.getCurrentDateTimeConvertToTimestamp());
		
		Om001m om001m = om001mService.getOm001m(map);
		
		if (om001m != null) {	
			map.put("rescKey", om001m.getRescKey());
			map.put("oganEstbDate", om001m.getOganEstbDate());
			int cnt = om001mService.deleteOm001mOm002m(map);
		
			if (cnt > 0) {
				result = messageSource.getMessage("resc.msg.deleteOk", null, response.getLocale());
			} else {
				result = messageSource.getMessage("resc.msg.deleteFail", null, response.getLocale());
			}
		} else {
			result = messageSource.getMessage("resc.msg.deleteFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@PutMapping(value = "/ogan/update")
	public ResponseEntity<String> updateOganInfo(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {	
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String result = "";
		Date crntDate = DateUtil.getCurrentDate();
				
		//yyyy-MM-dd 형식으로 넘어온 날짜를 yyyyMMdd형식으로 변환
		String oganEstbDate = StringUtil.delDash((String)model.get("oganEstbDate"));
		String oganClsDate = StringUtil.delDash((String)model.get("oganClsDate"));
		
		//yyyyMMdd형식의 문자열을 Date형으로 변환.
		model.put("oganEstbDate", new DateUtil(oganEstbDate).getDate());
		model.put("oganClsDate", new DateUtil(oganClsDate).getDate());		
		
		Om001m om001m = new Om001m();
		WebUtil.bind(model, om001m);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", om001m.getCoId());
		map.put("oganCode", om001m.getOganCode());
		map.put("oganLev", om001m.getOganLev());
		map.put("crntDate", crntDate);
		map.put("lang", userSession.getLang());
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", om001m.getCoId());
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		ArrayList<Om002m> arr = new ArrayList<Om002m>();
		
		for (CommonCode commonCode : langList) {
			String oganName = StringUtil.null2void((String)model.get("oganName_" + commonCode.getRescKeyValue()));
			String oganDesc = StringUtil.null2void((String)model.get("oganDesc_" + commonCode.getRescKeyValue()));
			
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				om001m.setOganName(oganName);
				om001m.setOganDesc(oganDesc);
				
				// 등록된 조직 코드가 있는지 체크. 없으면 메시지 리턴
				Om001m chkOm001m = om001mService.getOm001m(map);
				if (chkOm001m == null || !chkOm001m.getOganCode().equalsIgnoreCase(om001m.getOganCode())) {
					result = messageSource.getMessage("resc.msg.oganCodeNotExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}								
			}
			
			Om002m om002m = new Om002m();
			om002m.setCoId(om001m.getCoId());
			om002m.setRescKey(om001m.getRescKey());
			om002m.setLocale(commonCode.getRescKeyValue());
			om002m.setRescKeyValue(oganName);
			om002m.setRescKeyValueDesc(oganDesc);
			
			arr.add(om002m);
		}
		
		// 조직 정보를 등록한다.
		int cnt = om001mService.updateOm001mOm002m(om001m, arr);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.modifyOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.modifyFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK); 			
	}
}
