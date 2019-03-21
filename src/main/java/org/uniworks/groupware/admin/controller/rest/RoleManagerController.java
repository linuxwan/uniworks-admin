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
import org.uniworks.groupware.admin.common.util.DateUtil;
import org.uniworks.groupware.admin.common.util.WebUtil;
import org.uniworks.groupware.admin.domain.Nw106m;
import org.uniworks.groupware.admin.service.Nw105mService;
import org.uniworks.groupware.admin.service.Nw106mService;

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
		
		nw106m.setCrtId(userSession.getAdminId());
		nw106m.setCrtDate(DateUtil.getCurrentDate());
		
		int rtn = nw106mService.addNw106m(nw106m);
		
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
	public ResponseEntity<String> modifyContent(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Nw106m nw106m = new Nw106m();
		WebUtil.bind(model, nw106m);
		
		nw106m.setChngId(userSession.getAdminId());
		nw106m.setChngDate(DateUtil.getCurrentDate());
		
		int rtn = nw106mService.updateNw106m(nw106m);
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
	public ResponseEntity<String> deleteBoardMaster(@PathVariable("coId") String coId, @PathVariable("role") String role, 
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
		
		int cnt = nw106mService.deleteNw106m(map);
		
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.deleteOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.deleteFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}
}
