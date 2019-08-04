/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service;

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.Nw106m;
import org.uniworks.groupware.admin.domain.Nw107m;

/**
 * @author Park Chung Wan
 *
 */
public interface RoleService {
	/**
	 * Role 추가
	 * @param nw106m
	 * @param nw107mList
	 * @return
	 */
	int addRole(Nw106m nw106m, List<Nw107m> nw107mList);
	/**
	 * Role 수정
	 * @param nw106m
	 * @param nw107mList
	 * @return
	 */
	int modifyRole(Nw106m nw106m, List<Nw107m> nw107mList);
	/**
	 * Role 삭제
	 * @param map
	 * @return
	 */
	int removeRole(Map<String, Object> map);
}
