/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.service; 

import java.util.List; 

import org.uniworks.groupware.admin.domain.Hr011m; 

public interface Hr011mService { 
	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	List<Hr011m> getHr011mList(java.util.Map<String,Object> map); 

	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	Hr011m getHr011m(java.util.Map<String,Object> map); 

	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	int addHr011m(Hr011m hr011m); 

	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	int updateHr011m(Hr011m hr011m); 

	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	int deleteHr011m(java.util.Map<String,Object> map); 
} 
