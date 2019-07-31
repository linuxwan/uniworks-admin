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
 * 관련 테이블 : Nw100m
 * 사용자 등록 정보를 관리하기 위한 클래스.
 */ 
@XmlRootElement(name = "user") 
@XmlType(propOrder = {"coId", "userId", "empName", "deptCode", "deptDesc", "dutyCode", "dutyDesc", "pstnCode", "pstnDesc", "pswd", "pswdChngDate", "pswdInitIndc", "internalMailAddr", "outsideMailAddr", "cnfmPswd", "cnfmPswdChngDate", "userRgsrDate", "useIndc"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class User implements Serializable {  
	private String coId; 
	private String userId; 
	private String empName;
	private String deptCode;
	private String deptDesc;
	private String dutyCode;	//직급코드
	private String dutyDesc;	//직급명칭
	private String pstnCode;	//보직코드
	private String pstnDesc;	//보직명칭
	private String pswd; 
	private Timestamp pswdChngDate; 
	private String pswdInitIndc; 
	private String internalMailAddr; 
	private String outsideMailAddr; 
	private String cnfmPswd; 
	private Timestamp cnfmPswdChngDate; 
	private Timestamp userRgsrDate; 
	private String useIndc; 

	public void setCoId(String coId) { 
		this.coId = coId; 
	} 
	public String getCoId() { 
		return this.coId; 
	} 
	public void setUserId(String userId) { 
		this.userId = userId; 
	} 
	public String getUserId() { 
		return this.userId; 
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
	public void setPswdInitIndc(String pswdInitIndc) { 
		this.pswdInitIndc = pswdInitIndc; 
	} 
	public String getPswdInitIndc() { 
		return this.pswdInitIndc; 
	} 
	public void setInternalMailAddr(String internalMailAddr) { 
		this.internalMailAddr = internalMailAddr; 
	} 
	public String getInternalMailAddr() { 
		return this.internalMailAddr; 
	} 
	public void setOutsideMailAddr(String outsideMailAddr) { 
		this.outsideMailAddr = outsideMailAddr; 
	} 
	public String getOutsideMailAddr() { 
		return this.outsideMailAddr; 
	} 
	public void setCnfmPswd(String cnfmPswd) { 
		this.cnfmPswd = cnfmPswd; 
	} 
	public String getCnfmPswd() { 
		return this.cnfmPswd; 
	} 
	public void setCnfmPswdChngDate(Timestamp cnfmPswdChngDate) { 
		this.cnfmPswdChngDate = cnfmPswdChngDate; 
	} 
	public Timestamp getCnfmPswdChngDate() { 
		return this.cnfmPswdChngDate; 
	} 
	public void setUserRgsrDate(Timestamp userRgsrDate) { 
		this.userRgsrDate = userRgsrDate; 
	} 
	public Timestamp getUserRgsrDate() { 
		return this.userRgsrDate; 
	} 
	public void setUseIndc(String useIndc) { 
		this.useIndc = useIndc; 
	} 
	public String getUseIndc() { 
		return this.useIndc; 
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