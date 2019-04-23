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
@XmlRootElement(name = "user") 
@XmlType(propOrder = {"coId", "userId", "empName", "role", "useIndc", "dutyCode", "dutyDesc", "pstnCode", "pstnDesc", "deptCode", "deptDesc"})
@AutoProperty 
@SuppressWarnings("serial") 
public class UserRole implements Serializable {
	private String coId;
	private String userId;
	private String empName;
	private String role;
	private String useIndc;
	private String dutyCode;
	private String dutyDesc;
	private String pstnCode;
	private String pstnDesc;
	private String deptCode;
	private String deptDesc;
	
	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUseIndc() {
		return useIndc;
	}
	public void setUseIndc(String useIndc) {
		this.useIndc = useIndc;
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
