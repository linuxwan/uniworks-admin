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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
import org.uniworks.groupware.admin.common.util.DateUtil;
import org.uniworks.groupware.admin.domain.HumanResource;
import org.uniworks.groupware.admin.domain.Nw100m;
import org.uniworks.groupware.admin.domain.User;
import org.uniworks.groupware.admin.service.HumanResourceService;
import org.uniworks.groupware.admin.service.Nw100mService;
import org.uniworks.groupware.admin.service.UserService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class UserController {
	@Autowired UserService userService;
	@Autowired Nw100mService nw100mService;
	@Autowired HumanResourceService hrService;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired private MessageSource messageSource;
	
	/**
	 * 사용자 목록 가져오기
	 * @param request
	 * @param coId
	 * @param searchKind
	 * @param searchWord
	 * @param orderBy
	 * @return
	 */
	@GetMapping(value = "/user/coId/{coId}/searchKind/{searchKind}/searchWord/{searchWord}/orderBy/{orderBy}")
	public ResponseEntity<List<User>> getUserList(HttpServletRequest request, @PathVariable("coId") String coId, 
			@PathVariable("searchKind") String searchKind, @PathVariable("searchWord") String searchWord, @PathVariable("orderBy") String orderBy) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");		
		//검색어가 없을 경우 0값을 URL에 포함 > 0값을 지우는 작업
		if (searchWord.equalsIgnoreCase("0")) searchWord = "";
		
		Map<String, Object> map = new HashMap<String, Object>();	
		map.put("coId", coId);
		map.put("lang", userSession.getLang());
		map.put("searchKind", searchKind);
		map.put("searchWord", searchWord);
		map.put("orderBy", orderBy);
		
		List<User> userList = userService.getUserList(map);
		
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}
	
	/**
	 * 사용자 등록
	 * @param nw100m
	 * @param request
	 * @param response
	 * @param ucBuilder
	 * @return
	 */
	@PostMapping(value = "/user/create")
	public ResponseEntity<String> createUser(@RequestBody final Nw100m nw100m, HttpServletRequest request, HttpServletResponse response, final UriComponentsBuilder ucBuilder) {
		String result = "";	
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");		
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw100m.getCoId());
		map.put("userId", nw100m.getUserId());
		map.put("empNo", nw100m.getUserId());
		map.put("lang", userSession.getLang());
		
		//인사정보에 등록된 사용자인지 확인
		HumanResource hr = hrService.getByHumanResource(map);
		if (hr == null) {			
			result = messageSource.getMessage("resc.msg.empNotExist", new String[] {"", nw100m.getUserId()}, response.getLocale());
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
		
		//이미 사용자 등록이 된 사용자인지 확인
		User existUser = userService.getUser(map);
		if (existUser != null) {
			result = messageSource.getMessage("resc.msg.empExist", new String[] {existUser.getEmpName(), existUser.getUserId()}, response.getLocale());
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
		
		nw100m.setPswd(passwordEncoder.encode(nw100m.getPswd()));
		nw100m.setCnfmPswd(passwordEncoder.encode(nw100m.getCnfmPswd()));
		
		int cnt = nw100mService.addNw100m(nw100m);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.addOk", null, response.getLocale());
		} else {
			result = messageSource.getMessage("resc.msg.addFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * 사용자 정보 수정(업데이트)
	 * @param nw100m
	 * @param request
	 * @param response
	 * @param ucBuilder
	 * @return
	 */
	@PutMapping(value = "/user/update")
	public ResponseEntity<String> updateUser(@RequestBody final Nw100m nw100m, HttpServletRequest request, HttpServletResponse response, final UriComponentsBuilder ucBuilder) {
		String result = "";	
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");		
		nw100m.setPswd(passwordEncoder.encode(nw100m.getPswd()));
		nw100m.setCnfmPswd(passwordEncoder.encode(nw100m.getCnfmPswd()));
		nw100m.setPswdChngDate(DateUtil.getCurrentDate());
		nw100m.setCnfmPswdChngDate(DateUtil.getCurrentDate());
		
		int cnt = nw100mService.updateNw100m(nw100m);
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.modifyOk", null, response.getLocale());
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
