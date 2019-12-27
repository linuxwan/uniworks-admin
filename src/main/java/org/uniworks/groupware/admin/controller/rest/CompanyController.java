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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import org.uniworks.groupware.admin.common.util.StringUtil;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.domain.Hr001mExtend;
import org.uniworks.groupware.admin.service.Hr001mService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class CompanyController {
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	@Autowired private Hr001mService hr001mService;
	
	/**
	 * 회사코드 목록을 모두 다 가져온다.
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/company")
	public ResponseEntity<List<Hr001m>> getCompanyListAll(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();		
		
		//로그인한 사용자의 UserName과 Authority 정보를 가져온다.
		String coId = SecurityUtil.getUserName().substring(0,  4);
		String adminType = SecurityUtil.getAuthority();
		map.put("adminType", adminType);
		map.put("coId",  coId);
		
		List<Hr001m> list = hr001mService.getHr001mList(map);		
		return new ResponseEntity<List<Hr001m>>(list, HttpStatus.OK);
	}
	
	/**
	 * 회사 상세 정보를 가져온다. - 현재까지 유효한 회사 정보만 가져온다.
	 * @param prntCoCode
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/company/coId/{coId}")
	public ResponseEntity<Hr001m> getCompanyListByParentCoCode(@PathVariable("coId") String coId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		
		Hr001m hr001m = hr001mService.getHr001m(map);
				
		return new ResponseEntity<Hr001m>(hr001m, HttpStatus.OK);
	}
	
	/**
	 * 회사 상세 정보를 가져온다. - 회사코드와 시작일자를 기준으로 회사 정보를 가져온다. 사용하지 않는 회사 정보도 가져온다.
	 * @param prntCoCode
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/company/coId/{coId}/stDate/{stDate}")
	public ResponseEntity<Hr001m> getCompanyListByParentCoCodeStDate(@PathVariable("coId") String coId, @PathVariable("stDate") String stDate, 
				HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("stDate", stDate);
		
		Hr001m hr001m = hr001mService.getHr001m(map);
				
		return new ResponseEntity<Hr001m>(hr001m, HttpStatus.OK);
	}
	
	/**
	 * 회사 정보를 등록한다.
	 * @param hr001m
	 * @param ucBuilder
	 * @return
	 */
	@Secured("SYS_ADM")
	@PostMapping(value = "/company/create")
	public ResponseEntity<Hr001m> createCompany(@RequestBody final Hr001m hr001m, final UriComponentsBuilder ucBuilder) {
		ResponseEntity<Hr001m> returnResEnty = null;
		if (hr001mService.isCompanyExist(hr001m.getCoId())) {
			return new ResponseEntity<Hr001m>(HttpStatus.CONFLICT);
		}
		
		//입력된 일자의 .(dot)를 제거한다.
		hr001m.setStDate(StringUtil.delDash(hr001m.getStDate()));
		hr001m.setFinDate(StringUtil.delDash(hr001m.getFinDate()));
		int cnt = hr001mService.addHr001m(hr001m);
		
		if (cnt > 0) {
			returnResEnty = new ResponseEntity<Hr001m>(hr001m, HttpStatus.CREATED);
		} else {
			returnResEnty = new ResponseEntity<Hr001m>(hr001m, HttpStatus.EXPECTATION_FAILED);
		}
		return returnResEnty;
	}
	
	/**
	 * 회사 정보를 수정한다.
	 * @param hr001m
	 * @return
	 */
	@PutMapping(value = "/company/update")
	public ResponseEntity<Hr001m> updateCompany(@RequestBody final Hr001m hr001m) {
		//입력된 일자의 .(dot)를 제거한다.
		hr001m.setStDate(StringUtil.delDash(hr001m.getStDate()));
		hr001m.setFinDate(StringUtil.delDash(hr001m.getFinDate()));
		
		int cnt = hr001mService.updateHr001m(hr001m);
		return new ResponseEntity<Hr001m>(hr001m, HttpStatus.OK);
	}
	
	/**
	 * 회사 정보를 삭제한다.
	 * @param coId
	 * @param stDate
	 * @return
	 */
	@Secured("SYS_ADM")
	@DeleteMapping(value = "/company/delete/{coId}/stDate/{stDate}")
	public ResponseEntity<Void> deleteCompany(@PathVariable("coId") final String coId, @PathVariable("stDate") final String stDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("stDate", StringUtil.delDash(stDate));
		int cnt = hr001mService.deleteHr001m(map);
		
		if (cnt > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
}
