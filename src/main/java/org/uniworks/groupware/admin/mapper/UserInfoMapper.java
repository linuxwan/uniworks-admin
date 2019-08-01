/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.mapper;

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.User;
import org.uniworks.groupware.admin.domain.UserInfo;
import org.uniworks.groupware.admin.domain.UserRole;

/**
 * @author Park Chungwan
 *
 */
public interface UserInfoMapper {
	/**
	 * 로그인 사용자 Role 정보를 가져온다.
	 * @param username
	 * @return
	 */
	public UserInfo getUserInfo(String username);	
	/**
	 * 사용자 Role을 여러개 가질 경우 사용자 정보가 Role별 List로 제공. 
	 * @param username
	 * @return
	 */
	public List<UserInfo> getUserInfoList(String username);
	/**
	 * 등록된 사용자 목록을 가져온다.
	 * @param map
	 * @return
	 */
	public List<User> getUserList(Map<String, Object> map);
	/**
	 * 등록된 사용자 정보를 가져온다.
	 * @param map
	 * @return
	 */
	public User getUser(Map<String, Object> map);
	/**
	 * Role별 사용자 목록을 가져온다.
	 * @param map
	 * @return
	 */
	public List<UserRole> getUserListByRole(Map<String, Object> map);
	/**
	 * Role 등록을 위한 사용자를 검색한다.
	 * @param map
	 * @return
	 */
	public List<UserRole> getUserListBySearch(Map<String, Object> map);
}
