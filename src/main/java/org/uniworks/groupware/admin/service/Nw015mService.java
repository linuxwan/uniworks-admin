/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.service; 

import java.util.List; 

import org.uniworks.groupware.admin.domain.Nw015m; 

public interface Nw015mService { 
	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	List<Nw015m> getNw015mList(java.util.Map<String,Object> map); 

	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	Nw015m getNw015m(java.util.Map<String,Object> map); 
	/**
	 * Sequence No 가져오기
	 * @param map
	 * @return
	 */
	int getSeqNo(java.util.Map<String,Object> map);
	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	int addNw015m(Nw015m nw015m); 

	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	int updateNw015m(Nw015m nw015m); 

	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	int deleteNw015m(java.util.Map<String,Object> map); 
	
	/**
	 * 결재마스터ID로 삭제
	 * @param map
	 * @return
	 */
	int deleteByApprMstId(java.util.Map<String,Object> map);
} 
