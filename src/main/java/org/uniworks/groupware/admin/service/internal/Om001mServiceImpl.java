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
import org.uniworks.groupware.admin.domain.Ogan;
import org.uniworks.groupware.admin.domain.Om001m;
import org.uniworks.groupware.admin.domain.Om002m;
import org.uniworks.groupware.admin.mapper.Om001mMapper;
import org.uniworks.groupware.admin.mapper.Om002mMapper;
import org.uniworks.groupware.admin.service.Om001mService; 

@Service 
@Transactional(readOnly = true) 
public class Om001mServiceImpl implements Om001mService { 
	@Autowired Om001mMapper om001mMapper; 
	@Autowired Om002mMapper om002mMapper;

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Om001m> getOm001mList(Map<String, Object> map) { 
		return om001mMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Om001m getOm001m(Map<String, Object> map) { 
		return om001mMapper.selectByPrimaryKey(map); 
	} 
	/**
	 * 조회한다. (등록된 다국어별 조직정보를 모두 가져온다.)
	 * @param map
	 * @return
	 */
	public List<Ogan> getOm001mListLang(java.util.Map<String, Object> map) {
		return om001mMapper.selectByLangList(map);
	}
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addOm001m(Om001m om001m) { 
		return om001mMapper.insert(om001m); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateOm001m(Om001m om001m) { 
		return om001mMapper.updateByPrimaryKey(om001m); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteOm001m(Map<String, Object> map) { 
		return om001mMapper.deleteByPrimaryKey(map); 
	}
	
	/**
	 * 조직 정보를 등록한다. 다국어 포함해서 등록.
	 * @param om001m
	 * @param om002mList
	 * @return
	 */
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int addOm001mOm002m(Om001m om001m, List<Om002m> om002mList) {
		int row = 0;
		
		row = om001mMapper.insert(om001m);
		
		for (Om002m om002m : om002mList) {
			om002mMapper.insert(om002m);
		}
		
		return row;
	}
	
	/**
	 * 조직 정보를 업데이트 한다. 다국어 포함해서 수정.
	 * @param om001m
	 * @param om002mList
	 * @return
	 */
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int updateOm001mOm002m(Om001m om001m, List<Om002m> om002mList) {
		int row = 0;
		
		row = om001mMapper.updateByPrimaryKey(om001m);
		
		for (Om002m om002m : om002mList) {
			om002mMapper.updateByPrimaryKey(om002m);
		}
		
		return row;
	}
	
	/**
	 * 조직 정보를 삭제한다. 다국어 포함해서 삭제.
	 * @param map
	 */
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int deleteOm001mOm002m(Map<String, Object> map) {
		int row = 0;
		
		row = om002mMapper.deleteByRescKey(map);
		row = om001mMapper.deleteByPrimaryKey(map);
		
		return row;
	}
} 
