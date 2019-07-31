/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.domain.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Park Chung Wan
 *
 */
public class Role implements GrantedAuthority {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<Privilege> privileges;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	public String getName() { 
		return name; 
	} 
	
	public void setName(String name) { 
		this.name = name; 
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}	
}
