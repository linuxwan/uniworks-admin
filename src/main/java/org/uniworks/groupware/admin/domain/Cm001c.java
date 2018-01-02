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
 * 관련 테이블 : Cm001c
 */ 
@XmlRootElement(name = "cm001c") 
@XmlType(propOrder = {"coId", "majCode", "rescKey", "majCodeName", "majCodeDesc", "useIndc"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Cm001c implements Serializable {
	private String coId;
	private String majCode; 
	private String rescKey; 
	private String majCodeName; 
	private String majCodeDesc; 
	private String useIndc; 

	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	public void setMajCode(String majCode) { 
		this.majCode = majCode; 
	} 
	public String getMajCode() { 
		return this.majCode; 
	} 
	public void setRescKey(String rescKey) { 
		this.rescKey = rescKey; 
	} 
	public String getRescKey() { 
		return this.rescKey; 
	} 
	public void setMajCodeName(String majCodeName) { 
		this.majCodeName = majCodeName; 
	} 
	public String getMajCodeName() { 
		return this.majCodeName; 
	} 
	public void setMajCodeDesc(String majCodeDesc) { 
		this.majCodeDesc = majCodeDesc; 
	} 
	public String getMajCodeDesc() { 
		return this.majCodeDesc; 
	} 
	public void setUseIndc(String useIndc) { 
		this.useIndc = useIndc; 
	} 
	public String getUseIndc() { 
		return this.useIndc; 
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