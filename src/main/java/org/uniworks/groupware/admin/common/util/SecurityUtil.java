/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.common.util;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Park Chung Wan
 *
 */
public class SecurityUtil {
	/**
	 * 로그인한 사용자 UserName을 가져온다. (coId + ":" + loginId 형태로 구성)
	 * @return
	 */
	public static String getUserName() {
		// 시큐리티 컨텍스트 객체를 얻습니다. 
		SecurityContext context = SecurityContextHolder.getContext(); 
		// 인증 객체를 얻습니다. 
		Authentication authentication = context.getAuthentication(); 
		// 로그인한 사용자정보를 가진 객체를 얻습니다. 
		UserDetails userDetail = (UserDetails) authentication.getPrincipal(); 
		return userDetail.getUsername();
	}
	
	/**
	 * 로그인한 사용자의 Authority를 가져온다 (여기서는 SYS_ADM, GRP_ADM, CNT_ADM 중 1개를 가져 온다.)
	 * @return
	 */
	public static String getAuthority() {
		// 시큐리티 컨텍스트 객체를 얻습니다. 
		SecurityContext context = SecurityContextHolder.getContext(); 
		// 인증 객체를 얻습니다. 
		Authentication authentication = context.getAuthentication(); 
		String adminType = "";
		// 사용자가 가진 모든 롤 정보를 얻습니다. 
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); 
		Iterator<? extends GrantedAuthority> iter = authorities.iterator(); 
		while (iter.hasNext()) { 
			GrantedAuthority auth = iter.next(); 
			adminType = auth.getAuthority(); 
		}
		
		return adminType;
	}
}
