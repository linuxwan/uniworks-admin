/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.domain;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.pojomatic.annotations.AutoProperty;

/**
 * @author Park Chung Wan
 *
 */
@XmlRootElement(name = "oganInfo") 
@XmlType(propOrder = {"coId", "oganCode", "oganLev", "oganEstbDate", "oganName", "rescKey", "oganClsDate", "highOganCode", "highOganLev", "oganType", "oganDesc", "lang"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class OganInfo {
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
	private String lang;
	
	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	public String getOganCode() {
		return oganCode;
	}
	public void setOganCode(String oganCode) {
		this.oganCode = oganCode;
	}
	public String getOganLev() {
		return oganLev;
	}
	public void setOganLev(String oganLev) {
		this.oganLev = oganLev;
	}
	public Timestamp getOganEstbDate() {
		return oganEstbDate;
	}
	public void setOganEstbDate(Timestamp oganEstbDate) {
		this.oganEstbDate = oganEstbDate;
	}
	public String getOganName() {
		return oganName;
	}
	public void setOganName(String oganName) {
		this.oganName = oganName;
	}
	public String getRescKey() {
		return rescKey;
	}
	public void setRescKey(String rescKey) {
		this.rescKey = rescKey;
	}
	public Timestamp getOganClsDate() {
		return oganClsDate;
	}
	public void setOganClsDate(Timestamp oganClsDate) {
		this.oganClsDate = oganClsDate;
	}
	public String getHighOganCode() {
		return highOganCode;
	}
	public void setHighOganCode(String highOganCode) {
		this.highOganCode = highOganCode;
	}
	public String getHighOganLev() {
		return highOganLev;
	}
	public void setHighOganLev(String highOganLev) {
		this.highOganLev = highOganLev;
	}
	public String getOganType() {
		return oganType;
	}
	public void setOganType(String oganType) {
		this.oganType = oganType;
	}
	public String getOganDesc() {
		return oganDesc;
	}
	public void setOganDesc(String oganDesc) {
		this.oganDesc = oganDesc;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
}
