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

import org.uniworks.groupware.admin.domain.Nw030m; 
import org.uniworks.groupware.admin.mapper.Nw030mMapper; 
import org.uniworks.groupware.admin.service.Nw030mService; 

@Service 
@Transactional(readOnly = true) 
public class Nw030mServiceImpl implements Nw030mService { 
	@Autowired Nw030mMapper nw030mMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Nw030m> getNw030mList(Map<String, Object> map) { 
		return nw030mMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Nw030m getNw030m(Map<String, Object> map) { 
		return nw030mMapper.selectByPrimaryKey(map); 
	} 
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addNw030m(Nw030m nw030m) { 
		return nw030mMapper.insert(nw030m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateNw030m(Nw030m nw030m) { 
		return nw030mMapper.updateByPrimaryKey(nw030m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteNw030m(Map<String, Object> map) { 
		return nw030mMapper.deleteByPrimaryKey(map); 
	} 
} 
