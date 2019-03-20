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

import org.uniworks.groupware.admin.domain.Nw106m; 
import org.uniworks.groupware.admin.mapper.Nw106mMapper; 
import org.uniworks.groupware.admin.service.Nw106mService; 

@Service 
@Transactional(readOnly = true) 
public class Nw106mServiceImpl implements Nw106mService { 
	@Autowired Nw106mMapper nw106mMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Nw106m> getNw106mList(Map<String, Object> map) { 
		return nw106mMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Nw106m getNw106m(Map<String, Object> map) { 
		return nw106mMapper.selectByPrimaryKey(map); 
	} 
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addNw106m(Nw106m nw106m) { 
		return nw106mMapper.insert(nw106m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateNw106m(Nw106m nw106m) { 
		return nw106mMapper.updateByPrimaryKey(nw106m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteNw106m(Map<String, Object> map) { 
		return nw106mMapper.deleteByPrimaryKey(map); 
	} 
} 
