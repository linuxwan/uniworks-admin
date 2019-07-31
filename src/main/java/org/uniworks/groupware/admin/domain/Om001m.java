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
 * 관련 테이블 : Om001m
 */ 
@XmlRootElement(name = "om001m") 
@XmlType(propOrder = {"coId", "oganCode", "oganLev", "oganEstbDate", "oganName", "rescKey", "oganClsDate", "highOganCode", "highOganLev", "oganType", "oganDesc"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Om001m implements Serializable {  
	private String coId; 
	private String oganCode; 
	private String oganLev; 
	private Timestamp oganEstbDate; 
	private String oganName; 
	private String rescKey; 
	private Timestamp oganClsDate; 
	private String highOganCode; 
	private String highOganLev; 
	private String oganType; 
	private String oganDesc; 

	public void setCoId(String coId) { 
		this.coId = coId; 
	} 
	public String getCoId() { 
		return this.coId; 
	} 
	public void setOganCode(String oganCode) { 
		this.oganCode = oganCode; 
	} 
	public String getOganCode() { 
		return this.oganCode; 
	} 
	public void setOganLev(String oganLev) { 
		this.oganLev = oganLev; 
	} 
	public String getOganLev() { 
		return this.oganLev; 
	} 
	public void setOganEstbDate(Timestamp oganEstbDate) { 
		this.oganEstbDate = oganEstbDate; 
	} 
	public Timestamp getOganEstbDate() { 
		return this.oganEstbDate; 
	} 
	public void setOganName(String oganName) { 
		this.oganName = oganName; 
	} 
	public String getOganName() { 
		return this.oganName; 
	} 
	public void setRescKey(String rescKey) { 
		this.rescKey = rescKey; 
	} 
	public String getRescKey() { 
		return this.rescKey; 
	} 
	public void setOganClsDate(Timestamp oganClsDate) { 
		this.oganClsDate = oganClsDate; 
	} 
	public Timestamp getOganClsDate() { 
		return this.oganClsDate; 
	} 
	public void setHighOganCode(String highOganCode) { 
		this.highOganCode = highOganCode; 
	} 
	public String getHighOganCode() { 
		return this.highOganCode; 
	} 
	public void setHighOganLev(String highOganLev) { 
		this.highOganLev = highOganLev; 
	} 
	public String getHighOganLev() { 
		return this.highOganLev; 
	} 
	public void setOganType(String oganType) { 
		this.oganType = oganType; 
	} 
	public String getOganType() { 
		return this.oganType; 
	} 
	public void setOganDesc(String oganDesc) { 
		this.oganDesc = oganDesc; 
	} 
	public String getOganDesc() { 
		return this.oganDesc; 
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