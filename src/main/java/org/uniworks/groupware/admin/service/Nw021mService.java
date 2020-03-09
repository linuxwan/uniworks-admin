/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.service; 

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.Nw021m; 

public interface Nw021mService { 
	/** 
	 * 목록을 조회한다. 
	 * 
	 * @return 
	 */ 
	List<Nw021m> getNw021mList(java.util.Map<String,Object> map); 

	/** 
	 * 조회한다. 
	 * 
	 * @return 
	 */ 
	Nw021m getNw021m(java.util.Map<String,Object> map); 

	/** 
	 * 등록한다. 
	 * 
	 * @return 
	 */ 
	int addNw021m(Nw021m nw021m); 

	/** 
	 * 수정한다. 
	 * 
	 * @return 
	 */ 
	int updateNw021m(Nw021m nw021m); 

	/** 
	 * 삭제한다. 
	 * 
	 * @return 
	 */ 
	int deleteNw021mByPrimary(java.util.Map<String,Object> map); 
	
	/**
	 * Menu Id에 해당하는 다국어 메뉴정보를 모두 삭제한다.
	 * @param map
	 * @return
	 */
	int deleteNw020m(Map<String, Object> map);
} 
