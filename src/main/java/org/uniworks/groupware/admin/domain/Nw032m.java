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
 * 관련 테이블 : Nw032m
 */ 
@XmlRootElement(name = "nw032m") 
@XmlType(propOrder = {"cntnId", "coId", "stDate", "finDate", "useIndc"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Nw032m implements Serializable {  
	private String cntnId; 
	private String coId; 
	private Date stDate; 
	private Date finDate; 
	private String useIndc;

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
	public void setStDate(Date stDate) { 
		this.stDate = stDate; 
	} 
	public Date getStDate() { 
		return this.stDate; 
	} 
	public void setFinDate(Date finDate) { 
		this.finDate = finDate; 
	} 
	public Date getFinDate() { 
		return this.finDate; 
	} 
	public String getUseIndc() {
		return useIndc;
	}
	public void setUseIndc(String useIndc) {
		this.useIndc = useIndc;
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