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
 * 관련 테이블 : Cm010c
 */ 
@XmlRootElement(name = "cm010c") 
@XmlType(propOrder = {"adminId", "coId", "empNo", "pswd", "pswdChngDate", "useStDate", "useFinDate", "adminType"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Cm010c implements Serializable {  
	private String adminId; 
	private String coId; 
	private String empNo; 
	private String pswd; 
	private Timestamp pswdChngDate; 
	private Timestamp useStDate; 
	private Timestamp useFinDate; 
	private String adminType; 

	public void setAdminId(String adminId) { 
		this.adminId = adminId; 
	} 
	public String getAdminId() { 
		return this.adminId; 
	} 
	public void setCoId(String coId) { 
		this.coId = coId; 
	} 
	public String getCoId() { 
		return this.coId; 
	} 
	public void setEmpNo(String empNo) { 
		this.empNo = empNo; 
	} 
	public String getEmpNo() { 
		return this.empNo; 
	} 
	public void setPswd(String pswd) { 
		this.pswd = pswd; 
	} 
	public String getPswd() { 
		return this.pswd; 
	} 
	public void setPswdChngDate(Timestamp pswdChngDate) { 
		this.pswdChngDate = pswdChngDate; 
	} 
	public Timestamp getPswdChngDate() { 
		return this.pswdChngDate; 
	} 
	public void setUseStDate(Timestamp useStDate) { 
		this.useStDate = useStDate; 
	} 
	public Timestamp getUseStDate() { 
		return this.useStDate; 
	} 
	public void setUseFinDate(Timestamp useFinDate) { 
		this.useFinDate = useFinDate; 
	} 
	public Timestamp getUseFinDate() { 
		return this.useFinDate; 
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