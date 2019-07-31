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

import org.uniworks.groupware.admin.domain.Nw032m; 
import org.uniworks.groupware.admin.mapper.Nw032mMapper; 
import org.uniworks.groupware.admin.service.Nw032mService; 

@Service 
@Transactional(readOnly = true) 
public class Nw032mServiceImpl implements Nw032mService { 
	@Autowired Nw032mMapper nw032mMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Nw032m> getNw032mList(Map<String, Object> map) { 
		return nw032mMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Nw032m getNw032m(Map<String, Object> map) { 
		return nw032mMapper.selectByPrimaryKey(map); 
	} 
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addNw032m(Nw032m nw032m) { 
		return nw032mMapper.insert(nw032m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateNw032m(Nw032m nw032m) { 
		return nw032mMapper.updateByPrimaryKey(nw032m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteNw032m(Map<String, Object> map) { 
		return nw032mMapper.deleteByPrimaryKey(map); 
	} 
} 
