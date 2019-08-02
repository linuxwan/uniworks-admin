/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.controller.rest;

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
import org.uniworks.groupware.admin.common.util.WebUtil;
import org.uniworks.groupware.admin.domain.Nw105m;
import org.uniworks.groupware.admin.domain.UserRole;
import org.uniworks.groupware.admin.service.Nw105mService;
import org.uniworks.groupware.admin.service.UserService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class UserRoleController {
	private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);
	@Autowired Nw105mService nw105mService;
	@Autowired UserService userService;
	@Autowired private MessageSource messageSource;
	
	/**
	 * Role별 사용자 목록을 가져온다.
	 * @param request
	 * @param coId
	 * @param role
	 * @return
	 */
	@GetMapping(value = "/userListByRole/coId/{coId}/role/{role}")
	public ResponseEntity<List<UserRole>> userListByRole(HttpServletRequest request, @PathVariable("coId") String coId, @PathVariable("role") String role) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("role", role);
		map.put("lang", userSession.getLang());
		
		List<UserRole> userByRoleList = userService.getUserListByRole(map);
		return new ResponseEntity<List<UserRole>>(userByRoleList, HttpStatus.OK);
	}		
	
	/**
	 * 사용자 Role 등록
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/roleUser/create")
	public ResponseEntity<String> createRoleUser(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Nw105m nw105m = new Nw105m();
		WebUtil.bind(model, nw105m);
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw105m.getCoId());
		map.put("role", nw105m.getRole());
		map.put("userId", nw105m.getUserId());		
		
		Nw105m existUserRole = nw105mService.getNw105m(map);
		if (existUserRole != null && existUserRole.getUserId().equalsIgnoreCase(nw105m.getUserId()) 
				&& existUserRole.getRole().equalsIgnoreCase(nw105m.getRole())) {
			result = messageSource.getMessage("resc.msg.userRoleExist", null, response.getLocale());	
			return new ResponseEntity<String>(result, HttpStatus.OK); 			
		}
		
		int rtn = nw105mService.addNw105m(nw105m);
		
		if (rtn > 0) {
			result = messageSource.getMessage("resc.msg.addOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.addFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}
	
	/**
	 * 사용자 Role 등록
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PutMapping(value = "/roleUser/update")
	public ResponseEntity<String> updateRoleUser(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Nw105m nw105m = new Nw105m();
		WebUtil.bind(model, nw105m);
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw105m.getCoId());
		map.put("role", nw105m.getRole());
		map.put("userId", nw105m.getUserId());		
		
		Nw105m existUserRole = nw105mService.getNw105m(map);
		if (existUserRole == null) {
			result = messageSource.getMessage("resc.msg.notExistRoleUser", null, response.getLocale());	
			return new ResponseEntity<String>(result, HttpStatus.OK); 			
		}
		
		int rtn = nw105mService.updateNw105m(nw105m);
		
		if (rtn > 0) {
			result = messageSource.getMessage("resc.msg.modifyOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.modifyFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}
	
	/**
	 * 사용자별 Role 삭제
	 * @param coId
	 * @param boardId
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping(value = "/roleUser/delete/coId/{coId}/role/{role}/userId/{userId}")
	public ResponseEntity<String> deleteRoleUser(@PathVariable("coId") String coId, @PathVariable("role") String role, 
			@PathVariable("userId") String userId, HttpServletRequest request, HttpServletResponse response) {	
		String result = "";		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("role", role);
		map.put("userId", userId);
		
		Nw105m checkRoleUser = nw105mService.getNw105m(map);
		if (checkRoleUser == null) {
			result = messageSource.getMessage("resc.msg.notExistRoleUser", null, response.getLocale());	
			return new ResponseEntity<String>(result, HttpStatus.OK); 
		}
		
		int cnt = nw105mService.deleteNw105m(map);
		
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.deleteOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.deleteFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}	
}
