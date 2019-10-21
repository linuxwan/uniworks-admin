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
 * 관련 테이블 : Nw034m
 * 사용자 등록 정보를 관리하기 위한 클래스.
 */ 
@XmlRootElement(name = "chargeUser") 
@XmlType(propOrder = {"coId", "cntnId", "cntnName", "userId", "empName", "deptCode", "deptDesc", "dutyCode", "dutyDesc", "pstnCode", "pstnDesc"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class ChargeUser implements Serializable {  
	private String coId; 
	private String cntnId;
	private String cntnName;
	private String userId; 
	private String empName;
	private String deptCode;
	private String deptDesc;
	private String dutyCode;	//직급코드
	private String dutyDesc;	//직급명칭
	private String pstnCode;	//보직코드
	private String pstnDesc;	//보직명칭
		
	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	public String getCntnId() {
		return cntnId;
	}
	public void setCntnId(String cntnId) {
		this.cntnId = cntnId;
	}
	public String getCntnName() {
		return cntnName;
	}
	public void setCntnName(String cntnName) {
		this.cntnName = cntnName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptDesc() {
		return deptDesc;
	}
	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}
	public String getDutyCode() {
		return dutyCode;
	}
	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}
	public String getDutyDesc() {
		return dutyDesc;
	}
	public void setDutyDesc(String dutyDesc) {
		this.dutyDesc = dutyDesc;
	}
	public String getPstnCode() {
		return pstnCode;
	}
	public void setPstnCode(String pstnCode) {
		this.pstnCode = pstnCode;
	}
	public String getPstnDesc() {
		return pstnDesc;
	}
	public void setPstnDesc(String pstnDesc) {
		this.pstnDesc = pstnDesc;
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