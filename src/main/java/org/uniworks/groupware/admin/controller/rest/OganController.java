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
import org.uniworks.groupware.admin.common.util.StringUtil;
import org.uniworks.groupware.admin.domain.Om001m;
import org.uniworks.groupware.admin.service.Om001mService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class OganController {
	private static final Logger logger = LoggerFactory.getLogger(OganController.class);
	
	@Autowired Om001mService om001mService;	
	
	/**
	 * 조직 목록을 모두 다 가져온다.
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/oganList")
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
}
