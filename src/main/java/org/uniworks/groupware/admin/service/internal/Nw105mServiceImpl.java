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

import org.uniworks.groupware.admin.domain.Nw105m; 
import org.uniworks.groupware.admin.mapper.Nw105mMapper; 
import org.uniworks.groupware.admin.service.Nw105mService; 

@Service 
@Transactional(readOnly = true) 
public class Nw105mServiceImpl implements Nw105mService { 
	@Autowired Nw105mMapper nw105mMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Nw105m> getNw105mList(Map<String, Object> map) { 
		return nw105mMapper.select(map); 
	} 
	/**
	 * Role 사용자 수
	 * @param map
	 * @return
	 */
	@Override
	public int getRoleUserCount(java.util.Map<String,Object> map) {
		return nw105mMapper.selectRoleUserCount(map);
	}
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Nw105m getNw105m(Map<String, Object> map) { 
		return nw105mMapper.selectByPrimaryKey(map); 
	} 
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addNw105m(Nw105m nw105m) { 
		return nw105mMapper.insert(nw105m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateNw105m(Nw105m nw105m) { 
		return nw105mMapper.updateByPrimaryKey(nw105m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteNw105m(Map<String, Object> map) { 
		return nw105mMapper.deleteByPrimaryKey(map); 
	} 
} 
