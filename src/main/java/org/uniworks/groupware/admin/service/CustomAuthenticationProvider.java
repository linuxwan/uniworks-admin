/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.uniworks.groupware.admin.domain.UserInfo;

/**
 * @author Park Chung Wan
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationProvider.class);
	@Autowired AuthenticationService authenticationService;
	@Autowired PasswordEncoder passwordEncoder;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		UserInfo user;
		Collection<? extends GrantedAuthority> authorities = null;
		
		try {
			user = authenticationService.loadUserByUsername(username);						
			
			if (user != null) {				
				authorities = user.getAuthorities();				
			}
		} catch (UsernameNotFoundException e) {
			logger.info(e.toString());
			throw new UsernameNotFoundException(e.getMessage());
		} catch (BadCredentialsException e) {
			logger.info(e.toString());
			throw new BadCredentialsException(e.getMessage());
		} catch (Exception e) {
			logger.info(e.toString());
			throw new RuntimeException(e.getMessage());
		}
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}
