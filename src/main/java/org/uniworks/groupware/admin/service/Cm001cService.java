/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.service; 

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.Cm001c;
import org.uniworks.groupware.admin.domain.Cm003m; 

public interface Cm001cService { 
	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	List<Cm001c> getCm001cList(java.util.Map<String,Object> map); 

	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	Cm001c getCm001c(java.util.Map<String,Object> map); 

	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	int addCm001c(Cm001c cm001c); 

	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	int updateCm001c(Cm001c cm001c); 

	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	int deleteCm001c(java.util.Map<String,Object> map);
	
	/**
	 * 주코드와 다국어 명칭을 등록한다.
	 * @param cm001c
	 * @param cm003m
	 * @return
	 */
	int addMasterCodeInfo(Cm001c cm001c, List<Cm003m> cm003mList);
	
	/**
	 * 주코드와 다국어 명칭을 수정한다.
	 * @param cm001c
	 * @param cm003mList
	 * @return
	 */
	int updateMasterCodeInfo(Cm001c cm001c, List<Cm003m> cm003mList);
	
	/**
	 * 주코드와 다국어 명칭을 모두 삭제한다.
	 * @param map
	 * @return
	 */
	int deleteMasterCodeAndMultiLanguage(Map<String, Object> map);
} 
