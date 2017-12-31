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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
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
	
	/**
	 * 관리자 목록을 가져온다.
	 * 로그인한 관리자의 Roll과 회사코드를 확인해서 관리자 목록을 제공
	 * SYS_ADM - 시스템 관리자 (등록된 관리자 전체 목록 제공)
	 * CNT_ADM - 회사별 관리자 (자기 회사에 대해서 등록된 관리자 목록만 제공)
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/admin")
	public ResponseEntity<List<Cm010c>> getAdminList(HttpServletRequest request) {		
		//Session 정보를 가져온다.
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adminType", userSession.getAdminType());
		map.put("coId",  userSession.getCoId());
		List<Cm010c> returnList = cm010cService.getCm010cList(map);
		logger.debug("adminType: " + userSession.getAdminType());
		return new ResponseEntity<List<Cm010c>>(returnList, HttpStatus.OK);
	}	
}
