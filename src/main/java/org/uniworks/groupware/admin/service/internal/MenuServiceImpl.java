/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.uniworks.groupware.admin.domain.MenuInfo;
import org.uniworks.groupware.admin.domain.Nw020m;
import org.uniworks.groupware.admin.domain.Nw021m;
import org.uniworks.groupware.admin.mapper.MenuMapper;
import org.uniworks.groupware.admin.mapper.Nw020mMapper;
import org.uniworks.groupware.admin.mapper.Nw021mMapper;
import org.uniworks.groupware.admin.service.MenuService;

/**
 * @author Park Chung Wan
 *
 */
@Service
@Transactional(readOnly = true) 
public class MenuServiceImpl implements MenuService {
	@Autowired MenuMapper menuMapper;
	@Autowired Nw020mMapper nw020mMapper;
	@Autowired Nw021mMapper nw021mMapper;
	/**
	 * 메뉴 목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<MenuInfo> getMenuAllList(Map<String, Object> map) {		
		return menuMapper.selectByMenuAllList(map);
	}

	/**
	 * 메뉴 정보를 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public MenuInfo selectByMenuId(Map<String, Object> map) {
		return menuMapper.selectByMenuId(map);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int addMenu(Nw020m nw020m, List<Nw021m> nw021mList) {
		int count = nw020mMapper.insert(nw020m);
		
		for (Nw021m nw021m : nw021mList) {
			count = nw021mMapper.insert(nw021m);
		}
		return count;
	}
	/**
	 * 메뉴 정보를 수정한다.
	 * @param nw020m
	 * @param nw021m
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int modifyMenu(Nw020m nw020m, List<Nw021m> nw021mList) {
		int count = nw020mMapper.updateByPrimaryKey(nw020m);
		
		//다국어 정보를 삭제하고 추가한다.
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw020m.getCoId());
		map.put("menuId", nw020m.getMenuId());
		
		int delCount = nw021mMapper.delete(map);
		if (delCount >= 0) {
			for (Nw021m nw021m : nw021mList) {
				count = nw021mMapper.insert(nw021m);
			}
		}
		return count;
	}
	
	/**
	 * 메뉴 삭제
	 * @param coId
	 * @param menuId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int deleteMenu(String coId, String menuId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", coId);
		map.put("menuId", menuId);
		
		int count = nw021mMapper.delete(map);
		count = nw020mMapper.deleteByPrimaryKey(map);
		
		return count;
	}
}
