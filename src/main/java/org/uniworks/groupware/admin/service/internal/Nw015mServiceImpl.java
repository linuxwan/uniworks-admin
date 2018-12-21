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

import org.uniworks.groupware.admin.domain.Nw015m; 
import org.uniworks.groupware.admin.mapper.Nw015mMapper; 
import org.uniworks.groupware.admin.service.Nw015mService; 

@Service 
@Transactional(readOnly = true) 
public class Nw015mServiceImpl implements Nw015mService { 
	@Autowired Nw015mMapper nw015mMapper; 

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Nw015m> getNw015mList(Map<String, Object> map) { 
		return nw015mMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Nw015m getNw015m(Map<String, Object> map) { 
		return nw015mMapper.selectByPrimaryKey(map); 
	} 
	/**
	 * Sequence No 가져오기
	 * @param map
	 * @return
	 */
	@Override
	public int getSeqNo(java.util.Map<String,Object> map) {
		return nw015mMapper.selectSeqNo(map);
	}
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addNw015m(Nw015m nw015m) { 
		return nw015mMapper.insert(nw015m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateNw015m(Nw015m nw015m) { 
		return nw015mMapper.updateByPrimaryKey(nw015m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteNw015m(Map<String, Object> map) { 
		return nw015mMapper.deleteByPrimaryKey(map); 
	} 
	
	/**
	 * 결재마스터ID로 삭제
	 * @param map
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int deleteByApprMstId(java.util.Map<String,Object> map) {
		return nw015mMapper.deleteByApprMstId(map);
	}
} 
