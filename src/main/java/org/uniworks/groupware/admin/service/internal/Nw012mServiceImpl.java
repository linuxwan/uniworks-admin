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
import org.springframework.transaction.annotation.Propagation; 
import org.springframework.transaction.annotation.Transactional; 

import org.uniworks.groupware.admin.domain.Nw012m; 
import org.uniworks.groupware.admin.mapper.Nw012mMapper; 
import org.uniworks.groupware.admin.service.Nw012mService; 

@Service 
@Transactional(readOnly = true) 
public class Nw012mServiceImpl implements Nw012mService { 
	@Autowired Nw012mMapper nw012mMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Nw012m> getNw012mList(Map<String, Object> map) { 
		return nw012mMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Nw012m getNw012m(Map<String, Object> map) { 
		return nw012mMapper.selectByPrimaryKey(map); 
	} 
	/**
	 * 현재 결재차수 정보를 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public Nw012m getByCurrentApprLevel(java.util.Map<String,Object> map) {
		return nw012mMapper.selectByCurrentApprLevel(map);
	}
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addNw012m(Nw012m nw012m) { 
		return nw012mMapper.insert(nw012m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateNw012m(Nw012m nw012m) { 
		return nw012mMapper.updateByPrimaryKey(nw012m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteNw012m(Map<String, Object> map) { 
		return nw012mMapper.deleteByPrimaryKey(map); 
	} 
} 
