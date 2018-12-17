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

import org.uniworks.groupware.admin.domain.Nw010m; 
import org.uniworks.groupware.admin.mapper.Nw010mMapper; 
import org.uniworks.groupware.admin.service.Nw010mService; 

@Service 
@Transactional(readOnly = true) 
public class Nw010mServiceImpl implements Nw010mService { 
	@Autowired Nw010mMapper nw010mMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Nw010m> getNw010mList(Map<String, Object> map) { 
		return nw010mMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Nw010m getNw010m(Map<String, Object> map) { 
		return nw010mMapper.selectByPrimaryKey(map); 
	} 
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addNw010m(Nw010m nw010m) { 
		return nw010mMapper.insert(nw010m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateNw010m(Nw010m nw010m) { 
		return nw010mMapper.updateByPrimaryKey(nw010m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteNw010m(Map<String, Object> map) { 
		return nw010mMapper.deleteByPrimaryKey(map); 
	} 
} 
