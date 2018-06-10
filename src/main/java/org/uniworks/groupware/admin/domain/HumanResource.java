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
 *
 */
@XmlRootElement(name = "menu")
@XmlType(propOrder = {"coId","empNo","sysUserId","empNameKor","empNameChn","empNameEng","workIndc","dutyCode","dutyDesc","pstnCode","pstnDesc","asgnOganCode1","asgnOganDesc1","asgnOganCode2",
		"asgnOganDesc2","asgnOganCode3","asgnOganDesc3","asgnOganCode4","asgnOganDesc4","asgnOganCode5","asgnOganDesc5","asgnOganCode6","asgnOganDesc6","asgnOganCode7","asgnOganDesc7",
		"asgnOganCode8","asgnOganDesc8","asgnOganCode9","asgnOganDesc9","asgnOganCode10","asgnOganDesc10","asgnOganCode11","asgnOganDesc11","asgnOganCode12","asgnOganDesc12",
		"offcTelNo","moblPhonNo","birthDate","ssn","mailAddr","entrDate","rsgnDate","offcOrdDate","marriageIndc","marriageDate","orgnOganCode1","orgnOganDesc1","orgnOganCode2",
		"orgnOganDesc2","orgnOganCode3","orgnOganDesc3","orgnOganCode4","orgnOganDesc4","orgnOganCode5","orgnOganDesc5","orgnOganCode6","orgnOganDesc6","orgnOganCode7","orgnOganDesc7",
		"orgnOganCode8","orgnOganDesc8","orgnOganCode9","orgnOganDesc9","orgnOganCode10","orgnOganDesc10","orgnOganCode11","orgnOganDesc11","orgnOganCode12","orgnOganDesc12",
		"baseAsgnOganLev","baseOrgnOganLev","deptCode","deptDesc", "orgnDeptCode", "orgnDeptDesc"})
@AutoProperty 
@SuppressWarnings("serial")
public class HumanResource implements Serializable {
	private String coId;			//회사 구분
	private String empNo;			//사번
	private String sysUserId;		//시스템 사용자 ID
	private String empNameKor;		//성명(한글)
	private String empNameChn;		//성명(한자)
	private String empNameEng;		//성명(영문)
	private String workIndc;		//근무 구분
	private String dutyCode;		//직위 코드
	private String dutyDesc;		//직위 명칭
	private String pstnCode;		//보직 코드
	private String pstnDesc;		//보직 명칭
	private String asgnOganCode1;	//소속 조직 코드 1
	private String asgnOganDesc1;	//소속 조직 명칭 1
	private String asgnOganCode2;	//소속 조직 코드 2
	private String asgnOganDesc2;	//소속 조직 명칭 2
	private String asgnOganCode3;	//소속 조직 코드 3
	private String asgnOganDesc3;	//소속 조직 명칭 3
	private String asgnOganCode4;	//소속 조직 코드 4
	private String asgnOganDesc4;	//소속 조직 명칭 4
	private String asgnOganCode5;	//소속 조직 코드 5
	private String asgnOganDesc5;	//소속 조직 명칭 5
	private String asgnOganCode6;	//소속 조직 코드 6
	private String asgnOganDesc6;	//소속 조직 명칭 6
	private String asgnOganCode7;	//소속 조직 코드 7
	private String asgnOganDesc7;	//소속 조직 명칭 7
	private String asgnOganCode8;	//소속 조직 코드 8
	private String asgnOganDesc8;	//소속 조직 명칭 8
	private String asgnOganCode9;	//소속 조직 코드 9
	private String asgnOganDesc9;	//소속 조직 명칭 9
	private String asgnOganCode10;	//소속 조직 코드 10
	private String asgnOganDesc10;	//소속 조직 명칭 10
	private String asgnOganCode11;	//소속 조직 코드 11
	private String asgnOganDesc11;	//소속 조직 명칭 11
	private String asgnOganCode12;	//소속 조직 코드 12
	private String asgnOganDesc12;	//소속 조직 명칭 12
	private String offcTelNo;		//사무실 전화번호
	private String moblPhonNo;		//모바일 번호
	private String birthDate;		//생년 월일
	private String ssn;				//주민 등록 번호
	private String mailAddr;		//메일 주소
	private String entrDate;		//입사 일자
	private String rsgnDate;		//퇴사 일자
	private String offcOrdDate;		//현직위 발령 일자
	private String marriageIndc;	//결혼 여부
	private String marriageDate;	//결혼 일자
	private String orgnOganCode1;	//원소속 조직 코드 1
	private String orgnOganDesc1;	//원소속 조직 명칭 1
	private String orgnOganCode2;	//원소속 조직 코드 2
	private String orgnOganDesc2;	//원소속 조직 명칭 2
	private String orgnOganCode3;	//원소속 조직 코드 3
	private String orgnOganDesc3;	//원소속 조직 명칭 3
	private String orgnOganCode4;	//원소속 조직 코드 4
	private String orgnOganDesc4;	//원소속 조직 명칭 4
	private String orgnOganCode5;	//원소속 조직 코드 5
	private String orgnOganDesc5;	//원소속 조직 명칭 5
	private String orgnOganCode6;	//원소속 조직 코드 6
	private String orgnOganDesc6;	//원소속 조직 명칭 6
	private String orgnOganCode7;	//원소속 조직 코드 7
	private String orgnOganDesc7;	//원소속 조직 명칭 7
	private String orgnOganCode8;	//원소속 조직 코드 8
	private String orgnOganDesc8;	//원소속 조직 명칭 8
	private String orgnOganCode9;	//원소속 조직 코드 9
	private String orgnOganDesc9;	//원소속 조직 명칭 9
	private String orgnOganCode10;	//원소속 조직 코드 10
	private String orgnOganDesc10;	//원소속 조직 명칭 10
	private String orgnOganCode11;	//원소속 조직 코드 11
	private String orgnOganDesc11;	//원소속 조직 명칭 11
	private String orgnOganCode12;	//원소속 조직 코드 12
	private String orgnOganDesc12;	//원소속 조직 명칭 12
	private String baseAsgnOganLev;	//소속 조직 기본 레벨
	private String baseOrgnOganLev;	//원소속 조직 기본 레벨
	private String deptCode;	//기준이 되는 조직코드
	private String deptDesc;	//기준이 되는 조직명칭
	private String orgnDeptCode;	//기준이 되는 원소속 조직코드
	private String orgnDeptDesc;	//기준이 되는 원소속 조직명칭
	
	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}
	public String getEmpNameKor() {
		return empNameKor;
	}
	public void setEmpNameKor(String empNameKor) {
		this.empNameKor = empNameKor;
	}
	public String getEmpNameChn() {
		return empNameChn;
	}
	public void setEmpNameChn(String empNameChn) {
		this.empNameChn = empNameChn;
	}
	public String getEmpNameEng() {
		return empNameEng;
	}
	public void setEmpNameEng(String empNameEng) {
		this.empNameEng = empNameEng;
	}
	public String getWorkIndc() {
		return workIndc;
	}
	public void setWorkIndc(String workIndc) {
		this.workIndc = workIndc;
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
	public String getAsgnOganCode1() {
		return asgnOganCode1;
	}
	public void setAsgnOganCode1(String asgnOganCode1) {
		this.asgnOganCode1 = asgnOganCode1;
	}
	public String getAsgnOganDesc1() {
		return asgnOganDesc1;
	}
	public void setAsgnOganDesc1(String asgnOganDesc1) {
		this.asgnOganDesc1 = asgnOganDesc1;
	}
	public String getAsgnOganCode2() {
		return asgnOganCode2;
	}
	public void setAsgnOganCode2(String asgnOganCode2) {
		this.asgnOganCode2 = asgnOganCode2;
	}
	public String getAsgnOganDesc2() {
		return asgnOganDesc2;
	}
	public void setAsgnOganDesc2(String asgnOganDesc2) {
		this.asgnOganDesc2 = asgnOganDesc2;
	}
	public String getAsgnOganCode3() {
		return asgnOganCode3;
	}
	public void setAsgnOganCode3(String asgnOganCode3) {
		this.asgnOganCode3 = asgnOganCode3;
	}
	public String getAsgnOganDesc3() {
		return asgnOganDesc3;
	}
	public void setAsgnOganDesc3(String asgnOganDesc3) {
		this.asgnOganDesc3 = asgnOganDesc3;
	}
	public String getAsgnOganCode4() {
		return asgnOganCode4;
	}
	public void setAsgnOganCode4(String asgnOganCode4) {
		this.asgnOganCode4 = asgnOganCode4;
	}
	public String getAsgnOganDesc4() {
		return asgnOganDesc4;
	}
	public void setAsgnOganDesc4(String asgnOganDesc4) {
		this.asgnOganDesc4 = asgnOganDesc4;
	}
	public String getAsgnOganCode5() {
		return asgnOganCode5;
	}
	public void setAsgnOganCode5(String asgnOganCode5) {
		this.asgnOganCode5 = asgnOganCode5;
	}
	public String getAsgnOganDesc5() {
		return asgnOganDesc5;
	}
	public void setAsgnOganDesc5(String asgnOganDesc5) {
		this.asgnOganDesc5 = asgnOganDesc5;
	}
	public String getAsgnOganCode6() {
		return asgnOganCode6;
	}
	public void setAsgnOganCode6(String asgnOganCode6) {
		this.asgnOganCode6 = asgnOganCode6;
	}
	public String getAsgnOganDesc6() {
		return asgnOganDesc6;
	}
	public void setAsgnOganDesc6(String asgnOganDesc6) {
		this.asgnOganDesc6 = asgnOganDesc6;
	}
	public String getAsgnOganCode7() {
		return asgnOganCode7;
	}
	public void setAsgnOganCode7(String asgnOganCode7) {
		this.asgnOganCode7 = asgnOganCode7;
	}
	public String getAsgnOganDesc7() {
		return asgnOganDesc7;
	}
	public void setAsgnOganDesc7(String asgnOganDesc7) {
		this.asgnOganDesc7 = asgnOganDesc7;
	}
	public String getAsgnOganCode8() {
		return asgnOganCode8;
	}
	public void setAsgnOganCode8(String asgnOganCode8) {
		this.asgnOganCode8 = asgnOganCode8;
	}
	public String getAsgnOganDesc8() {
		return asgnOganDesc8;
	}
	public void setAsgnOganDesc8(String asgnOganDesc8) {
		this.asgnOganDesc8 = asgnOganDesc8;
	}
	public String getAsgnOganCode9() {
		return asgnOganCode9;
	}
	public void setAsgnOganCode9(String asgnOganCode9) {
		this.asgnOganCode9 = asgnOganCode9;
	}
	public String getAsgnOganDesc9() {
		return asgnOganDesc9;
	}
	public void setAsgnOganDesc9(String asgnOganDesc9) {
		this.asgnOganDesc9 = asgnOganDesc9;
	}
	public String getAsgnOganCode10() {
		return asgnOganCode10;
	}
	public void setAsgnOganCode10(String asgnOganCode10) {
		this.asgnOganCode10 = asgnOganCode10;
	}
	public String getAsgnOganDesc10() {
		return asgnOganDesc10;
	}
	public void setAsgnOganDesc10(String asgnOganDesc10) {
		this.asgnOganDesc10 = asgnOganDesc10;
	}
	public String getAsgnOganCode11() {
		return asgnOganCode11;
	}
	public void setAsgnOganCode11(String asgnOganCode11) {
		this.asgnOganCode11 = asgnOganCode11;
	}
	public String getAsgnOganDesc11() {
		return asgnOganDesc11;
	}
	public void setAsgnOganDesc11(String asgnOganDesc11) {
		this.asgnOganDesc11 = asgnOganDesc11;
	}
	public String getAsgnOganCode12() {
		return asgnOganCode12;
	}
	public void setAsgnOganCode12(String asgnOganCode12) {
		this.asgnOganCode12 = asgnOganCode12;
	}
	public String getAsgnOganDesc12() {
		return asgnOganDesc12;
	}
	public void setAsgnOganDesc12(String asgnOganDesc12) {
		this.asgnOganDesc12 = asgnOganDesc12;
	}
	public String getOffcTelNo() {
		return offcTelNo;
	}
	public void setOffcTelNo(String offcTelNo) {
		this.offcTelNo = offcTelNo;
	}
	public String getMoblPhonNo() {
		return moblPhonNo;
	}
	public void setMoblPhonNo(String moblPhonNo) {
		this.moblPhonNo = moblPhonNo;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getMailAddr() {
		return mailAddr;
	}
	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}
	public String getEntrDate() {
		return entrDate;
	}
	public void setEntrDate(String entrDate) {
		this.entrDate = entrDate;
	}
	public String getRsgnDate() {
		return rsgnDate;
	}
	public void setRsgnDate(String rsgnDate) {
		this.rsgnDate = rsgnDate;
	}
	public String getOffcOrdDate() {
		return offcOrdDate;
	}
	public void setOffcOrdDate(String offcOrdDate) {
		this.offcOrdDate = offcOrdDate;
	}
	public String getMarriageIndc() {
		return marriageIndc;
	}
	public void setMarriageIndc(String marriageIndc) {
		this.marriageIndc = marriageIndc;
	}
	public String getMarriageDate() {
		return marriageDate;
	}
	public void setMarriageDate(String marriageDate) {
		this.marriageDate = marriageDate;
	}
	public String getOrgnOganCode1() {
		return orgnOganCode1;
	}
	public void setOrgnOganCode1(String orgnOganCode1) {
		this.orgnOganCode1 = orgnOganCode1;
	}
	public String getOrgnOganDesc1() {
		return orgnOganDesc1;
	}
	public void setOrgnOganDesc1(String orgnOganDesc1) {
		this.orgnOganDesc1 = orgnOganDesc1;
	}
	public String getOrgnOganCode2() {
		return orgnOganCode2;
	}
	public void setOrgnOganCode2(String orgnOganCode2) {
		this.orgnOganCode2 = orgnOganCode2;
	}
	public String getOrgnOganDesc2() {
		return orgnOganDesc2;
	}
	public void setOrgnOganDesc2(String orgnOganDesc2) {
		this.orgnOganDesc2 = orgnOganDesc2;
	}
	public String getOrgnOganCode3() {
		return orgnOganCode3;
	}
	public void setOrgnOganCode3(String orgnOganCode3) {
		this.orgnOganCode3 = orgnOganCode3;
	}
	public String getOrgnOganDesc3() {
		return orgnOganDesc3;
	}
	public void setOrgnOganDesc3(String orgnOganDesc3) {
		this.orgnOganDesc3 = orgnOganDesc3;
	}
	public String getOrgnOganCode4() {
		return orgnOganCode4;
	}
	public void setOrgnOganCode4(String orgnOganCode4) {
		this.orgnOganCode4 = orgnOganCode4;
	}
	public String getOrgnOganDesc4() {
		return orgnOganDesc4;
	}
	public void setOrgnOganDesc4(String orgnOganDesc4) {
		this.orgnOganDesc4 = orgnOganDesc4;
	}
	public String getOrgnOganCode5() {
		return orgnOganCode5;
	}
	public void setOrgnOganCode5(String orgnOganCode5) {
		this.orgnOganCode5 = orgnOganCode5;
	}
	public String getOrgnOganDesc5() {
		return orgnOganDesc5;
	}
	public void setOrgnOganDesc5(String orgnOganDesc5) {
		this.orgnOganDesc5 = orgnOganDesc5;
	}
	public String getOrgnOganCode6() {
		return orgnOganCode6;
	}
	public void setOrgnOganCode6(String orgnOganCode6) {
		this.orgnOganCode6 = orgnOganCode6;
	}
	public String getOrgnOganDesc6() {
		return orgnOganDesc6;
	}
	public void setOrgnOganDesc6(String orgnOganDesc6) {
		this.orgnOganDesc6 = orgnOganDesc6;
	}
	public String getOrgnOganCode7() {
		return orgnOganCode7;
	}
	public void setOrgnOganCode7(String orgnOganCode7) {
		this.orgnOganCode7 = orgnOganCode7;
	}
	public String getOrgnOganDesc7() {
		return orgnOganDesc7;
	}
	public void setOrgnOganDesc7(String orgnOganDesc7) {
		this.orgnOganDesc7 = orgnOganDesc7;
	}
	public String getOrgnOganCode8() {
		return orgnOganCode8;
	}
	public void setOrgnOganCode8(String orgnOganCode8) {
		this.orgnOganCode8 = orgnOganCode8;
	}
	public String getOrgnOganDesc8() {
		return orgnOganDesc8;
	}
	public void setOrgnOganDesc8(String orgnOganDesc8) {
		this.orgnOganDesc8 = orgnOganDesc8;
	}
	public String getOrgnOganCode9() {
		return orgnOganCode9;
	}
	public void setOrgnOganCode9(String orgnOganCode9) {
		this.orgnOganCode9 = orgnOganCode9;
	}
	public String getOrgnOganDesc9() {
		return orgnOganDesc9;
	}
	public void setOrgnOganDesc9(String orgnOganDesc9) {
		this.orgnOganDesc9 = orgnOganDesc9;
	}
	public String getOrgnOganCode10() {
		return orgnOganCode10;
	}
	public void setOrgnOganCode10(String orgnOganCode10) {
		this.orgnOganCode10 = orgnOganCode10;
	}
	public String getOrgnOganDesc10() {
		return orgnOganDesc10;
	}
	public void setOrgnOganDesc10(String orgnOganDesc10) {
		this.orgnOganDesc10 = orgnOganDesc10;
	}
	public String getOrgnOganCode11() {
		return orgnOganCode11;
	}
	public void setOrgnOganCode11(String orgnOganCode11) {
		this.orgnOganCode11 = orgnOganCode11;
	}
	public String getOrgnOganDesc11() {
		return orgnOganDesc11;
	}
	public void setOrgnOganDesc11(String orgnOganDesc11) {
		this.orgnOganDesc11 = orgnOganDesc11;
	}
	public String getOrgnOganCode12() {
		return orgnOganCode12;
	}
	public void setOrgnOganCode12(String orgnOganCode12) {
		this.orgnOganCode12 = orgnOganCode12;
	}
	public String getOrgnOganDesc12() {
		return orgnOganDesc12;
	}
	public void setOrgnOganDesc12(String orgnOganDesc12) {
		this.orgnOganDesc12 = orgnOganDesc12;
	}
	public String getBaseAsgnOganLev() {
		return baseAsgnOganLev;
	}
	public void setBaseAsgnOganLev(String baseAsgnOganLev) {
		this.baseAsgnOganLev = baseAsgnOganLev;
	}
	public String getBaseOrgnOganLev() {
		return baseOrgnOganLev;
	}
	public void setBaseOrgnOganLev(String baseOrgnOganLev) {
		this.baseOrgnOganLev = baseOrgnOganLev;
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
	public String getOrgnDeptCode() {
		return orgnDeptCode;
	}
	public void setOrgnDeptCode(String orgnDeptCode) {
		this.orgnDeptCode = orgnDeptCode;
	}
	public String getOrgnDeptDesc() {
		return orgnDeptDesc;
	}
	public void setOrgnDeptDesc(String orgnDeptDesc) {
		this.orgnDeptDesc = orgnDeptDesc;
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
