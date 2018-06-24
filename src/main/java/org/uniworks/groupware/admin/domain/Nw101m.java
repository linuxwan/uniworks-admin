/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.domain; 

import java.io.Serializable; 
import java.sql.Timestamp; 

import javax.xml.bind.annotation.XmlRootElement; 
import javax.xml.bind.annotation.XmlType; 

import org.pojomatic.Pojomatic; 
import org.pojomatic.annotations.AutoProperty; 
/** 
 * @author Park Chungwan 
 * 관련 테이블 : Nw101m
 */ 
@XmlRootElement(name = "nw101m") 
@XmlType(propOrder = {"coId", "userId", "pswdType", "pswdChngType", "pswdChngDateTime", "pswdChngUserId"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Nw101m implements Serializable {  
	private String coId; 
	private String userId; 
	private String pswdType; 
	private String pswdChngType; 
	private Timestamp pswdChngDateTime; 
	private String pswdChngUserId; 

	public void setCoId(String coId) { 
		this.coId = coId; 
	} 
	public String getCoId() { 
		return this.coId; 
	} 
	public void setUserId(String userId) { 
		this.userId = userId; 
	} 
	public String getUserId() { 
		return this.userId; 
	} 
	public void setPswdType(String pswdType) { 
		this.pswdType = pswdType; 
	} 
	public String getPswdType() { 
		return this.pswdType; 
	} 
	public void setPswdChngType(String pswdChngType) { 
		this.pswdChngType = pswdChngType; 
	} 
	public String getPswdChngType() { 
		return this.pswdChngType; 
	} 
	public void setPswdChngDateTime(Timestamp pswdChngDateTime) { 
		this.pswdChngDateTime = pswdChngDateTime; 
	} 
	public Timestamp getPswdChngDateTime() { 
		return this.pswdChngDateTime; 
	} 
	public void setPswdChngUserId(String pswdChngUserId) { 
		this.pswdChngUserId = pswdChngUserId; 
	} 
	public String getPswdChngUserId() { 
		return this.pswdChngUserId; 
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