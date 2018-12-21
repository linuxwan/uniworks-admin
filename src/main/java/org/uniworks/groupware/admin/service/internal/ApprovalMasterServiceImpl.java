/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.uniworks.groupware.admin.common.util.DateUtil;
import org.uniworks.groupware.admin.domain.ApprTypeByApprInfo;
import org.uniworks.groupware.admin.domain.ApprovalMasterInfo;
import org.uniworks.groupware.admin.domain.Nw010m;
import org.uniworks.groupware.admin.domain.Nw011m;
import org.uniworks.groupware.admin.domain.Nw012m;
import org.uniworks.groupware.admin.domain.Nw013m;
import org.uniworks.groupware.admin.domain.Nw014m;
import org.uniworks.groupware.admin.domain.Nw015m;
import org.uniworks.groupware.admin.mapper.ApprovalMasterMapper;
import org.uniworks.groupware.admin.mapper.Nw010mMapper;
import org.uniworks.groupware.admin.mapper.Nw011mMapper;
import org.uniworks.groupware.admin.mapper.Nw012mMapper;
import org.uniworks.groupware.admin.mapper.Nw013mMapper;
import org.uniworks.groupware.admin.mapper.Nw014mMapper;
import org.uniworks.groupware.admin.mapper.Nw015mMapper;
import org.uniworks.groupware.admin.service.ApprovalMasterService;

/**
 * @author Park Chung Wan
 *
 */
@Service 
@Transactional(readOnly = true) 
public class ApprovalMasterServiceImpl implements ApprovalMasterService {
	@Autowired ApprovalMasterMapper apprMaster;
	@Autowired Nw010mMapper nw010mMapper;
	@Autowired Nw011mMapper nw011mMapper;
	@Autowired Nw012mMapper nw012mMapper;
	@Autowired Nw013mMapper nw013mMapper;
	@Autowired Nw014mMapper nw014mMapper;
	@Autowired Nw015mMapper nw015mMapper;
	@Autowired MessageSource messageSource;
	
	/**
	 * 결재 마스터 목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<ApprovalMasterInfo> getApprMasterList(Map<String, Object> map) {		
		return apprMaster.selectApprMasterList(map);
	}	
	
	/**
	 * 결재 마스터 정보를 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public ApprovalMasterInfo getApprMasterInfo(Map<String, Object> map) {
		return apprMaster.selectApprMasterInfo(map);
	}
	
	/**
	 * 결재 마스터 정보를 등록한다.
	 * @param nw010m
	 * @param nw011mList
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addApprovalMasterInfo(Nw010m nw010m, List<Nw011m> nw011mList, Nw012m nw012m) {
		int cnt = 0;
		
		if (nw010m == null || nw011mList.size() < 1 || nw012m == null) return cnt;
		
		//결재 마스터 정보 등록
		cnt = nw010mMapper.insert(nw010m);
		if (cnt < 1) return cnt;
		
		for (Nw011m nw011m : nw011mList) {
			//결재 마스터 명칭 다국어 등록
			cnt = nw011mMapper.insert(nw011m);
		}
				
		//결재 마스터 정보의 결재차수 정보를 Insert 한다.
		cnt = nw012mMapper.insert(nw012m);
						
		return cnt;
	}
	
	/**
	 * 결재 마스터 정보를 수정한다.
	 * @param nw010m
	 * @param nw011mList
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int modifyApprovalMasterInfo(Nw010m nw010m, List<Nw011m> nw011mList, Nw012m nw012m) {
		int cnt = 0;
		
		if (nw010m == null || nw011mList.size() < 1 || nw012m == null) return cnt;
		
		//결재 마스터 정보 등록
		cnt = nw010mMapper.updateByPrimaryKey(nw010m);
		if (cnt < 1) return cnt;
		
		Map<String, Object> nw011mMap = new HashMap<String, Object>();
		nw011mMap.put("coId", nw010m.getCoId());
		nw011mMap.put("apprMstId", nw010m.getApprMstId());
		//기존에 등록된 apprMstId별 다국어 명칭 삭제
		cnt = nw011mMapper.deleteByPrimaryKey(nw011mMap);
		
		for (Nw011m nw011m : nw011mList) {
			//결재 마스터 명칭 다국어 등록
			cnt = nw011mMapper.insert(nw011m);
		}
		
		String crntDate = DateUtil.getCurrentDateToString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw012m.getCoId());
		map.put("apprMstId", nw012m.getApprMstId());
		map.put("crntDate", crntDate);
		
		Nw012m tempNw012m = nw012mMapper.selectByCurrentApprLevel(map);
		//현재 등록된 결재 차수가 있을 경우, 기존 결재 차수(APPR_LEVEL)가 다를 경우 정보를 업데이트 한다. (CLS_DATE를 99991231에서 현재 일자로)
		if (tempNw012m != null && tempNw012m.getApprLevel() != nw012m.getApprLevel()) {
			//최근 결재차수 정보를 가져와서 시작일자가 현재일자와 다를 경우
			if (!tempNw012m.getCrtDate().equalsIgnoreCase(crntDate)) {
				tempNw012m.setClsDate(DateUtil.getCurrentDateToString(-1));
				cnt = nw012mMapper.updateByPrimaryKey(tempNw012m);
				//결재 마스터 정보의 결재차수 정보를 Insert 한다.
				cnt = nw012mMapper.insert(nw012m);
			} else { //최근 결재차수 정보의 시작일자가 현재일자와 동일할 경우 Update
				cnt = nw012mMapper.updateByPrimaryKey(nw012m);
			}
		}		
			
		return cnt;
	}
	
	/**
	 * 결재 마스터 정보를 삭제한다.
	 * @param map
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteApprovalMasterInfo(Map<String, Object> map) {
		int cnt = 0;
		
		//결재 유형 정보 삭제
		cnt = nw015mMapper.deleteByApprMstId(map);
		//결재 마스터의 결재 차수 정보 삭제
		cnt = nw012mMapper.deleteByPrimaryKey(map);
		//결재 마스터 다국어 명칭 삭제
		cnt = nw011mMapper.deleteByPrimaryKey(map);
		//결재 마스터 삭제
		cnt = nw010mMapper.deleteByPrimaryKey(map);
		
		return cnt;
	}
	
	/**
	 * 결재 마스터를 기반으로 생성된 결재문서가 있는지 체크해서 결재문서 개수를 반환
	 * @param map
	 * @return
	 */
	@Override
	public int getApprovalDocCount(Map<String, Object> map) {
		return apprMaster.selectApprovalDocCount(map);
	}
	
	/**
	 * 결재 유형 목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<Nw013m> getApprTypeList(Map<String, Object> map) {
		return apprMaster.selectApprTypeList(map);
	}
	
	/**
	 * 결재 유형 정보를 등록한다.
	 * @param nw013m
	 * @param nw014m
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addApprovalTypeInfo(Nw013m nw013m, List<Nw014m> nw014mList) {
		int cnt = 0;
		
		if (nw013m == null || nw014mList.size() < 1) return cnt;
		
		cnt = nw013mMapper.insert(nw013m);
		if (cnt < 1) return cnt;
		
		for (Nw014m nw014m : nw014mList) {
			cnt = nw014mMapper.insert(nw014m);
		}
		
		return cnt;
	}
	
	/**
	 * 결재 유형 정보를 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public Nw013m getApprTypeInfo(Map<String, Object> map) {
		return apprMaster.selectApprTypeInfo(map);
	}
	
	/**
	 * 결재 유형 정보를 Update한다.
	 * @param nw013m
	 * @param nw014mList
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int updateApprovalTypeInfo(Nw013m nw013m, List<Nw014m> nw014mList) {
		int cnt = 0;
		
		if (nw013m == null || nw014mList.size() < 1) return cnt;
		
		cnt = nw013mMapper.updateByPrimaryKey(nw013m);
		if (cnt < 1) return cnt;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw013m.getCoId());
		map.put("apprItemId", nw013m.getApprItemId());
		
		//등록된 결재 유형 다국어 정보를 삭제
		cnt = nw014mMapper.deleteByPrimaryKey(map);

		//결재 유형 다국어 정보를 새로 등록한다.
		for (Nw014m nw014m : nw014mList) {						
			cnt = nw014mMapper.insert(nw014m);
		}
		
		return cnt;
	}
	
	/**
	 * 결재 유형 정보 삭제
	 * @param map
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String deleteApprovalTypeInfo(Map<String, Object> map, Locale locale) {
		int cnt = 0;
		String result = "";
		
		List<Nw015m> nw015mList = nw015mMapper.select(map);
		if (nw015mList.size() > 0) {
			result = messageSource.getMessage("resc.msg.apprTypeApprMstExist", null, locale);
			return result;
		} else {
			cnt = nw014mMapper.deleteByPrimaryKey(map);
			cnt = nw013mMapper.deleteByPrimaryKey(map);
		}
		
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.deleteOk", null, locale);
		} else {
			result = messageSource.getMessage("resc.msg.deleteFail", null, locale);
		}
		
		return result;
	}
	
	/**
	 * 결재 유형별 결재 Master 목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<ApprTypeByApprInfo> getApprTypeByApprInfo(Map<String, Object> map) {
		return apprMaster.selectApprTypeByApprInfo(map);
	}
}
