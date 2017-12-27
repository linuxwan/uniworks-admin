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
 *
 */
@XmlRootElement(name = "menu")
@XmlType(propOrder = {"majCode","subCode","rescKey","useIndc","rescKeyValue","rescKeyDesc"})
@AutoProperty 
@SuppressWarnings("serial")
public class CommonCode implements Serializable {
	private String majCode;	//주코드
	private String subCode;	//부코드
	private String rescKey;	//리소스키
	private String useIndc;	//사용유무
	private String rescKeyValue;	//리소스키값
	private String rescKeyDesc;		//리소스키상세
	
	public String getMajCode() {
		return majCode;
	}
	public void setMajCode(String majCode) {
		this.majCode = majCode;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getRescKey() {
		return rescKey;
	}
	public void setRescKey(String rescKey) {
		this.rescKey = rescKey;
	}
	public String getUseIndc() {
		return useIndc;
	}
	public void setUseIndc(String useIndc) {
		this.useIndc = useIndc;
	}
	public String getRescKeyValue() {
		return rescKeyValue;
	}
	public void setRescKeyValue(String rescKeyValue) {
		this.rescKeyValue = rescKeyValue;
	}
	public String getRescKeyDesc() {
		return rescKeyDesc;
	}
	public void setRescKeyDesc(String rescKeyDesc) {
		this.rescKeyDesc = rescKeyDesc;
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
