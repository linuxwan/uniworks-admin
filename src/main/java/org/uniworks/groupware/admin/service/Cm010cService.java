/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.service; 

import java.util.List;

import org.uniworks.groupware.admin.domain.Cm010c; 

public interface Cm010cService { 
	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	List<Cm010c> getCm010cList(java.util.Map<String,Object> map); 

	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	Cm010c getCm010c(java.util.Map<String,Object> map); 

	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	int addCm010c(Cm010c cm010c); 

	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	int updateCm010c(Cm010c cm010c); 

	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	int deleteCm010c(java.util.Map<String,Object> map); 
} 
