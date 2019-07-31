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

import org.uniworks.groupware.admin.domain.Nw003m; 
import org.uniworks.groupware.admin.mapper.Nw003mMapper; 
import org.uniworks.groupware.admin.service.Nw003mService; 

@Service 
@Transactional(readOnly = true) 
public class Nw003mServiceImpl implements Nw003mService { 
	@Autowired Nw003mMapper nw003mMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Nw003m> getNw003mList(Map<String, Object> map) { 
		return nw003mMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Nw003m getNw003m(Map<String, Object> map) { 
		return nw003mMapper.selectByPrimaryKey(map); 
	} 
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addNw003m(Nw003m nw003m) { 
		return nw003mMapper.insert(nw003m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateNw003m(Nw003m nw003m) { 
		return nw003mMapper.updateByPrimaryKey(nw003m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteNw003m(Map<String, Object> map) { 
		return nw003mMapper.deleteByPrimaryKey(map); 
	} 
	
	/**
	 * Board Id로 삭제
	 * @param map
	 * @return
	 */
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteByBoardId(java.util.Map<String,Object> map) {
		return nw003mMapper.deleteByBoardId(map);
	}
} 
