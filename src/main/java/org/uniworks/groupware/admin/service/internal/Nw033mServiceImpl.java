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

import org.uniworks.groupware.admin.domain.Nw033m; 
import org.uniworks.groupware.admin.mapper.Nw033mMapper; 
import org.uniworks.groupware.admin.service.Nw033mService; 

@Service 
@Transactional(readOnly = true) 
public class Nw033mServiceImpl implements Nw033mService { 
	@Autowired Nw033mMapper nw033mMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Nw033m> getNw033mList(Map<String, Object> map) { 
		return nw033mMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Nw033m getNw033m(Map<String, Object> map) { 
		return nw033mMapper.selectByPrimaryKey(map); 
	} 
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addNw033m(Nw033m nw033m) { 
		return nw033mMapper.insert(nw033m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateNw033m(Nw033m nw033m) { 
		return nw033mMapper.updateByPrimaryKey(nw033m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteNw033m(Map<String, Object> map) { 
		return nw033mMapper.deleteByPrimaryKey(map); 
	} 
} 
