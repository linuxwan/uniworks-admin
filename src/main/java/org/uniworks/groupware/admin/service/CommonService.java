/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.GroupInfo;
import org.uniworks.groupware.admin.domain.Nw106m;

/**
 * @author Park Chungwan
 *
 */
@Service
public interface CommonService {
	/**
	 * 주코드에 속하는 하위코드 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<CommonCode> getCommonSubCodeList(Map<String, Object> map);	
	/**
	 * 사용자 그룹에 대한 검색 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<GroupInfo> getGroupListBySearch(Map<String, Object> map);
	/**
	 * Role에 대한 검색 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<Nw106m> getRoleListBySearch(Map<String, Object> map);
}
