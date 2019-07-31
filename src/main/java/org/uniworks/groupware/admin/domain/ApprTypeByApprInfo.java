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
 * @author Park Chung Wan
 *
 */
@XmlRootElement(name = "ApprTypeByApprInfo") 
@XmlType(propOrder = {"coId", "apprItemId", "apprItemName", "apprMstId", "apprDesc", "seqNo"}) 
@AutoProperty 
@SuppressWarnings("serial")
public class ApprTypeByApprInfo implements Serializable {
	private String coId;			//회사구분
	private String apprItemId;		//결재유형 ID
	private String apprItemName;	//결재유형 명칭
	private String apprMstId;		//결재 Master ID
	private String apprDesc;		//결재 Master 명칭
	private int seqNo;				//Sequence No.
	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	public String getApprItemId() {
		return apprItemId;
	}
	public void setApprItemId(String apprItemId) {
		this.apprItemId = apprItemId;
	}
	public String getApprItemName() {
		return apprItemName;
	}
	public void setApprItemName(String apprItemName) {
		this.apprItemName = apprItemName;
	}
	public String getApprMstId() {
		return apprMstId;
	}
	public void setApprMstId(String apprMstId) {
		this.apprMstId = apprMstId;
	}
	public String getApprDesc() {
		return apprDesc;
	}
	public void setApprDesc(String apprDesc) {
		this.apprDesc = apprDesc;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
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
