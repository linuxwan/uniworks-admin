/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.mapper; 

import java.util.List; 
import java.util.Map; 

import org.uniworks.groupware.admin.domain.Hr002m; 


/** 
 * @author Park Chungwan 
 * 관련 테이블 : Hr002m
 */ 
public interface Hr002mMapper { 
	/** 
	 * 목록 가져오기 
	 * @param map 
	 * @return 
	 */ 
	List<Hr002m> select(Map<String, Object> map); 
	/** 
	 * 정보 가져오기 
	 * @param map 
	 * @return 
	 */ 
	Hr002m selectByPrimaryKey(Map<String, Object> map); 
	/** 
	 * 등록한다 
	 * @param hr002m 
	 * @return 
	 */ 
	int insert(Hr002m hr002m); 
	/** 
	 * 수정한다 
	 * @param hr002m 
	 * @return 
	 */ 
	int updateByPrimaryKey(Hr002m hr002m); 
	/** 
	 * 삭제한다 
	 * @param map 
	 * @return 
	 */ 
	int deleteByPrimaryKey(Map<String, Object> map); 
} 
