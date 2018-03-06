/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.service; 

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.OganInfo;
import org.uniworks.groupware.admin.domain.Om001m;
import org.uniworks.groupware.admin.domain.Om002m; 

public interface Om001mService { 
	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	List<Om001m> getOm001mList(java.util.Map<String,Object> map); 

	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	Om001m getOm001m(java.util.Map<String,Object> map); 

	/**
	 * 조회한다. (등록된 다국어별 조직정보를 모두 가져온다.)
	 * @param map
	 * @return
	 */
	List<OganInfo> getOm001mListLang(java.util.Map<String, Object> map);
	
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	int addOm001m(Om001m om001m); 

	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	int updateOm001m(Om001m om001m); 

	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	int deleteOm001m(java.util.Map<String,Object> map); 
	
	/**
	 * 조직 정보를 등록한다. 다국어 포함해서 등록.
	 * @param om001m
	 * @param om002mList
	 * @return
	 */
	int addOm001mOm002m(Om001m om001m, List<Om002m> om002mList);
	
	/**
	 * 조직 정보를 업데이트 한다. 다국어 포함해서 수정.
	 * @param om001m
	 * @param om002mList
	 * @return
	 */
	int updateOm001mOm002m(Om001m om001m, List<Om002m> om002mList);
	
	/**
	 * 조직 정보를 삭제한다. 다국어 포함해서 삭제.
	 * @param map
	 */
	int deleteOm001mOm002m(Map<String, Object> map);
} 
