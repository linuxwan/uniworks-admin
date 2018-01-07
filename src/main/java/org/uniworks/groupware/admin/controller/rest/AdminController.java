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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.uniworks.groupware.admin.common.util.SecurityUtil;
import org.uniworks.groupware.admin.domain.Cm010c;
import org.uniworks.groupware.admin.service.Cm010cService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Autowired Cm010cService cm010cService;
	@Autowired PasswordEncoder passwordEncoder;
	
	/**
	 * 관리자 목록을 가져온다.
	 * 로그인한 관리자의 Roll과 회사코드를 확인해서 관리자 목록을 제공
	 * SYS_ADM - 시스템 관리자 (등록된 관리자 전체 목록 제공)
	 * GRP_AMD - 그룹 관리자 (모회사/자회사 모두를 관리하는 관리자)
	 * CNT_ADM - 회사별 관리자 (자기 회사에 대해서 등록된 관리자 목록만 제공)
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/admin")
	public ResponseEntity<List<Cm010c>> getAdminList(HttpServletRequest request) {		
		//로그인한 사용자의 UserName과 Authority 정보를 가져온다.
		String coId = SecurityUtil.getUserName().substring(0,  4);
		String adminType = SecurityUtil.getAuthority();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adminType", adminType);
		map.put("coId",  coId);
		List<Cm010c> returnList = cm010cService.getCm010cList(map);
		logger.debug("adminType: " + adminType);
		return new ResponseEntity<List<Cm010c>>(returnList, HttpStatus.OK);
	}	
	
	/**
	 * 관리자 상세 정보를 가져온다.
	 * @param coId
	 * @param adminId
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/admin/coId/{coId}/adminId/{adminId}") 
	public ResponseEntity<Cm010c> getAdminInfo(@PathVariable("coId") String coId, @PathVariable("adminId") String adminId, 
	HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("adminId", adminId);
		
		Cm010c cm010c = cm010cService.getCm010c(map);
		return new ResponseEntity<Cm010c>(cm010c, HttpStatus.OK);
	}
	
	/**
	 * 관리자 정보를 등록한다.
	 * @param cm010c
	 * @param ucBuilder
	 * @return
	 */
	@PostMapping(value = "/admin/create")
	public ResponseEntity<Cm010c> createAdminUser(@RequestBody final Cm010c cm010c, final UriComponentsBuilder ucBuilder) {		
		ResponseEntity<Cm010c> returnResEnty = null;
		if (cm010cService.isAdminExist(cm010c.getCoId(), cm010c.getAdminId())) {
			return new ResponseEntity<Cm010c>(HttpStatus.CONFLICT);
		}
		
		//입력한 비밀번호를 암호화
		cm010c.setPswd(passwordEncoder.encode(cm010c.getPswd()));
		
		int cnt = cm010cService.addCm010c(cm010c);
		
		if (cnt > 0) {
			returnResEnty = new ResponseEntity<Cm010c>(cm010c, HttpStatus.CREATED);
		} else {
			returnResEnty = new ResponseEntity<Cm010c>(cm010c, HttpStatus.EXPECTATION_FAILED);
		}
		return returnResEnty;
	}
	
	/**
	 * 관리자 정보를 수정한다.
	 * @param cm010c
	 * @return
	 */
	@PutMapping(value = "/admin/update")
	public ResponseEntity<Cm010c> updateCompany(@RequestBody final Cm010c cm010c) {				
		int cnt = cm010cService.updateCm010c(cm010c);
		return new ResponseEntity<Cm010c>(cm010c, HttpStatus.OK);
	}
	
	/**
	 * 관리자 정보를 삭제한다.
	 * @param coId
	 * @param adminId
	 * @return
	 */
	@DeleteMapping(value = "/admin/delete/coId/{coId}/adminId/{adminId}")
	public ResponseEntity<Void> deleteCompany(@PathVariable("coId") final String coId, @PathVariable("adminId") final String adminId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("adminId", adminId);
		int cnt = cm010cService.deleteCm010c(map);
		
		if (cnt > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
}
