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
import org.uniworks.groupware.admin.domain.Nw106m;
import org.uniworks.groupware.admin.domain.Nw107m;
import org.uniworks.groupware.admin.mapper.Nw106mMapper;
import org.uniworks.groupware.admin.mapper.Nw107mMapper;
import org.uniworks.groupware.admin.service.RoleService;

/**
 * @author Park Chung Wan
 *
 */
@Service
@Transactional(readOnly = true) 
public class RoleServiceImpl implements RoleService {
	@Autowired Nw106mMapper nw106mMapper; 
	@Autowired Nw107mMapper nw107mMapper; 
	/**
	 * Role 추가
	 * @param nw106m
	 * @param nw107mList
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int addRole(Nw106m nw106m, List<Nw107m> nw107mList) {
		int cnt = nw106mMapper.insert(nw106m);
		
		for (Nw107m nw107m : nw107mList) {
			cnt = nw107mMapper.insert(nw107m);
		}
		
		return cnt;
	}

	/**
	 * Role 수정
	 * @param nw106m
	 * @param nw107mList
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int modifyRole(Nw106m nw106m, List<Nw107m> nw107mList) {
		int cnt = nw106mMapper.updateByPrimaryKey(nw106m);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw106m.getCoId());
		map.put("role", nw106m.getRole());
		
		int delCnt = nw107mMapper.deleteByPrimaryKey(map);
		
		for (Nw107m nw107m : nw107mList) {
			cnt = nw107mMapper.insert(nw107m);
		}
		
		return cnt;
	}
	
	/**
	 * Role 삭제
	 * @param map
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int removeRole(Map<String, Object> map) {
		int cnt = nw107mMapper.deleteByPrimaryKey(map);
		cnt = nw106mMapper.deleteByPrimaryKey(map);
		
		return cnt;
	}
}
