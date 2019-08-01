/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service;

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.Nw100m;
import org.uniworks.groupware.admin.domain.User;
import org.uniworks.groupware.admin.domain.UserRole;

/**
 * @author Park Chung Wan
 *
 */
public interface UserService {
	/**
	 * 사용자 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<User> getUserList(Map<String, Object> map);
	/**
	 * 사용자 정보를 가져온다.
	 * @param map
	 * @return
	 */
	User getUser(Map<String, Object> map);
	/**
	 * 사용자 정보를 등록한다.
	 * @param nw100m
	 * @param nw101m
	 * @return
	 */
	int addUser(Nw100m nw100m);
	/**
	 * 사용자 정보를 수정한다.
	 * @param nw100m
	 * @return
	 */
	int updateUser(Nw100m nw100m, String loginUserId);
	/**
	 * 사용자 정보를 삭제한다.
	 * @param map
	 * @return
	 */
	int deleteUser(Map<String, Object> map);
	/**
	 * Role별 사용자 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<UserRole> getUserListByRole(Map<String, Object> map);
	/**
	 * 사용자 검색 결과 목록 가져오기
	 * @param map
	 * @return
	 */
	List<UserRole> getUserListBySearch(Map<String, Object> map);
}
