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
import org.uniworks.groupware.admin.domain.GroupInfo;
import org.uniworks.groupware.admin.domain.Nw106m;
import org.uniworks.groupware.admin.domain.Nw107m;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Nw105mService;
import org.uniworks.groupware.admin.service.Nw106mService;
import org.uniworks.groupware.admin.service.Nw107mService;
import org.uniworks.groupware.admin.service.RoleService;
import org.uniworks.groupware.admin.service.UserService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class RoleManagerController {
	private static final Logger logger = LoggerFactory.getLogger(RoleManagerController.class);
	@Autowired Nw105mService nw105mService;
	@Autowired Nw106mService nw106mService;
	@Autowired Nw107mService nw107mService;
	@Autowired UserService userService;
	@Autowired CommonService commonService;
	@Autowired RoleService roleService;
	@Autowired private MessageSource messageSource;
	
	/**
	 * Role 목록
	 * @param request
	 * @param coId
	 * @return
	 */
	@GetMapping(value = "/role/coId/{coId}")
	public ResponseEntity<List<Nw106m>> roleList(HttpServletRequest request, @PathVariable("coId") String coId) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		
		List<Nw106m> roleList = nw106mService.getNw106mList(map);
		return new ResponseEntity<List<Nw106m>>(roleList, HttpStatus.OK);
	}
	
	/**
	 * Role 등록
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/role/create")
	public ResponseEntity<String> createRole(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Nw106m nw106m = new Nw106m();
		WebUtil.bind(model, nw106m);
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", model.get("coId"));
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		ArrayList<Nw107m> nw107mList = new ArrayList<Nw107m>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", model.get("coId"));
		map.put("role", model.get("role"));
		
		for (CommonCode commonCode : langList) {
			String roleName = StringUtil.null2void((String)model.get("roleName_" + commonCode.getRescKeyValue()));	
			String roleDetl = StringUtil.null2void((String)model.get("roleDetl_" + commonCode.getRescKeyValue()));	
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				nw106m.setRoleName(roleName);
				nw106m.setRoleDetl(roleDetl);
				Nw107m tempNw107m = nw107mService.getNw107m(map);
				if (tempNw107m != null && tempNw107m.getRole().equalsIgnoreCase(nw106m.getRole())) {
					result = messageSource.getMessage("resc.msg.roleExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
			
			Nw107m nw107m = new Nw107m();
			nw107m.setCoId(nw106m.getCoId());
			nw107m.setLocale(commonCode.getRescKeyValue());
			nw107m.setRole(nw106m.getRole());
			nw107m.setRoleName(roleName);
			nw107m.setRoleDetl(roleDetl);
			
			nw107mList.add(nw107m);
		}		
		
		nw106m.setCrtId(userSession.getAdminId());
		nw106m.setCrtDate(DateUtil.getCurrentDate());
		
		int rtn = roleService.addRole(nw106m, nw107mList);
		
		if (rtn > 0) {
			result = messageSource.getMessage("resc.msg.addOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.addFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}
	
	/**
	 * Role 수정
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PutMapping(value = "/role/modify")
	public ResponseEntity<String> modifyRole(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Nw106m nw106m = new Nw106m();
		WebUtil.bind(model, nw106m);
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", model.get("coId"));
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		ArrayList<Nw107m> nw107mList = new ArrayList<Nw107m>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", model.get("coId"));
		map.put("role", model.get("role"));
		
		for (CommonCode commonCode : langList) {
			String roleName = StringUtil.null2void((String)model.get("roleName_" + commonCode.getRescKeyValue()));	
			String roleDetl = StringUtil.null2void((String)model.get("roleDetl_" + commonCode.getRescKeyValue()));
			map.put("locale", commonCode.getRescKeyValue());
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				nw106m.setRoleName(roleName);
				nw106m.setRoleDetl(roleDetl);
				Nw107m tempNw107m = nw107mService.getNw107m(map);
				if (tempNw107m != null && !tempNw107m.getRole().equalsIgnoreCase(nw106m.getRole())) {
					result = messageSource.getMessage("resc.msg.roleLangNotExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
			
			Nw107m nw107m = new Nw107m();
			nw107m.setCoId(nw106m.getCoId());
			nw107m.setLocale(commonCode.getRescKeyValue());
			nw107m.setRole(nw106m.getRole());
			nw107m.setRoleName(roleName);
			nw107m.setRoleDetl(roleDetl);
			
			nw107mList.add(nw107m);
		}		
		
		nw106m.setChngId(userSession.getAdminId());
		nw106m.setChngDate(DateUtil.getCurrentDate());
		
		int rtn = roleService.modifyRole(nw106m, nw107mList);
		if (rtn > 0) {
			result = messageSource.getMessage("resc.msg.modifyOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.modifyFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * Role 삭제
	 * @param coId
	 * @param boardId
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping(value = "/role/delete/coId/{coId}/role/{role}")
	public ResponseEntity<String> deleteRole(@PathVariable("coId") String coId, @PathVariable("role") String role, 
			HttpServletRequest request, HttpServletResponse response) {	
		String result = "";		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("role", role);
		
		int checkRoleCnt = nw105mService.getRoleUserCount(map);
		if (checkRoleCnt > 0) {
			result = messageSource.getMessage("resc.msg.notDeleteRole", null, response.getLocale());	
			return new ResponseEntity<String>(result, HttpStatus.OK); 
		}
		
		int cnt = roleService.removeRole(map);
		
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.deleteOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.deleteFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}	
	
	/**
	 * Role 검색
	 * @param request
	 * @param coId
	 * @param searchKind
	 * @param searchWord
	 * @param orderBy
	 * @return
	 */
	@GetMapping(value = "/role/search/coId/{coId}/searchKind/{searchKind}/searchWord/{searchWord}")
	public ResponseEntity<List<Nw106m>> getSearchGroupList(HttpServletRequest request, @PathVariable("coId") String coId, 
				@PathVariable("searchKind") String searchKind, @PathVariable("searchWord") String searchWord) {		
		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());		
		map.put("coId", coId);
		map.put("searchKind", searchKind);
		if (searchWord.equals("0")) searchWord = "%";
		map.put("searchWord", searchWord);
		
		List<Nw106m> grpList = commonService.getRoleListBySearch(map);
		return new ResponseEntity<List<Nw106m>>(grpList, HttpStatus.OK);
	}
}
