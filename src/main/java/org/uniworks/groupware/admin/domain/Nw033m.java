/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.domain; 

import java.io.Serializable; 

import javax.xml.bind.annotation.XmlRootElement; 
import javax.xml.bind.annotation.XmlType; 

import org.pojomatic.Pojomatic; 
import org.pojomatic.annotations.AutoProperty; 
/** 
 * @author Park Chungwan 
 * 관련 테이블 : Nw033m
 */ 
@XmlRootElement(name = "nw033m") 
@XmlType(propOrder = {"cntnId", "coId", "useAuthType", "useAuthGrpCode", "crtAuth", "rdAuth", "updtAuth", "delAuth", "prntAuth", "upldAuth", "dnldAuth"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Nw033m implements Serializable {  
	private String cntnId; 
	private String coId; 
	private String useAuthType; 
	private String useAuthGrpCode; 
	private String crtAuth = "N"; 
	private String rdAuth = "N"; 
	private String updtAuth = "N"; 
	private String delAuth = "N"; 
	private String prntAuth = "N"; 
	private String upldAuth = "N"; 
	private String dnldAuth = "N"; 

	public void setCntnId(String cntnId) { 
		this.cntnId = cntnId; 
	} 
	public String getCntnId() { 
		return this.cntnId; 
	} 
	public void setCoId(String coId) { 
		this.coId = coId; 
	} 
	public String getCoId() { 
		return this.coId; 
	} 
	public void setUseAuthType(String useAuthType) { 
		this.useAuthType = useAuthType; 
	} 
	public String getUseAuthType() { 
		return this.useAuthType; 
	} 
	public void setUseAuthGrpCode(String useAuthGrpCode) { 
		this.useAuthGrpCode = useAuthGrpCode; 
	} 
	public String getUseAuthGrpCode() { 
		return this.useAuthGrpCode; 
	} 
	public void setCrtAuth(String crtAuth) { 
		this.crtAuth = crtAuth; 
	} 
	public String getCrtAuth() { 
		return this.crtAuth; 
	} 
	public void setRdAuth(String rdAuth) { 
		this.rdAuth = rdAuth; 
	} 
	public String getRdAuth() { 
		return this.rdAuth; 
	} 
	public void setUpdtAuth(String updtAuth) { 
		this.updtAuth = updtAuth; 
	} 
	public String getUpdtAuth() { 
		return this.updtAuth; 
	} 
	public void setDelAuth(String delAuth) { 
		this.delAuth = delAuth; 
	} 
	public String getDelAuth() { 
		return this.delAuth; 
	} 
	public void setPrntAuth(String prntAuth) { 
		this.prntAuth = prntAuth; 
	} 
	public String getPrntAuth() { 
		return this.prntAuth; 
	} 
	public void setUpldAuth(String upldAuth) { 
		this.upldAuth = upldAuth; 
	} 
	public String getUpldAuth() { 
		return this.upldAuth; 
	} 
	public void setDnldAuth(String dnldAuth) { 
		this.dnldAuth = dnldAuth; 
	} 
	public String getDnldAuth() { 
		return this.dnldAuth; 
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