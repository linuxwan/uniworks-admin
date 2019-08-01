/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.uniworks.groupware.admin.domain.UserInfo;
import org.uniworks.groupware.admin.domain.security.Role;
import org.uniworks.groupware.admin.mapper.UserInfoMapper;

/**
 * @author Park Chungwan
 *
 */
@Service
public class AuthenticationService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	@Autowired UserInfoMapper userInfoMapper;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserInfo userInfo = userInfoMapper.getUserInfo(username);
		userInfo.setUsername(userInfo.getCoId() + ":" + userInfo.getUsername());
		
		Role role = new Role();
		role.setName(userInfo.getRole());
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		userInfo.setAuthorities(roles);
				
		return userInfo;
	}	
}
