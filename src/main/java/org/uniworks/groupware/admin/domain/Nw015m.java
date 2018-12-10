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
 * 관련 테이블 : Nw015m
 */ 
@XmlRootElement(name = "nw015m") 
@XmlType(propOrder = {"coId", "apprItemId", "apprMstId", "seqNo"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Nw015m implements Serializable {  
	private String coId; 
	private String apprItemId; 
	private String apprMstId; 
	private int seqNo; 

	public void setCoId(String coId) { 
		this.coId = coId; 
	} 
	public String getCoId() { 
		return this.coId; 
	} 
	public void setApprItemId(String apprItemId) { 
		this.apprItemId = apprItemId; 
	} 
	public String getApprItemId() { 
		return this.apprItemId; 
	} 
	public void setApprMstId(String apprMstId) { 
		this.apprMstId = apprMstId; 
	} 
	public String getApprMstId() { 
		return this.apprMstId; 
	} 
	public void setSeqNo(int seqNo) { 
		this.seqNo = seqNo; 
	} 
	public int getSeqNo() { 
		return this.seqNo; 
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