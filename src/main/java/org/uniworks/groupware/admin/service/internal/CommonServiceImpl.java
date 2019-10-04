/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service.internal;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.domain.GroupInfo;
import org.uniworks.groupware.admin.domain.Nw106m;
import org.uniworks.groupware.admin.mapper.CommonMapper;
import org.uniworks.groupware.admin.service.CommonService;

/**
 * @author Park Chungwan
 *
 */
@Service
@Transactional(readOnly = true) 
public class CommonServiceImpl implements CommonService {
	@Autowired CommonMapper cmmMapper;
	/**
	 * 주코드에 속하는 하위코드 목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<CommonCode> getCommonSubCodeList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<CommonCode> codeList = cmmMapper.selectByCommonSubCodeList(map);
		return codeList;
	}
	
	/**
	 * 사용자 그룹에 대한 검색 목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<GroupInfo> getGroupListBySearch(Map<String, Object> map) {
		List<GroupInfo> groupList = cmmMapper.getGroupListBySearch(map);
		return groupList;
	}
	
	/**
	 * Role에 대한 검색 목록을 가져온다.
	 * @param map
	 * @return
	 */
	public List<Nw106m> getRoleListBySearch(Map<String, Object> map) {
		List<Nw106m> roleList = cmmMapper.getRoleListBySearch(map);
		return roleList;
	}
}
