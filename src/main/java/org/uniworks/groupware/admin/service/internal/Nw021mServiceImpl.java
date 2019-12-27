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

import org.uniworks.groupware.admin.domain.Nw021m; 
import org.uniworks.groupware.admin.mapper.Nw021mMapper; 
import org.uniworks.groupware.admin.service.Nw021mService; 

@Service 
@Transactional(readOnly = true) 
public class Nw021mServiceImpl implements Nw021mService { 
	@Autowired Nw021mMapper nw021mMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Nw021m> getNw021mList(Map<String, Object> map) { 
		return nw021mMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Nw021m getNw021m(Map<String, Object> map) { 
		return nw021mMapper.selectByPrimaryKey(map); 
	} 
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addNw021m(Nw021m nw021m) { 
		return nw021mMapper.insert(nw021m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateNw021m(Nw021m nw021m) { 
		return nw021mMapper.updateByPrimaryKey(nw021m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteNw021m(Map<String, Object> map) { 
		return nw021mMapper.deleteByPrimaryKey(map); 
	} 
} 
