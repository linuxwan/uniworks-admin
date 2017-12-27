/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.service.internal; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.uniworks.groupware.admin.domain.Hr001m;
import org.uniworks.groupware.admin.mapper.Hr001mMapper;
import org.uniworks.groupware.admin.service.Hr001mService; 

@Service 
@Transactional(readOnly = true) 
public class Hr001mServiceImpl implements Hr001mService { 
	@Autowired Hr001mMapper hr001mMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Hr001m> getHr001mList(Map<String, Object> map) { 
		return hr001mMapper.select(map);
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Hr001m getHr001m(Map<String, Object> map) { 
		return hr001mMapper.selectByPrimaryKey(map); 
	} 
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addHr001m(Hr001m hr001m) { 
		return hr001mMapper.insert(hr001m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateHr001m(Hr001m hr001m) { 
		return hr001mMapper.updateByPrimaryKey(hr001m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteHr001m(Map<String, Object> map) { 
		return hr001mMapper.deleteByPrimaryKey(map); 
	} 
	
	/**
	 * 회사가 존재하는지 확인해서 존재하면 true, 그렇지 않으면 false를 리턴
	 * @param hr001m
	 * @return
	 */
	public boolean isCompanyExist(String coId) {
		boolean chk = false;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);

		Hr001m hr001m = hr001mMapper.selectByPrimaryKey(map);
		if (hr001m != null && hr001m.getCoId().length() > 0) chk = true;
		return chk;
	}
} 
