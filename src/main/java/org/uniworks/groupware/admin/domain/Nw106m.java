/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.domain; 

import java.io.Serializable; 
import java.util.Date; 

import javax.xml.bind.annotation.XmlRootElement; 
import javax.xml.bind.annotation.XmlType; 

import org.pojomatic.Pojomatic; 
import org.pojomatic.annotations.AutoProperty; 
/** 
 * @author Park Chungwan 
 * 관련 테이블 : Nw106m
 */ 
@XmlRootElement(name = "nw106m") 
@XmlType(propOrder = {"coId", "role", "roleName", "useIndc", "roleDetl", "crtId", "crtDate", "chngId", "chngDate"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Nw106m implements Serializable {  
	private String coId; 
	private String role; 
	private String roleName; 
	private String useIndc; 
	private String roleDetl; 
	private String crtId; 
	private Date crtDate; 
	private String chngId; 
	private Date chngDate; 

	public void setCoId(String coId) { 
		this.coId = coId; 
	} 
	public String getCoId() { 
		return this.coId; 
	} 
	public void setRole(String role) { 
		this.role = role; 
	} 
	public String getRole() { 
		return this.role; 
	} 
	public void setRoleName(String roleName) { 
		this.roleName = roleName; 
	} 
	public String getRoleName() { 
		return this.roleName; 
	} 
	public void setUseIndc(String useIndc) { 
		this.useIndc = useIndc; 
	} 
	public String getUseIndc() { 
		return this.useIndc; 
	} 
	public void setRoleDetl(String roleDetl) { 
		this.roleDetl = roleDetl; 
	} 
	public String getRoleDetl() { 
		return this.roleDetl; 
	} 
	public void setCrtId(String crtId) { 
		this.crtId = crtId; 
	} 
	public String getCrtId() { 
		return this.crtId; 
	} 
	public void setCrtDate(Date crtDate) { 
		this.crtDate = crtDate; 
	} 
	public Date getCrtDate() { 
		return this.crtDate; 
	} 
	public void setChngId(String chngId) { 
		this.chngId = chngId; 
	} 
	public String getChngId() { 
		return this.chngId; 
	} 
	public void setChngDate(Date chngDate) { 
		this.chngDate = chngDate; 
	} 
	public Date getChngDate() { 
		return this.chngDate; 
	} 
	@Override 
	public boolean equals(Object o) {  
		return Pojomatic.equals(this, o); 
	} 
	@Override 
	public int hashCode() { 
		return Pojomatic.hashCode(this); 
	} 
	@Override 
	public String toString() { 
		return Pojomatic.toString(this); 
	} 
}