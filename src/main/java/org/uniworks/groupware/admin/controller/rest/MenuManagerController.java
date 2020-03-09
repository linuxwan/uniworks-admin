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
import org.uniworks.groupware.admin.domain.MenuInfo;
import org.uniworks.groupware.admin.domain.Nw020m;
import org.uniworks.groupware.admin.domain.Nw021m;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.MenuService;
import org.uniworks.groupware.admin.service.Nw020mService;
import org.uniworks.groupware.admin.service.Nw021mService;

/**
 * @author Park Chung Wan
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class MenuManagerController {
	private static final Logger logger = LoggerFactory.getLogger(MenuManagerController.class);
	@Autowired MenuService menuService;
	@Autowired CommonService commonService;
	@Autowired Nw020mService nw020mService;
	@Autowired Nw021mService nw021mService;
	@Autowired private MessageSource messageSource;
	
	/**
	 * 등록된 메뉴 목록 가져오기 
	 * @param request
	 * @param coId
	 * @return
	 */
	@GetMapping(value = "/menuList/coId/{coId}")
	public ResponseEntity<List<MenuInfo>> getMenuList(HttpServletRequest request, @PathVariable("coId") String coId) {
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("lang", userSession.getLang());
		
		List<MenuInfo> list = menuService.getMenuAllList(map);		
		
		return new ResponseEntity<List<MenuInfo>>(list, HttpStatus.OK);
	}
	
	/**
	 * 메뉴 등록
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/menu/create")
	public ResponseEntity<String> createMenu(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Nw020m nw020m = new Nw020m();
		WebUtil.bind(model, nw020m);
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", model.get("coId"));
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		ArrayList<Nw021m> nw021mList = new ArrayList<Nw021m>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", model.get("coId"));
		map.put("menuId", model.get("menuId"));
		nw020m.setCrtDate(DateUtil.getCurrentDate());
		nw020m.setCrtId(userSession.getAdminId());
		for (CommonCode commonCode : langList) {
			String menuDsplName = StringUtil.null2void((String)model.get("menuDsplName_" + commonCode.getRescKeyValue()));	
			String menuDesc = StringUtil.null2void((String)model.get("menuDesc_" + commonCode.getRescKeyValue()));
			map.put("locale", commonCode.getRescKeyValue());
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				nw020m.setMenuDsplName(menuDsplName);
				nw020m.setMenuDesc(menuDesc);				
				Nw021m tempNw021m = nw021mService.getNw021m(map);
				if (tempNw021m != null && tempNw021m.getMenuId().equalsIgnoreCase(nw020m.getMenuId())) {
					result = messageSource.getMessage("resc.msg.menuExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
			
			Nw021m nw021m = new Nw021m();
			nw021m.setCoId(nw020m.getCoId());
			nw021m.setLocale(commonCode.getRescKeyValue());
			nw021m.setMenuId(nw020m.getMenuId());
			nw021m.setMenuDsplName(menuDsplName);
			nw021m.setMenuDesc(menuDesc);
			
			nw021mList.add(nw021m);
		}		
		
		int rtn = menuService.addMenu(nw020m, nw021mList);
		if (rtn > 0) {
			result = messageSource.getMessage("resc.msg.addOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.addFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * 메뉴 수정
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PutMapping(value = "/menu/modify")
	public ResponseEntity<String> modifyMenu(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Nw020m nw020m = new Nw020m();
		WebUtil.bind(model, nw020m);
		
		Map<String, Object> lanMap = new HashMap<String, Object>();
		lanMap.put("lang", userSession.getLang());
		lanMap.put("coId", model.get("coId"));
		lanMap.put("majCode", "CD001"); //지원언어가 저장되어져 있는 주코드 CD001
		lanMap.put("orderBy", "rescKey");	//코드 정렬 방법 셋팅
		
		String defaultLang = ApplicationConfigReader.get("default.language");
		List<CommonCode> langList = commonService.getCommonSubCodeList(lanMap);
		ArrayList<Nw021m> nw021mList = new ArrayList<Nw021m>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", model.get("coId"));
		map.put("menuId", model.get("menuId"));
		nw020m.setChngDate(DateUtil.getCurrentDate());
		nw020m.setChngId(userSession.getAdminId());
		for (CommonCode commonCode : langList) {
			String menuDsplName = StringUtil.null2void((String)model.get("menuDsplName_" + commonCode.getRescKeyValue()));	
			String menuDesc = StringUtil.null2void((String)model.get("menuDesc_" + commonCode.getRescKeyValue()));
			map.put("locale", commonCode.getRescKeyValue());
			if (defaultLang.equalsIgnoreCase(commonCode.getRescKeyValue())) {
				nw020m.setMenuDsplName(menuDsplName);
				nw020m.setMenuDesc(menuDesc);				
				Nw021m tempNw021m = nw021mService.getNw021m(map);
				if (tempNw021m != null && !tempNw021m.getMenuId().equalsIgnoreCase(nw020m.getMenuId())) {
					result = messageSource.getMessage("resc.msg.menuLangNotExist", null, response.getLocale());
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
			
			Nw021m nw021m = new Nw021m();
			nw021m.setCoId(nw020m.getCoId());
			nw021m.setLocale(commonCode.getRescKeyValue());
			nw021m.setMenuId(nw020m.getMenuId());
			nw021m.setMenuDsplName(menuDsplName);
			nw021m.setMenuDesc(menuDesc);
			
			nw021mList.add(nw021m);
		}		
		
		int rtn = menuService.modifyMenu(nw020m, nw021mList);
		if (rtn > 0) {
			result = messageSource.getMessage("resc.msg.modifyOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.modifyFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	/**
	 * 메뉴 삭제
	 * @param coId
	 * @param menuId
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping(value = "/menu/delete/coId/{coId}/menuId/{menuId}")
	public ResponseEntity<String> deleteMenu(@PathVariable("coId") String coId, @PathVariable("menuId") String menuId, 
			HttpServletRequest request, HttpServletResponse response) {	
		String result = "";		
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("menuId", menuId);
		
		Nw020m nw020m = nw020mService.getNw020m(map);		
		if (nw020m == null) {
			result = messageSource.getMessage("resc.msg.menuNotExist", null, response.getLocale());	
			return new ResponseEntity<String>(result, HttpStatus.OK); 
		}
		
		int cnt = menuService.deleteMenu(coId, menuId);
		
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.deleteOk", null, response.getLocale());			
		} else {
			result = messageSource.getMessage("resc.msg.deleteFail", null, response.getLocale());
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}	
}
