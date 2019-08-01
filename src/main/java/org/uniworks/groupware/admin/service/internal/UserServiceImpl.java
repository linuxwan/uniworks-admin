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
import org.uniworks.groupware.admin.domain.Nw100m;
import org.uniworks.groupware.admin.domain.Nw101m;
import org.uniworks.groupware.admin.domain.Nw105m;
import org.uniworks.groupware.admin.domain.User;
import org.uniworks.groupware.admin.domain.UserRole;
import org.uniworks.groupware.admin.mapper.Nw100mMapper;
import org.uniworks.groupware.admin.mapper.Nw101mMapper;
import org.uniworks.groupware.admin.mapper.Nw105mMapper;
import org.uniworks.groupware.admin.mapper.UserInfoMapper;
import org.uniworks.groupware.admin.service.Nw105mService;
import org.uniworks.groupware.admin.service.UserService;

/**
 * @author Park Chung Wan
 *
 */
@Service 
@Transactional(readOnly = true) 
public class UserServiceImpl implements UserService {
	@Autowired UserInfoMapper userInfoMapper;
	@Autowired Nw100mMapper nw100mMapper;
	@Autowired Nw101mMapper nw101mMapper;
	@Autowired Nw105mMapper nw105mMapper;
	
	/**
	 * 사용자 목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<User> getUserList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<User> userList = userInfoMapper.getUserList(map);
		return userList;
	}

	/**
	 * 사용자 정보를 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public User getUser(Map<String, Object> map) {
		User user = userInfoMapper.getUser(map);
		return user;
	}
	
	/**
	 * 사용자 정보를 등록한다.
	 * @param nw100m
	 * @param nw101m
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int addUser(Nw100m nw100m) {
		int cnt = nw100mMapper.insert(nw100m);
		
		Nw105m nw105m = new Nw105m();
		nw105m.setCoId(nw100m.getCoId());
		nw105m.setUserId(nw100m.getUserId());
		short useIndc = 1;
		nw105m.setUseIndc(useIndc);
		nw105m.setRole("ROLE_USER");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw100m.getCoId());
		map.put("userId", nw100m.getUserId());
		map.put("role", "ROLE_USER");
		
		Nw105m chkNw105m = nw105mMapper.selectByPrimaryKey(map);
		if (chkNw105m == null) {
			cnt = nw105mMapper.insert(nw105m);
		}
		
		return cnt;
	}
	
	/**
	 * 사용자 정보를 수정한다.
	 * @param nw100m
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int updateUser(Nw100m nw100m,String loginUserId) {
		//사용자 정보(NW100M)를 수정한다.
		int cnt = nw100mMapper.updateByPrimaryKey(nw100m);
		//비밀번호 변경 이력을 남긴다.
		Nw101m nw101m = getNw101mAdminPswdChangeInfo(nw100m, loginUserId);		
		cnt = nw101mMapper.insert(nw101m);
		
		return cnt;
	}
	
	/**
	 * 사용자 정보를 삭제한다.
	 * @param map
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public int deleteUser(Map<String, Object> map) {
		//사용자 Role 정보 삭제		
		int cnt = nw105mMapper.deleteByPrimaryKey(map);		
		cnt = nw100mMapper.deleteByPrimaryKey(map);
		
		return cnt;
	}
	
	/**
	 * Role별 사용자 목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<UserRole> getUserListByRole(Map<String, Object> map) {
		return userInfoMapper.getUserListByRole(map);
	}
	
	/**
	 * 사용자 검색 결과 목록 가져오기
	 */
	@Override
	public List<UserRole> getUserListBySearch(Map<String, Object> map) {
		return userInfoMapper.getUserListBySearch(map);
	}
	
	/**
	 * 비밀번호 변경 시 로그인 비밀번호/이중 비밀번호 변경 이력을 남긴다.
	 * @param nw100m
	 * @return
	 */
	private Nw101m getNw101mAdminPswdChangeInfo(Nw100m nw100m, String loginUserId) {
		Nw101m nw101m = new Nw101m();
		nw101m.setCoId(nw100m.getCoId());
		nw101m.setUserId(nw100m.getUserId());
		nw101m.setPswdChngUserId(loginUserId);
		nw101m.setPswdChngType("A");
		
		//비밀번호 변경을 할 경우,
		if (nw100m.getPswd() != null && !nw100m.getPswd().equalsIgnoreCase("")) {
			nw101m.setPswdType("LP");			
		} else if (nw100m.getCnfmPswd() != null && !nw100m.getCnfmPswd().equalsIgnoreCase("")) { //이중비밀번호 변경을 할 경우
			nw101m.setPswdType("SP");
		}
		
		return nw101m;
	}		
}
