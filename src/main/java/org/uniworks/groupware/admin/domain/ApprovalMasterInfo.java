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
 * @author Chungwan Park
 * ApprovalMaster.java 2012. 2. 17.
 */
@XmlRootElement(name = "ApprovalMasterInfo") 
@XmlType(propOrder = {"coId", "apprMstId", "apprTmplType", "apprDesc", "rcptIndc", "rfncIndc", "cprtnIndc", "cprtnType", "atndIndc", "crcltnIndc", "dcsnRuleIndc", "crtId", "createDate", "chngId", "chngDate", "basePrsvTerm", "apprLevel"}) 
@AutoProperty 
@SuppressWarnings("serial")
public class ApprovalMasterInfo implements Serializable {
	private String coId;	//회사구분
	private String apprMstId;	//결재 Master ID
	private String apprTmplType;	//결재양식유형
	private String apprDesc;	//결재명칭
	private String rcptIndc;	//수신처유무
	private String rfncIndc;	//참조처유무
	private String cprtnIndc;	//협조결재유무
	private String cprtnType;	//협조결재유형
	private String atndIndc;	//참석자유무
	private String crcltnIndc;	//회람유무
	private String dcsnRuleIndc;	//전결규정유무
	private String crtId;	//생성 ID
	private Date createDate;	//생성일자
	private String chngId;	//변경 ID
	private Date chngDate;	//변경일자
	private String basePrsvTerm;	//기본 보존연한	
	private int apprLevel;	//결재차수정보(ApprovalLevel Class)
	
	
	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	public String getApprMstId() {
		return apprMstId;
	}
	public void setApprMstId(String apprMstId) {
		this.apprMstId = apprMstId;
	}
	public String getApprTmplType() {
		return apprTmplType;
	}
	public void setApprTmplType(String apprTmplType) {
		this.apprTmplType = apprTmplType;
	}
	public String getApprDesc() {
		return apprDesc;
	}
	public void setApprDesc(String apprDesc) {
		this.apprDesc = apprDesc;
	}
	public String getRcptIndc() {
		return rcptIndc;
	}
	public void setRcptIndc(String rcptIndc) {
		this.rcptIndc = rcptIndc;
	}
	public String getRfncIndc() {
		return rfncIndc;
	}
	public void setRfncIndc(String rfncIndc) {
		this.rfncIndc = rfncIndc;
	}
	public String getCprtnIndc() {
		return cprtnIndc;
	}
	public void setCprtnIndc(String cprtnIndc) {
		this.cprtnIndc = cprtnIndc;
	}
	public String getCprtnType() {
		return cprtnType;
	}
	public void setCprtnType(String cprtnType) {
		this.cprtnType = cprtnType;
	}
	public String getAtndIndc() {
		return atndIndc;
	}
	public void setAtndIndc(String atndIndc) {
		this.atndIndc = atndIndc;
	}
	public String getCrcltnIndc() {
		return crcltnIndc;
	}
	public void setCrcltnIndc(String crcltnIndc) {
		this.crcltnIndc = crcltnIndc;
	}
	public String getDcsnRuleIndc() {
		return dcsnRuleIndc;
	}
	public void setDcsnRuleIndc(String dcsnRuleIndc) {
		this.dcsnRuleIndc = dcsnRuleIndc;
	}
	public String getCrtId() {
		return crtId;
	}
	public void setCrtId(String crtId) {
		this.crtId = crtId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getChngId() {
		return chngId;
	}
	public void setChngId(String chngId) {
		this.chngId = chngId;
	}
	public Date getChngDate() {
		return chngDate;
	}
	public void setChngDate(Date chngDate) {
		this.chngDate = chngDate;
	}
	public int getApprLevel() {
		return apprLevel;
	}
	public void setApprLevel(int apprLevel) {
		this.apprLevel = apprLevel;
	}
	public String getBasePrsvTerm() {
		return basePrsvTerm;
	}
	public void setBasePrsvTerm(String basePrsvTerm) {
		this.basePrsvTerm = basePrsvTerm;
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
