/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.service; 

import java.util.List;

import org.uniworks.groupware.admin.domain.Hr001m; 

public interface Hr001mService { 
	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	List<Hr001m> getHr001mList(java.util.Map<String,Object> map); 

	/**
	 * 그룹사 목록을 가져온다. 
	 * @param map
	 * @return
	 */
	List<Hr001m> getGroupCompanyListAll(java.util.Map<String,Object> map);
	
	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	Hr001m getHr001m(java.util.Map<String,Object> map); 

	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	int addHr001m(Hr001m hr001m); 

	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	int updateHr001m(Hr001m hr001m); 

	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	int deleteHr001m(java.util.Map<String,Object> map); 
	
	/**
	 * 회사가 존재하는지 확인해서 존재하면 true, 그렇지 않으면 false를 리턴
	 * @param hr001m
	 * @return
	 */
	boolean isCompanyExist(String coId);
} 
