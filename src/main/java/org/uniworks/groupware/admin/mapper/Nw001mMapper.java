/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.mapper; 

import java.util.List; 
import java.util.Map; 

import org.uniworks.groupware.admin.domain.Nw001m; 


/** 
 * @author Park Chungwan 
 * 관련 테이블 : Nw001m
 */ 
public interface Nw001mMapper { 
	/** 
	 * 목록 가져오기 
	 * @param map 
	 * @return 
	 */ 
	List<Nw001m> select(Map<String, Object> map); 
	/** 
	 * 정보 가져오기 
	 * @param map 
	 * @return 
	 */ 
	Nw001m selectByPrimaryKey(Map<String, Object> map); 
	/** 
	 * 등록한다 
	 * @param nw001m 
	 * @return 
	 */ 
	int insert(Nw001m nw001m); 
	/** 
	 * 수정한다 
	 * @param nw001m 
	 * @return 
	 */ 
	int updateByPrimaryKey(Nw001m nw001m); 
	/** 
	 * 삭제한다 
	 * @param map 
	 * @return 
	 */ 
	int deleteByPrimaryKey(Map<String, Object> map); 
} 
