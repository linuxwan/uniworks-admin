/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service;

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.MenuInfo;
import org.uniworks.groupware.admin.domain.Nw020m;
import org.uniworks.groupware.admin.domain.Nw021m;

/**
 * @author Park Chung Wan
 *
 */
public interface MenuService {
	/**
	 * 메뉴 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<MenuInfo> getMenuAllList(Map<String, Object> map);
	
	/**
	 * 메뉴 정보를 가져온다.
	 * @param map
	 * @return
	 */
	MenuInfo selectByMenuId(Map<String, Object> map);
	
	/**
	 * 메뉴를 등록한다. 
	 * @param nw020m
	 * @param nw021mList
	 * @return
	 */
	int addMenu(Nw020m nw020m, List<Nw021m> nw021mList);
	
	/**
	 * 메뉴 정보를 수정한다.
	 * @param nw020m
	 * @param nw021m
	 * @return
	 */
	int modifyMenu(Nw020m nw020m, List<Nw021m> nw021mList);
	
	/**
	 * 메뉴 삭제
	 * @param coId
	 * @param menuId
	 * @return
	 */
	int deleteMenu(String coId, String menuId);
}
