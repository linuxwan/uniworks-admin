/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service.internal;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uniworks.groupware.admin.domain.ApprovalMasterInfo;
import org.uniworks.groupware.admin.domain.Nw013m;
import org.uniworks.groupware.admin.domain.Nw014m;
import org.uniworks.groupware.admin.mapper.ApprovalMasterMapper;
import org.uniworks.groupware.admin.mapper.Nw013mMapper;
import org.uniworks.groupware.admin.mapper.Nw014mMapper;
import org.uniworks.groupware.admin.service.ApprovalMasterService;

/**
 * @author Park Chung Wan
 *
 */
@Service 
@Transactional(readOnly = true) 
public class ApprovalMasterServiceImpl implements ApprovalMasterService {
	@Autowired ApprovalMasterMapper apprMaster;
	@Autowired Nw013mMapper nw013mMapper;
	@Autowired Nw014mMapper nw014mMapper;
	
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
	public int addApprovalTypeInfo(Nw013m nw013m, List<Nw014m> nw014mList) {
		int cnt = 0;
		
		if (nw013m == null || nw014mList.size() < 1) return cnt;
		
		cnt = nw013mMapper.insert(nw013m);
		
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
}
