/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.common;

/**
 * @author Park Chungwan
 *
 */
public class UserSession {
	private String coId;	//회사구분
	private String adminId;	//관리자 ID
	private String empNo;	//사번
	private String adminType;	//관리자 유형
	private String lang;	//사용언어
	private String pswd;	//로그인 패스워드
	private String locale;	//로케일
	private String lowCaseLocale;	//소문자 로케일 정보
	private String baseOganLev;	//회사별 기준이 되는 조직 레벨
	
	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getAdminType() {
		return adminType;
	}
	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getLowCaseLocale() {
		return lowCaseLocale;
	}
	public void setLowCaseLocale(String lowCaseLocale) {
		this.lowCaseLocale = lowCaseLocale;
	}
	public String getBaseOganLev() {
		return baseOganLev;
	}
	public void setBaseOganLev(String baseOganLev) {
		this.baseOganLev = baseOganLev;
	}
}
