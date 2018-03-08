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
 * 관련 테이블 : Om002m
 */ 
@XmlRootElement(name = "om002m") 
@XmlType(propOrder = {"rescKey", "locale", "rescKeyValue", "rescKeyValueDesc"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Om002m implements Serializable {  
	private String rescKey; 
	private String locale; 
	private String rescKeyValue; 
	private String rescKeyValueDesc;

	public void setRescKey(String rescKey) { 
		this.rescKey = rescKey; 
	} 
	public String getRescKey() { 
		return this.rescKey; 
	} 
	public void setLocale(String locale) { 
		this.locale = locale; 
	} 
	public String getLocale() { 
		return this.locale; 
	} 
	public void setRescKeyValue(String rescKeyValue) { 
		this.rescKeyValue = rescKeyValue; 
	} 
	public String getRescKeyValue() { 
		return this.rescKeyValue; 
	} 
	public String getRescKeyValueDesc() {
		return rescKeyValueDesc;
	}
	public void setRescKeyValueDesc(String rescKeyValueDesc) {
		this.rescKeyValueDesc = rescKeyValueDesc;
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