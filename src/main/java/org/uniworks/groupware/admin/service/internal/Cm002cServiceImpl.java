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
import org.uniworks.groupware.admin.domain.Cm001c;
import org.uniworks.groupware.admin.domain.Cm002c;
import org.uniworks.groupware.admin.domain.Cm003m;
import org.uniworks.groupware.admin.mapper.Cm002cMapper;
import org.uniworks.groupware.admin.mapper.Cm003mMapper;
import org.uniworks.groupware.admin.service.Cm002cService; 

@Service 
@Transactional(readOnly = true) 
public class Cm002cServiceImpl implements Cm002cService { 
	@Autowired Cm002cMapper cm002cMapper;
	@Autowired Cm003mMapper cm003mMapper;

	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public List<Cm002c> getCm002cList(Map<String, Object> map) { 
		return cm002cMapper.select(map); 
	} 
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	public Cm002c getCm002c(Map<String, Object> map) { 
		return cm002cMapper.selectByPrimaryKey(map); 
	} 
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addCm002c(Cm002c cm002c) { 
		return cm002cMapper.insert(cm002c); 
	} 
	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateCm002c(Cm002c cm002c) { 
		return cm002cMapper.updateByPrimaryKey(cm002c); 
	} 
	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteCm002c(Map<String, Object> map) { 
		return cm002cMapper.deleteByPrimaryKey(map); 
	}
	
	/**
	 * 부코드와 다국어 명칭을 등록한다.
	 * @param cm002c
	 * @param cm003m
	 * @return
	 */
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int addSubCodeInfo(Cm002c cm002c, List<Cm003m> cm003mList) {
		int cnt = cm002cMapper.insert(cm002c);		
		
		for (Cm003m cm003m : cm003mList) {
			cm003mMapper.insert(cm003m);
		}
		
		return cnt;
	}
	
	/**
	 * 부코드와 다국어 명칭을 수정한다.
	 * @param cm002c
	 * @param cm003mList
	 * @return
	 */
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int updateSubCodeInfo(Cm002c cm002c, List<Cm003m> cm003mList) {
		int cnt = cm002cMapper.updateByPrimaryKey(cm002c);
		
		for (Cm003m cm003m : cm003mList) {
			cm003mMapper.updateByPrimaryKey(cm003m);
		}
		
		return cnt;
	}
	
	/**
	 * 부코드와 다국어 명칭을 모두 삭제한다.
	 * @param map
	 * @return
	 */
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int deleteSubCodeAndMultiLanguage(Map<String, Object> map) {
		int cnt = 0;
		cnt = cm003mMapper.deleteByPrimaryKey(map);
		cnt = cm002cMapper.deleteByPrimaryKey(map);
		return cnt;
	}
} 
