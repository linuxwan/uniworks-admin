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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
import org.uniworks.groupware.admin.domain.GroupInfo;
import org.uniworks.groupware.admin.service.CommonService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class GroupManagerController {
	private static final Logger logger = LoggerFactory.getLogger(GroupManagerController.class);
	@Autowired CommonService commonService;
	
	/**
	 * 그룹 검색
	 * @param request
	 * @param coId
	 * @param searchKind
	 * @param searchWord
	 * @param orderBy
	 * @return
	 */
	@GetMapping(value = "/group/search/coId/{coId}/searchKind/{searchKind}/searchWord/{searchWord}")
	public ResponseEntity<List<GroupInfo>> getSearchGroupList(HttpServletRequest request, @PathVariable("coId") String coId, 
				@PathVariable("searchKind") String searchKind, @PathVariable("searchWord") String searchWord) {		
		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", userSession.getLang());		
		map.put("coId", coId);
		map.put("searchKind", searchKind);
		if (searchWord.equals("0")) searchWord = "%";
		map.put("searchWord", searchWord);
		
		List<GroupInfo> grpList = commonService.getGroupListBySearch(map);
		return new ResponseEntity<List<GroupInfo>>(grpList, HttpStatus.OK);
	}
}
