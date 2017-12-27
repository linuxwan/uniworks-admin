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
 * 관련 테이블 : Hr002m
 */ 
@XmlRootElement(name = "hr002m") 
@XmlType(propOrder = {"coId", "chrgId", "pswd", "empNo", "seqNo", "stDate", "finDate", "hrChrg", "chrgIndc", "pswdInitIndc", "adminType"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Hr002m implements Serializable {  
	private String coId; 
	private String chrgId; 
	private String pswd; 
	private String empNo; 
	private long seqNo; 
	private String stDate; 
	private String finDate; 
	private String hrChrg; 
	private String chrgIndc; 
	private String pswdInitIndc; 
	private String adminType; 

	public void setCoId(String coId) { 
		this.coId = coId; 
	} 
	public String getCoId() { 
		return this.coId; 
	} 
	public void setChrgId(String chrgId) { 
		this.chrgId = chrgId; 
	} 
	public String getChrgId() { 
		return this.chrgId; 
	} 
	public void setPswd(String pswd) { 
		this.pswd = pswd; 
	} 
	public String getPswd() { 
		return this.pswd; 
	} 
	public void setEmpNo(String empNo) { 
		this.empNo = empNo; 
	} 
	public String getEmpNo() { 
		return this.empNo; 
	} 
	public void setSeqNo(long seqNo) { 
		this.seqNo = seqNo; 
	} 
	public long getSeqNo() { 
		return this.seqNo; 
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
	public void setHrChrg(String hrChrg) { 
		this.hrChrg = hrChrg; 
	} 
	public String getHrChrg() { 
		return this.hrChrg; 
	} 
	public void setChrgIndc(String chrgIndc) { 
		this.chrgIndc = chrgIndc; 
	} 
	public String getChrgIndc() { 
		return this.chrgIndc; 
	} 
	public void setPswdInitIndc(String pswdInitIndc) { 
		this.pswdInitIndc = pswdInitIndc; 
	} 
	public String getPswdInitIndc() { 
		return this.pswdInitIndc; 
	} 
	public void setAdminType(String adminType) { 
		this.adminType = adminType; 
	} 
	public String getAdminType() { 
		return this.adminType; 
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