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
 * 관련 테이블 : Hr001m
 */ 
@XmlRootElement(name = "hr001m") 
@XmlType(propOrder = {"coId", "coName", "stDate", "finDate", "prntCoClsf", "prntCoCode", "useIndc", "bizRgsrNo", "rprsntName", "zipCode", "coAddr", "coPhonNo", "coFaxNo", "homePageUrl", "baseOganLev", "sprtLang"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Hr001m implements Serializable {  
	private String coId; 
	private String coName; 
	private String stDate; 
	private String finDate; 
	private String prntCoClsf; 
	private String prntCoCode; 
	private String useIndc; 
	private String bizRgsrNo; 
	private String rprsntName; 
	private String zipCode; 
	private String coAddr; 
	private String coPhonNo; 
	private String coFaxNo; 
	private String homePageUrl; 
	private String baseOganLev; 
	private String sprtLang; 

	public void setCoId(String coId) { 
		this.coId = coId; 
	} 
	public String getCoId() { 
		return this.coId; 
	} 
	public void setCoName(String coName) { 
		this.coName = coName; 
	} 
	public String getCoName() { 
		return this.coName; 
	} 
	public void setStDate(String stDate) { 
		this.stDate = stDate; 
	} 
	public String getStDate() { 
		return this.stDate; 
	} 
	public void setFinDate(String finDate) { 
		this.finDate = finDate; 
	} 
	public String getFinDate() { 
		return this.finDate; 
	} 
	public void setPrntCoClsf(String prntCoClsf) { 
		this.prntCoClsf = prntCoClsf; 
	} 
	public String getPrntCoClsf() { 
		return this.prntCoClsf; 
	} 
	public void setPrntCoCode(String prntCoCode) { 
		this.prntCoCode = prntCoCode; 
	} 
	public String getPrntCoCode() { 
		return this.prntCoCode; 
	} 
	public void setUseIndc(String useIndc) { 
		this.useIndc = useIndc; 
	} 
	public String getUseIndc() { 
		return this.useIndc; 
	} 
	public void setBizRgsrNo(String bizRgsrNo) { 
		this.bizRgsrNo = bizRgsrNo; 
	} 
	public String getBizRgsrNo() { 
		return this.bizRgsrNo; 
	} 
	public void setRprsntName(String rprsntName) { 
		this.rprsntName = rprsntName; 
	} 
	public String getRprsntName() { 
		return this.rprsntName; 
	} 
	public void setZipCode(String zipCode) { 
		this.zipCode = zipCode; 
	} 
	public String getZipCode() { 
		return this.zipCode; 
	} 
	public void setCoAddr(String coAddr) { 
		this.coAddr = coAddr; 
	} 
	public String getCoAddr() { 
		return this.coAddr; 
	} 
	public void setCoPhonNo(String coPhonNo) { 
		this.coPhonNo = coPhonNo; 
	} 
	public String getCoPhonNo() { 
		return this.coPhonNo; 
	} 
	public void setCoFaxNo(String coFaxNo) { 
		this.coFaxNo = coFaxNo; 
	} 
	public String getCoFaxNo() { 
		return this.coFaxNo; 
	} 
	public void setHomePageUrl(String homePageUrl) { 
		this.homePageUrl = homePageUrl; 
	} 
	public String getHomePageUrl() { 
		return this.homePageUrl; 
	} 
	public void setBaseOganLev(String baseOganLev) { 
		this.baseOganLev = baseOganLev; 
	} 
	public String getBaseOganLev() { 
		return this.baseOganLev; 
	} 
	public void setSprtLang(String sprtLang) { 
		this.sprtLang = sprtLang; 
	} 
	public String getSprtLang() { 
		return this.sprtLang; 
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