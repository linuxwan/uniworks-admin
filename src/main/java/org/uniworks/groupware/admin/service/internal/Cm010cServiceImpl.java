/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.service.internal; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.uniworks.groupware.admin.domain.Cm010c;
import org.uniworks.groupware.admin.mapper.Cm010cMapper;
import org.uniworks.groupware.admin.service.Cm010cService; 

@Service 
@Transactional(readOnly = true) 
public class Cm010cServiceImpl implements Cm010cService { 
	@Autowired Cm010cMapper cm010cMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Cm010c> getCm010cList(Map<String, Object> map) { 
		return cm010cMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Cm010c getCm010c(Map<String, Object> map) { 
		return cm010cMapper.selectByPrimaryKey(map); 
	} 
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addCm010c(Cm010c cm010c) { 
		return cm010cMapper.insert(cm010c); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateCm010c(Cm010c cm010c) { 
		return cm010cMapper.updateByPrimaryKey(cm010c); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteCm010c(Map<String, Object> map) { 
		return cm010cMapper.deleteByPrimaryKey(map); 
	} 
	
	/**
	 * 등록된 관리자가 있는지 체크한다.
	 * @param coId
	 * @param adminId
	 * @return
	 */
	public boolean isAdminExist(String coId, String adminId) {
		boolean chk = false;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("adminId", adminId);
		
		Cm010c cm010c = cm010cMapper.selectByPrimaryKey(map);
		if (cm010c != null && cm010c.getCoId().length() > 0) chk = true;
		return chk;
	}
} 
