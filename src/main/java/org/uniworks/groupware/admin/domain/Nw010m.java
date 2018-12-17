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
 * 관련 테이블 : Nw010m
 */ 
@XmlRootElement(name = "nw010m") 
@XmlType(propOrder = {"coId", "apprMstId", "apprDesc", "rcptIndc", "rfncIndc", "cprtnIndc", "cprtnType", "crcltnIndc", "dcsnRuleIndc", "crtId", "createDate", "chngId", "chngDate", "atndIndc", "apprTmplType", "basePrsvTerm"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Nw010m implements Serializable {  
	private String coId; 
	private String apprMstId; 
	private String apprDesc; 
	private String rcptIndc; 
	private String rfncIndc; 
	private String cprtnIndc; 
	private String cprtnType; 
	private String crcltnIndc; 
	private String dcsnRuleIndc; 
	private String crtId; 
	private Timestamp createDate; 
	private String chngId; 
	private Timestamp chngDate; 
	private String atndIndc; 
	private String apprTmplType; 
	private String basePrsvTerm; 

	public void setCoId(String coId) { 
		this.coId = coId; 
	} 
	public String getCoId() { 
		return this.coId; 
	} 
	public void setApprMstId(String apprMstId) { 
		this.apprMstId = apprMstId; 
	} 
	public String getApprMstId() { 
		return this.apprMstId; 
	} 
	public void setApprDesc(String apprDesc) { 
		this.apprDesc = apprDesc; 
	} 
	public String getApprDesc() { 
		return this.apprDesc; 
	} 
	public void setRcptIndc(String rcptIndc) { 
		this.rcptIndc = rcptIndc; 
	} 
	public String getRcptIndc() { 
		return this.rcptIndc; 
	} 
	public void setRfncIndc(String rfncIndc) { 
		this.rfncIndc = rfncIndc; 
	} 
	public String getRfncIndc() { 
		return this.rfncIndc; 
	} 
	public void setCprtnIndc(String cprtnIndc) { 
		this.cprtnIndc = cprtnIndc; 
	} 
	public String getCprtnIndc() { 
		return this.cprtnIndc; 
	} 
	public void setCprtnType(String cprtnType) { 
		this.cprtnType = cprtnType; 
	} 
	public String getCprtnType() { 
		return this.cprtnType; 
	} 
	public void setCrcltnIndc(String crcltnIndc) { 
		this.crcltnIndc = crcltnIndc; 
	} 
	public String getCrcltnIndc() { 
		return this.crcltnIndc; 
	} 
	public void setDcsnRuleIndc(String dcsnRuleIndc) { 
		this.dcsnRuleIndc = dcsnRuleIndc; 
	} 
	public String getDcsnRuleIndc() { 
		return this.dcsnRuleIndc; 
	} 
	public void setCrtId(String crtId) { 
		this.crtId = crtId; 
	} 
	public String getCrtId() { 
		return this.crtId; 
	} 
	public void setCreateDate(Timestamp createDate) { 
		this.createDate = createDate; 
	} 
	public Timestamp getCreateDate() { 
		return this.createDate; 
	} 
	public void setChngId(String chngId) { 
		this.chngId = chngId; 
	} 
	public String getChngId() { 
		return this.chngId; 
	} 
	public void setChngDate(Timestamp chngDate) { 
		this.chngDate = chngDate; 
	} 
	public Timestamp getChngDate() { 
		return this.chngDate; 
	} 
	public void setAtndIndc(String atndIndc) { 
		this.atndIndc = atndIndc; 
	} 
	public String getAtndIndc() { 
		return this.atndIndc; 
	} 
	public void setApprTmplType(String apprTmplType) { 
		this.apprTmplType = apprTmplType; 
	} 
	public String getApprTmplType() { 
		return this.apprTmplType; 
	} 
	public void setBasePrsvTerm(String basePrsvTerm) { 
		this.basePrsvTerm = basePrsvTerm; 
	} 
	public String getBasePrsvTerm() { 
		return this.basePrsvTerm; 
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