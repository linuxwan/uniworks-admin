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
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.domain.HumanResource;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;
import org.uniworks.groupware.admin.service.HumanResourceService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class HumanResourceController {
	private static final Logger logger = LoggerFactory.getLogger(HumanResourceController.class);
	@Autowired private CommonService commonService;
	@Autowired private MessageSource messageSource;
	@Autowired private Hr001mService hr001mService;
	@Autowired private HumanResourceService hrService;
	
	/**
	 * 조직에 소속된 멤버 모두 다 가져온다.
	 * baseOganLev보다 작을 경우에는 해당 조직의 보직자만 가져오고, 그렇지 않은 경우에는 조직에 소속된 구성원 모두를 가져온다.
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/hr/coId/{coId}/oganLev/{oganLev}/oganCode/{oganCode}/workIndc/{workIndc}")
	public ResponseEntity<List<HumanResource>> getOganMemberList(HttpServletRequest request, @PathVariable("coId") String coId, 
			@PathVariable("oganLev") String oganLev, @PathVariable("oganCode") String oganCode, @PathVariable("workIndc") String workIndc) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");		
		String pstnIndc;
		Map<String, Object> map = new HashMap<String, Object>();	
		map.put("coId", coId);
		map.put("lang", userSession.getLang());
		
		//회사 정보에서 baseOganLev 정보를 가져온다.
		Hr001m hr001m = hr001mService.getHr001m(map);
				
		int baseOganLev = Integer.parseInt(hr001m.getBaseOganLev());		
		map.put("baseOganLev", Integer.parseInt(oganLev));
		if (baseOganLev > Integer.parseInt(oganLev)) {			
			pstnIndc = "Y";
		} else {			
			pstnIndc = "N";			
		}
		map.put("pstnIndc", pstnIndc);
		
		if (workIndc.equals("")) workIndc = "1";
		
		map.put("oganCode", oganCode);
		map.put("pstnIndc", pstnIndc);
		map.put("workIndc", workIndc);
		
		List<HumanResource> hrList = hrService.getByOganLevelEmpList(map);			
		
		return new ResponseEntity<List<HumanResource>>(hrList, HttpStatus.OK);
	}
}
