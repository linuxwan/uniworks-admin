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
 * 관련 테이블 : Hr010m
 */ 
@XmlRootElement(name = "hr010m") 
@XmlType(propOrder = {"coId", "empNo", "sysUserId", "empNameKor", "empNameChn", "empNameEng", "workIndc", "dutyCode", "pstnCode", "asgnOganCode1", "asgnOganDesc1", "asgnOganCode2", "asgnOganDesc2", "asgnOganCode3", "asgnOganDesc3", "asgnOganCode4", "asgnOganDesc4", "asgnOganCode5", "asgnOganDesc5", "asgnOganCode6", "asgnOganDesc6", "asgnOganCode7", "asgnOganDesc7", "asgnOganCode8", "asgnOganDesc8", "asgnOganCode9", "asgnOganDesc9", "asgnOganCode10", "asgnOganDesc10", "asgnOganCode11", "asgnOganDesc11", "asgnOganCode12", "asgnOganDesc12", "offcTelNo", "moblPhonNo", "birthDate", "ssn", "mailAddr", "entrDate", "rsgnDate", "offcOrdDate", "marriageIndc", "marriageDate", "orgnOganCode1", "orgnOganDesc1", "orgnOganCode2", "orgnOganDesc2", "orgnOganCode3", "orgnOganDesc3", "orgnOganCode4", "orgnOganDesc4", "orgnOganCode5", "orgnOganDesc5", "orgnOganCode6", "orgnOganDesc6", "orgnOganCode7", "orgnOganDesc7", "orgnOganCode8", "orgnOganDesc8", "orgnOganCode9", "orgnOganDesc9", "orgnOganCode10", "orgnOganDesc10", "orgnOganCode11", "orgnOganDesc11", "orgnOganCode12", "orgnOganDesc12", "baseAsgnOganLev", "baseOrgnOganLev"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Hr010m implements Serializable {  
	private String coId; 
	private String empNo; 
	private String sysUserId; 
	private String empNameKor; 
	private String empNameChn; 
	private String empNameEng; 
	private String workIndc; 
	private String dutyCode; 
	private String pstnCode; 
	private String asgnOganCode1; 
	private String asgnOganDesc1; 
	private String asgnOganCode2; 
	private String asgnOganDesc2; 
	private String asgnOganCode3; 
	private String asgnOganDesc3; 
	private String asgnOganCode4; 
	private String asgnOganDesc4; 
	private String asgnOganCode5; 
	private String asgnOganDesc5; 
	private String asgnOganCode6; 
	private String asgnOganDesc6; 
	private String asgnOganCode7; 
	private String asgnOganDesc7; 
	private String asgnOganCode8; 
	private String asgnOganDesc8; 
	private String asgnOganCode9; 
	private String asgnOganDesc9; 
	private String asgnOganCode10; 
	private String asgnOganDesc10; 
	private String asgnOganCode11; 
	private String asgnOganDesc11; 
	private String asgnOganCode12; 
	private String asgnOganDesc12; 
	private String offcTelNo; 
	private String moblPhonNo; 
	private String birthDate; 
	private String ssn; 
	private String mailAddr; 
	private String entrDate; 
	private String rsgnDate; 
	private String offcOrdDate; 
	private String marriageIndc; 
	private String marriageDate; 
	private String orgnOganCode1; 
	private String orgnOganDesc1; 
	private String orgnOganCode2; 
	private String orgnOganDesc2; 
	private String orgnOganCode3; 
	private String orgnOganDesc3; 
	private String orgnOganCode4; 
	private String orgnOganDesc4; 
	private String orgnOganCode5; 
	private String orgnOganDesc5; 
	private String orgnOganCode6; 
	private String orgnOganDesc6; 
	private String orgnOganCode7; 
	private String orgnOganDesc7; 
	private String orgnOganCode8; 
	private String orgnOganDesc8; 
	private String orgnOganCode9; 
	private String orgnOganDesc9; 
	private String orgnOganCode10; 
	private String orgnOganDesc10; 
	private String orgnOganCode11; 
	private String orgnOganDesc11; 
	private String orgnOganCode12; 
	private String orgnOganDesc12; 
	private String baseAsgnOganLev; 
	private String baseOrgnOganLev; 

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
	public void setSysUserId(String sysUserId) { 
		this.sysUserId = sysUserId; 
	} 
	public String getSysUserId() { 
		return this.sysUserId; 
	} 
	public void setEmpNameKor(String empNameKor) { 
		this.empNameKor = empNameKor; 
	} 
	public String getEmpNameKor() { 
		return this.empNameKor; 
	} 
	public void setEmpNameChn(String empNameChn) { 
		this.empNameChn = empNameChn; 
	} 
	public String getEmpNameChn() { 
		return this.empNameChn; 
	} 
	public void setEmpNameEng(String empNameEng) { 
		this.empNameEng = empNameEng; 
	} 
	public String getEmpNameEng() { 
		return this.empNameEng; 
	} 
	public void setWorkIndc(String workIndc) { 
		this.workIndc = workIndc; 
	} 
	public String getWorkIndc() { 
		return this.workIndc; 
	} 
	public void setDutyCode(String dutyCode) { 
		this.dutyCode = dutyCode; 
	} 
	public String getDutyCode() { 
		return this.dutyCode; 
	} 
	public void setPstnCode(String pstnCode) { 
		this.pstnCode = pstnCode; 
	} 
	public String getPstnCode() { 
		return this.pstnCode; 
	} 
	public void setAsgnOganCode1(String asgnOganCode1) { 
		this.asgnOganCode1 = asgnOganCode1; 
	} 
	public String getAsgnOganCode1() { 
		return this.asgnOganCode1; 
	} 
	public void setAsgnOganDesc1(String asgnOganDesc1) { 
		this.asgnOganDesc1 = asgnOganDesc1; 
	} 
	public String getAsgnOganDesc1() { 
		return this.asgnOganDesc1; 
	} 
	public void setAsgnOganCode2(String asgnOganCode2) { 
		this.asgnOganCode2 = asgnOganCode2; 
	} 
	public String getAsgnOganCode2() { 
		return this.asgnOganCode2; 
	} 
	public void setAsgnOganDesc2(String asgnOganDesc2) { 
		this.asgnOganDesc2 = asgnOganDesc2; 
	} 
	public String getAsgnOganDesc2() { 
		return this.asgnOganDesc2; 
	} 
	public void setAsgnOganCode3(String asgnOganCode3) { 
		this.asgnOganCode3 = asgnOganCode3; 
	} 
	public String getAsgnOganCode3() { 
		return this.asgnOganCode3; 
	} 
	public void setAsgnOganDesc3(String asgnOganDesc3) { 
		this.asgnOganDesc3 = asgnOganDesc3; 
	} 
	public String getAsgnOganDesc3() { 
		return this.asgnOganDesc3; 
	} 
	public void setAsgnOganCode4(String asgnOganCode4) { 
		this.asgnOganCode4 = asgnOganCode4; 
	} 
	public String getAsgnOganCode4() { 
		return this.asgnOganCode4; 
	} 
	public void setAsgnOganDesc4(String asgnOganDesc4) { 
		this.asgnOganDesc4 = asgnOganDesc4; 
	} 
	public String getAsgnOganDesc4() { 
		return this.asgnOganDesc4; 
	} 
	public void setAsgnOganCode5(String asgnOganCode5) { 
		this.asgnOganCode5 = asgnOganCode5; 
	} 
	public String getAsgnOganCode5() { 
		return this.asgnOganCode5; 
	} 
	public void setAsgnOganDesc5(String asgnOganDesc5) { 
		this.asgnOganDesc5 = asgnOganDesc5; 
	} 
	public String getAsgnOganDesc5() { 
		return this.asgnOganDesc5; 
	} 
	public void setAsgnOganCode6(String asgnOganCode6) { 
		this.asgnOganCode6 = asgnOganCode6; 
	} 
	public String getAsgnOganCode6() { 
		return this.asgnOganCode6; 
	} 
	public void setAsgnOganDesc6(String asgnOganDesc6) { 
		this.asgnOganDesc6 = asgnOganDesc6; 
	} 
	public String getAsgnOganDesc6() { 
		return this.asgnOganDesc6; 
	} 
	public void setAsgnOganCode7(String asgnOganCode7) { 
		this.asgnOganCode7 = asgnOganCode7; 
	} 
	public String getAsgnOganCode7() { 
		return this.asgnOganCode7; 
	} 
	public void setAsgnOganDesc7(String asgnOganDesc7) { 
		this.asgnOganDesc7 = asgnOganDesc7; 
	} 
	public String getAsgnOganDesc7() { 
		return this.asgnOganDesc7; 
	} 
	public void setAsgnOganCode8(String asgnOganCode8) { 
		this.asgnOganCode8 = asgnOganCode8; 
	} 
	public String getAsgnOganCode8() { 
		return this.asgnOganCode8; 
	} 
	public void setAsgnOganDesc8(String asgnOganDesc8) { 
		this.asgnOganDesc8 = asgnOganDesc8; 
	} 
	public String getAsgnOganDesc8() { 
		return this.asgnOganDesc8; 
	} 
	public void setAsgnOganCode9(String asgnOganCode9) { 
		this.asgnOganCode9 = asgnOganCode9; 
	} 
	public String getAsgnOganCode9() { 
		return this.asgnOganCode9; 
	} 
	public void setAsgnOganDesc9(String asgnOganDesc9) { 
		this.asgnOganDesc9 = asgnOganDesc9; 
	} 
	public String getAsgnOganDesc9() { 
		return this.asgnOganDesc9; 
	} 
	public void setAsgnOganCode10(String asgnOganCode10) { 
		this.asgnOganCode10 = asgnOganCode10; 
	} 
	public String getAsgnOganCode10() { 
		return this.asgnOganCode10; 
	} 
	public void setAsgnOganDesc10(String asgnOganDesc10) { 
		this.asgnOganDesc10 = asgnOganDesc10; 
	} 
	public String getAsgnOganDesc10() { 
		return this.asgnOganDesc10; 
	} 
	public void setAsgnOganCode11(String asgnOganCode11) { 
		this.asgnOganCode11 = asgnOganCode11; 
	} 
	public String getAsgnOganCode11() { 
		return this.asgnOganCode11; 
	} 
	public void setAsgnOganDesc11(String asgnOganDesc11) { 
		this.asgnOganDesc11 = asgnOganDesc11; 
	} 
	public String getAsgnOganDesc11() { 
		return this.asgnOganDesc11; 
	} 
	public void setAsgnOganCode12(String asgnOganCode12) { 
		this.asgnOganCode12 = asgnOganCode12; 
	} 
	public String getAsgnOganCode12() { 
		return this.asgnOganCode12; 
	} 
	public void setAsgnOganDesc12(String asgnOganDesc12) { 
		this.asgnOganDesc12 = asgnOganDesc12; 
	} 
	public String getAsgnOganDesc12() { 
		return this.asgnOganDesc12; 
	} 
	public void setOffcTelNo(String offcTelNo) { 
		this.offcTelNo = offcTelNo; 
	} 
	public String getOffcTelNo() { 
		return this.offcTelNo; 
	} 
	public void setMoblPhonNo(String moblPhonNo) { 
		this.moblPhonNo = moblPhonNo; 
	} 
	public String getMoblPhonNo() { 
		return this.moblPhonNo; 
	} 
	public void setBirthDate(String birthDate) { 
		this.birthDate = birthDate; 
	} 
	public String getBirthDate() { 
		return this.birthDate; 
	} 
	public void setSsn(String ssn) { 
		this.ssn = ssn; 
	} 
	public String getSsn() { 
		return this.ssn; 
	} 
	public void setMailAddr(String mailAddr) { 
		this.mailAddr = mailAddr; 
	} 
	public String getMailAddr() { 
		return this.mailAddr; 
	} 
	public void setEntrDate(String entrDate) { 
		this.entrDate = entrDate; 
	} 
	public String getEntrDate() { 
		return this.entrDate; 
	} 
	public void setRsgnDate(String rsgnDate) { 
		this.rsgnDate = rsgnDate; 
	} 
	public String getRsgnDate() { 
		return this.rsgnDate; 
	} 
	public void setOffcOrdDate(String offcOrdDate) { 
		this.offcOrdDate = offcOrdDate; 
	} 
	public String getOffcOrdDate() { 
		return this.offcOrdDate; 
	} 
	public void setMarriageIndc(String marriageIndc) { 
		this.marriageIndc = marriageIndc; 
	} 
	public String getMarriageIndc() { 
		return this.marriageIndc; 
	} 
	public void setMarriageDate(String marriageDate) { 
		this.marriageDate = marriageDate; 
	} 
	public String getMarriageDate() { 
		return this.marriageDate; 
	} 
	public void setOrgnOganCode1(String orgnOganCode1) { 
		this.orgnOganCode1 = orgnOganCode1; 
	} 
	public String getOrgnOganCode1() { 
		return this.orgnOganCode1; 
	} 
	public void setOrgnOganDesc1(String orgnOganDesc1) { 
		this.orgnOganDesc1 = orgnOganDesc1; 
	} 
	public String getOrgnOganDesc1() { 
		return this.orgnOganDesc1; 
	} 
	public void setOrgnOganCode2(String orgnOganCode2) { 
		this.orgnOganCode2 = orgnOganCode2; 
	} 
	public String getOrgnOganCode2() { 
		return this.orgnOganCode2; 
	} 
	public void setOrgnOganDesc2(String orgnOganDesc2) { 
		this.orgnOganDesc2 = orgnOganDesc2; 
	} 
	public String getOrgnOganDesc2() { 
		return this.orgnOganDesc2; 
	} 
	public void setOrgnOganCode3(String orgnOganCode3) { 
		this.orgnOganCode3 = orgnOganCode3; 
	} 
	public String getOrgnOganCode3() { 
		return this.orgnOganCode3; 
	} 
	public void setOrgnOganDesc3(String orgnOganDesc3) { 
		this.orgnOganDesc3 = orgnOganDesc3; 
	} 
	public String getOrgnOganDesc3() { 
		return this.orgnOganDesc3; 
	} 
	public void setOrgnOganCode4(String orgnOganCode4) { 
		this.orgnOganCode4 = orgnOganCode4; 
	} 
	public String getOrgnOganCode4() { 
		return this.orgnOganCode4; 
	} 
	public void setOrgnOganDesc4(String orgnOganDesc4) { 
		this.orgnOganDesc4 = orgnOganDesc4; 
	} 
	public String getOrgnOganDesc4() { 
		return this.orgnOganDesc4; 
	} 
	public void setOrgnOganCode5(String orgnOganCode5) { 
		this.orgnOganCode5 = orgnOganCode5; 
	} 
	public String getOrgnOganCode5() { 
		return this.orgnOganCode5; 
	} 
	public void setOrgnOganDesc5(String orgnOganDesc5) { 
		this.orgnOganDesc5 = orgnOganDesc5; 
	} 
	public String getOrgnOganDesc5() { 
		return this.orgnOganDesc5; 
	} 
	public void setOrgnOganCode6(String orgnOganCode6) { 
		this.orgnOganCode6 = orgnOganCode6; 
	} 
	public String getOrgnOganCode6() { 
		return this.orgnOganCode6; 
	} 
	public void setOrgnOganDesc6(String orgnOganDesc6) { 
		this.orgnOganDesc6 = orgnOganDesc6; 
	} 
	public String getOrgnOganDesc6() { 
		return this.orgnOganDesc6; 
	} 
	public void setOrgnOganCode7(String orgnOganCode7) { 
		this.orgnOganCode7 = orgnOganCode7; 
	} 
	public String getOrgnOganCode7() { 
		return this.orgnOganCode7; 
	} 
	public void setOrgnOganDesc7(String orgnOganDesc7) { 
		this.orgnOganDesc7 = orgnOganDesc7; 
	} 
	public String getOrgnOganDesc7() { 
		return this.orgnOganDesc7; 
	} 
	public void setOrgnOganCode8(String orgnOganCode8) { 
		this.orgnOganCode8 = orgnOganCode8; 
	} 
	public String getOrgnOganCode8() { 
		return this.orgnOganCode8; 
	} 
	public void setOrgnOganDesc8(String orgnOganDesc8) { 
		this.orgnOganDesc8 = orgnOganDesc8; 
	} 
	public String getOrgnOganDesc8() { 
		return this.orgnOganDesc8; 
	} 
	public void setOrgnOganCode9(String orgnOganCode9) { 
		this.orgnOganCode9 = orgnOganCode9; 
	} 
	public String getOrgnOganCode9() { 
		return this.orgnOganCode9; 
	} 
	public void setOrgnOganDesc9(String orgnOganDesc9) { 
		this.orgnOganDesc9 = orgnOganDesc9; 
	} 
	public String getOrgnOganDesc9() { 
		return this.orgnOganDesc9; 
	} 
	public void setOrgnOganCode10(String orgnOganCode10) { 
		this.orgnOganCode10 = orgnOganCode10; 
	} 
	public String getOrgnOganCode10() { 
		return this.orgnOganCode10; 
	} 
	public void setOrgnOganDesc10(String orgnOganDesc10) { 
		this.orgnOganDesc10 = orgnOganDesc10; 
	} 
	public String getOrgnOganDesc10() { 
		return this.orgnOganDesc10; 
	} 
	public void setOrgnOganCode11(String orgnOganCode11) { 
		this.orgnOganCode11 = orgnOganCode11; 
	} 
	public String getOrgnOganCode11() { 
		return this.orgnOganCode11; 
	} 
	public void setOrgnOganDesc11(String orgnOganDesc11) { 
		this.orgnOganDesc11 = orgnOganDesc11; 
	} 
	public String getOrgnOganDesc11() { 
		return this.orgnOganDesc11; 
	} 
	public void setOrgnOganCode12(String orgnOganCode12) { 
		this.orgnOganCode12 = orgnOganCode12; 
	} 
	public String getOrgnOganCode12() { 
		return this.orgnOganCode12; 
	} 
	public void setOrgnOganDesc12(String orgnOganDesc12) { 
		this.orgnOganDesc12 = orgnOganDesc12; 
	} 
	public String getOrgnOganDesc12() { 
		return this.orgnOganDesc12; 
	} 
	public void setBaseAsgnOganLev(String baseAsgnOganLev) { 
		this.baseAsgnOganLev = baseAsgnOganLev; 
	} 
	public String getBaseAsgnOganLev() { 
		return this.baseAsgnOganLev; 
	} 
	public void setBaseOrgnOganLev(String baseOrgnOganLev) { 
		this.baseOrgnOganLev = baseOrgnOganLev; 
	} 
	public String getBaseOrgnOganLev() { 
		return this.baseOrgnOganLev; 
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