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
import org.uniworks.groupware.admin.domain.Cm003m;
import org.uniworks.groupware.admin.mapper.Cm003mMapper;
import org.uniworks.groupware.admin.service.Cm003mService; 

@Service 
@Transactional(readOnly = true) 
public class Cm003mServiceImpl implements Cm003mService { 
	@Autowired Cm003mMapper cm003mMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Cm003m> getCm003mList(Map<String, Object> map) { 
		return cm003mMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Cm003m getCm003m(Map<String, Object> map) { 
		return cm003mMapper.selectByPrimaryKey(map); 
	} 
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addCm003m(Cm003m cm003m) { 
		return cm003mMapper.insert(cm003m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateCm003m(Cm003m cm003m) { 
		return cm003mMapper.updateByPrimaryKey(cm003m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteCm003m(Map<String, Object> map) { 
		return cm003mMapper.deleteByPrimaryKey(map); 
	} 
} 
