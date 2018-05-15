/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.controller.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
import org.uniworks.groupware.admin.common.util.CommUtil;
import org.uniworks.groupware.admin.common.util.DateUtil;
import org.uniworks.groupware.admin.common.util.StringUtil;
import org.uniworks.groupware.admin.common.util.WebUtil;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.domain.Hr010m;
import org.uniworks.groupware.admin.domain.Hr011m;
import org.uniworks.groupware.admin.domain.HumanResource;
import org.uniworks.groupware.admin.domain.Ogan;
import org.uniworks.groupware.admin.domain.Om001m;
import org.uniworks.groupware.admin.service.CommonService;
import org.uniworks.groupware.admin.service.Hr001mService;
import org.uniworks.groupware.admin.service.HumanResourceService;
import org.uniworks.groupware.admin.service.OganService;

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
	@Autowired private OganService oganService;
	
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
	
	/**
	 * 인사정보를 등록한다.	
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/hr/create")
	public ResponseEntity<String> createHr(@RequestBody Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {	
		//Session 정보를 가져온다.		
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String result = "";
		Date crntDate = DateUtil.getCurrentDate();
		String coId = StringUtil.delDash((String)model.get("coId"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		// 지원 언어 목록을 가져온다.
		Hr001m hr001m = hr001mService.getHr001m(map);
		if (hr001m == null) {
			result = messageSource.getMessage("resc.msg.coIdNotExist", null, response.getLocale());
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
		String supportLang = hr001m.getSprtLang();
		String baseOganLev = hr001m.getBaseOganLev();
		
		//yyyy-MM-dd 형식으로 넘어온 날짜를 yyyyMMdd형식으로 변환
		String birthDate = StringUtil.delDash((String)model.get("birthDate"));
		String entrDate = StringUtil.delDash((String)model.get("entrDate"));		
		String rsgnDate = StringUtil.delDash((String)model.get("rsgnDate"));		
		String marriageDate = StringUtil.delDash((String)model.get("marriageDate"));
		String offcOrdDate = StringUtil.delDash((String)model.get("offcOrdDate"));				
		
		model.put("birthDate", birthDate);
		model.put("entrDate", entrDate);
		model.put("rsgnDate", rsgnDate);
		model.put("marriageDate", marriageDate);
		model.put("offcOrdDate", offcOrdDate);
		
		String workOganCode = StringUtil.null2void((String)model.get("workOganCode"));
		String workOganLev = StringUtil.null2void((String)model.get("workOganLev"));
		String orgnOganCode = StringUtil.null2void((String)model.get("orgnOganCode"));
		String orgnOganLev = StringUtil.null2void((String)model.get("orgnOganLev"));
				
		Hr010m hr010m = new Hr010m();
		WebUtil.bind(model, hr010m);
		
		hr010m.setBaseAsgnOganLev(workOganLev);
		hr010m.setBaseOrgnOganLev(orgnOganLev);
		
		//사번을 Base64로 인코딩해서 sysUserId에 할당		
		hr010m.setSysUserId(CommUtil.encodeBase64String(hr010m.getEmpNo()));
		
		ArrayList<Hr011m> arr = new ArrayList<Hr011m>();
		// 지원언어 목록별로 성명을 저장한다.
		StringTokenizer st = new StringTokenizer(supportLang, "|");
		String strLang= "";
		while (st.hasMoreTokens()) {
			Hr011m hr011m = new Hr011m();
			hr011m.setCoId(hr010m.getCoId());
			hr011m.setEmpNo(hr010m.getEmpNo());
			
			strLang = st.nextToken();
			
			if (strLang.equalsIgnoreCase("en")) {
				hr011m.setLocale(strLang);
				hr011m.setEmpName(hr010m.getEmpNameEng());
			} else if (strLang.equalsIgnoreCase("ko")) {
				hr011m.setLocale(strLang);
				hr011m.setEmpName(hr010m.getEmpNameKor());
			} else {
				//ko와 en을 제외한 언어일 경우 - 추가적인 개선이 필요함. 직원등록화면의 다국어 입력 지원 및 파라미터 수정 필요[필수]
			}
			
			arr.add(hr011m);
		}
		
		//근무조직 정보를 가져와서 Hr001m에 할당
		Ogan asgnOgan = new Ogan();
		asgnOgan.setCoId(hr010m.getCoId());
		asgnOgan.setHighOganCode(workOganCode);
		asgnOgan.setHighOganLev(workOganLev);
		
		oganService.clearArrayList();
		oganService.getHighOganInfoRecursiveFunction(asgnOgan, userSession.getLang());
		hr010m = setWorkOganInfo(hr010m, oganService.getHighOganList(), baseOganLev);
		
		//원소속조직정보를 가져와서 Hr001m에 할당
		Ogan orgnOgan = new Ogan();
		orgnOgan.setCoId(hr010m.getCoId());
		orgnOgan.setHighOganCode(orgnOganCode);
		orgnOgan.setHighOganLev(orgnOganLev);
		
		oganService.clearArrayList();
		oganService.getHighOganInfoRecursiveFunction(orgnOgan, userSession.getLang());
		hr010m = setOrgnOganInfo(hr010m, oganService.getHighOganList(), baseOganLev);
		
		hrService.addEmpInfo(hr010m, arr);
		
		return new ResponseEntity<String>(result, HttpStatus.OK); 
	}
	
	/**
	 * Hr010m에 각 레벨별 조직코드와 조직명칭을 셋팅한다.(근무조직)
	 * @param record
	 * @param oganInfo
	 * @return
	 */
	private Hr010m setWorkOganInfo(Hr010m record, ArrayList<Ogan> oganInfo, String baseOganLev) {
		Ogan workOgan = null;
		int rowCnt = oganInfo.size();
		String oganCode = "";
		String oganName = "";

		for(int i = 0; i < rowCnt; i++) {
			workOgan = (Ogan) oganInfo.get(i);
			
			if (rowCnt == Integer.parseInt(workOgan.getOganLev())) {
				oganCode = workOgan.getOganCode();
				oganName = workOgan.getOganName();
			}
			
			switch(Integer.parseInt(workOgan.getOganLev())) {
				case 1:
					record.setAsgnOganCode1(workOgan.getOganCode());
					record.setAsgnOganDesc1(workOgan.getOganName());
					break;
				case 2:
					record.setAsgnOganCode2(workOgan.getOganCode());
					record.setAsgnOganDesc2(workOgan.getOganName());
					break;
				case 3:
					record.setAsgnOganCode3(workOgan.getOganCode());
					record.setAsgnOganDesc3(workOgan.getOganName());
					break;
				case 4:
					record.setAsgnOganCode4(workOgan.getOganCode());
					record.setAsgnOganDesc4(workOgan.getOganName());
					break;
				case 5:
					record.setAsgnOganCode5(workOgan.getOganCode());
					record.setAsgnOganDesc5(workOgan.getOganName());
					break;
				case 6:
					record.setAsgnOganCode6(workOgan.getOganCode());
					record.setAsgnOganDesc6(workOgan.getOganName());
					break;
				case 7:
					record.setAsgnOganCode7(workOgan.getOganCode());
					record.setAsgnOganDesc7(workOgan.getOganName());
					break;
				case 8:
					record.setAsgnOganCode8(workOgan.getOganCode());
					record.setAsgnOganDesc8(workOgan.getOganName());
					break;
				case 9:
					record.setAsgnOganCode9(workOgan.getOganCode());
					record.setAsgnOganDesc9(workOgan.getOganName());
					break;
				case 10:
					record.setAsgnOganCode10(workOgan.getOganCode());
					record.setAsgnOganDesc10(workOgan.getOganName());
					break;
				case 11:
					record.setAsgnOganCode11(workOgan.getOganCode());
					record.setAsgnOganDesc11(workOgan.getOganName());
					break;
				case 12:
					record.setAsgnOganCode12(workOgan.getOganCode());
					record.setAsgnOganDesc12(workOgan.getOganName());
					break;
			}
		}

		if (rowCnt < Integer.parseInt(baseOganLev)) {
			switch (rowCnt) {
				case 1:
					record.setAsgnOganCode2(oganCode);
					record.setAsgnOganDesc2(oganName);
				case 2:
					record.setAsgnOganCode3(oganCode);
					record.setAsgnOganDesc3(oganName);
				case 3:
					if (Integer.parseInt(baseOganLev) == 3) break;
					record.setAsgnOganCode4(oganCode);
					record.setAsgnOganDesc4(oganName);
				case 4:
					if (Integer.parseInt(baseOganLev) == 4) break;
					record.setAsgnOganCode5(oganCode);
					record.setAsgnOganDesc5(oganName);
			}
		}
		
		return record;
	}
	
	/**
	 * Hr010m에 각 레벨별 조직코드와 조직명칭을 셋팅한다.(원소속조직)
	 * @param record
	 * @param oganInfo
	 * @return
	 */
	private Hr010m setOrgnOganInfo(Hr010m record, ArrayList<Ogan> oganInfo, String baseOganLev) {
		Ogan oganOgan = null;
		int rowCnt = oganInfo.size();
		String oganCode = "";
		String oganName = "";
		
		for(int i = 0; i < rowCnt; i++) {
			oganOgan = (Ogan) oganInfo.get(i);
			
			if (rowCnt == Integer.parseInt(oganOgan.getOganLev())) {
				oganCode = oganOgan.getOganCode();
				oganName = oganOgan.getOganName();
			}
			
			switch(Integer.parseInt(oganOgan.getOganLev())) {
				case 1:
					record.setOrgnOganCode1(oganOgan.getOganCode());
					record.setOrgnOganDesc1(oganOgan.getOganName());
					break;
				case 2:
					record.setOrgnOganCode2(oganOgan.getOganCode());
					record.setOrgnOganDesc2(oganOgan.getOganName());
					break;
				case 3:
					record.setOrgnOganCode3(oganOgan.getOganCode());
					record.setOrgnOganDesc3(oganOgan.getOganName());
					break;
				case 4:
					record.setOrgnOganCode4(oganOgan.getOganCode());
					record.setOrgnOganDesc4(oganOgan.getOganName());
					break;
				case 5:
					record.setOrgnOganCode5(oganOgan.getOganCode());
					record.setOrgnOganDesc5(oganOgan.getOganName());
					break;
				case 6:
					record.setOrgnOganCode6(oganOgan.getOganCode());
					record.setOrgnOganDesc6(oganOgan.getOganName());
					break;
				case 7:
					record.setOrgnOganCode7(oganOgan.getOganCode());
					record.setOrgnOganDesc7(oganOgan.getOganName());
					break;
				case 8:
					record.setOrgnOganCode8(oganOgan.getOganCode());
					record.setOrgnOganDesc8(oganOgan.getOganName());
					break;
				case 9:
					record.setOrgnOganCode9(oganOgan.getOganCode());
					record.setOrgnOganDesc9(oganOgan.getOganName());
					break;
				case 10:
					record.setOrgnOganCode10(oganOgan.getOganCode());
					record.setOrgnOganDesc10(oganOgan.getOganName());
					break;
				case 11:
					record.setOrgnOganCode11(oganOgan.getOganCode());
					record.setOrgnOganDesc11(oganOgan.getOganName());
					break;
				case 12:
					record.setOrgnOganCode12(oganOgan.getOganCode());
					record.setOrgnOganDesc12(oganOgan.getOganName());
					break;
			}
		}
		
		if (rowCnt < Integer.parseInt(baseOganLev)) {
			switch (rowCnt) {
				case 1:
					record.setOrgnOganCode2(oganCode);
					record.setOrgnOganDesc2(oganName);
				case 2:
					record.setOrgnOganCode3(oganCode);
					record.setOrgnOganDesc3(oganName);
				case 3:
					if (Integer.parseInt(baseOganLev) == 3) break;
					record.setOrgnOganCode4(oganCode);
					record.setOrgnOganDesc4(oganName);
				case 4:
					if (Integer.parseInt(baseOganLev) == 4) break;
					record.setOrgnOganCode5(oganCode);
					record.setOrgnOganDesc5(oganName);
			}
		}
		
		return record;
	}
}
