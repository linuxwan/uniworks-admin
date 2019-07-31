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
 * 관련 테이블 : Nw001m
 */ 
@XmlRootElement(name = "boardMasterInfo") 
@XmlType(propOrder = {"coId", "boardId", "boardName", "boardType", "boardTypeName", "rplyIndc", "cmntIndc", "atchIndc", "evalIndc", "validTermIndc", "validTermCode", "anonyIndc", "anceIndc", "apprIndc", "typeCode1", "typeCode2", "typeCode3", "typeCode4", "useIndc", "entrOpenIndc", "crtDate", "crtId", "chngDate", "chngId"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class BoardMasterInfo implements Serializable {  
	private String coId; 
	private String boardId; 
	private String boardName;
	private String boardType; 
	private String boardTypeName;
	private String rplyIndc; 
	private String cmntIndc; 
	private String atchIndc; 
	private String evalIndc; 
	private String validTermIndc; 
	private String validTermCode; 
	private String anonyIndc; 
	private String anceIndc; 
	private String apprIndc; 
	private String typeCode1; 
	private String typeCode2; 
	private String typeCode3; 
	private String typeCode4; 
	private String useIndc; 
	private String entrOpenIndc; 
	private Timestamp crtDate; 
	private String crtId; 
	private Timestamp chngDate; 
	private String chngId; 

	public void setCoId(String coId) { 
		this.coId = coId; 
	} 
	public String getCoId() { 
		return this.coId; 
	} 
	public void setBoardId(String boardId) { 
		this.boardId = boardId; 
	} 
	public String getBoardId() { 
		return this.boardId; 
	} 
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public void setBoardType(String boardType) { 
		this.boardType = boardType; 
	} 
	public String getBoardType() { 
		return this.boardType; 
	} 
	public String getBoardTypeName() {
		return boardTypeName;
	}
	public void setBoardTypeName(String boardTypeName) {
		this.boardTypeName = boardTypeName;
	}
	public void setRplyIndc(String rplyIndc) { 
		this.rplyIndc = rplyIndc; 
	} 
	public String getRplyIndc() { 
		return this.rplyIndc; 
	} 
	public void setCmntIndc(String cmntIndc) { 
		this.cmntIndc = cmntIndc; 
	} 
	public String getCmntIndc() { 
		return this.cmntIndc; 
	} 
	public void setAtchIndc(String atchIndc) { 
		this.atchIndc = atchIndc; 
	} 
	public String getAtchIndc() { 
		return this.atchIndc; 
	} 
	public void setEvalIndc(String evalIndc) { 
		this.evalIndc = evalIndc; 
	} 
	public String getEvalIndc() { 
		return this.evalIndc; 
	} 
	public void setValidTermIndc(String validTermIndc) { 
		this.validTermIndc = validTermIndc; 
	} 
	public String getValidTermIndc() { 
		return this.validTermIndc; 
	} 
	public void setValidTermCode(String validTermCode) { 
		this.validTermCode = validTermCode; 
	} 
	public String getValidTermCode() { 
		return this.validTermCode; 
	} 
	public void setAnonyIndc(String anonyIndc) { 
		this.anonyIndc = anonyIndc; 
	} 
	public String getAnonyIndc() { 
		return this.anonyIndc; 
	} 
	public void setAnceIndc(String anceIndc) { 
		this.anceIndc = anceIndc; 
	} 
	public String getAnceIndc() { 
		return this.anceIndc; 
	} 
	public void setApprIndc(String apprIndc) { 
		this.apprIndc = apprIndc; 
	} 
	public String getApprIndc() { 
		return this.apprIndc; 
	} 
	public void setTypeCode1(String typeCode1) { 
		this.typeCode1 = typeCode1; 
	} 
	public String getTypeCode1() { 
		return this.typeCode1; 
	} 
	public void setTypeCode2(String typeCode2) { 
		this.typeCode2 = typeCode2; 
	} 
	public String getTypeCode2() { 
		return this.typeCode2; 
	} 
	public void setTypeCode3(String typeCode3) { 
		this.typeCode3 = typeCode3; 
	} 
	public String getTypeCode3() { 
		return this.typeCode3; 
	} 
	public void setTypeCode4(String typeCode4) { 
		this.typeCode4 = typeCode4; 
	} 
	public String getTypeCode4() { 
		return this.typeCode4; 
	} 
	public void setUseIndc(String useIndc) { 
		this.useIndc = useIndc; 
	} 
	public String getUseIndc() { 
		return this.useIndc; 
	} 
	public void setEntrOpenIndc(String entrOpenIndc) { 
		this.entrOpenIndc = entrOpenIndc; 
	} 
	public String getEntrOpenIndc() { 
		return this.entrOpenIndc; 
	} 
	public void setCrtDate(Timestamp crtDate) { 
		this.crtDate = crtDate; 
	} 
	public Timestamp getCrtDate() { 
		return this.crtDate; 
	} 
	public void setCrtId(String crtId) { 
		this.crtId = crtId; 
	} 
	public String getCrtId() { 
		return this.crtId; 
	} 
	public void setChngDate(Timestamp chngDate) { 
		this.chngDate = chngDate; 
	} 
	public Timestamp getChngDate() { 
		return this.chngDate; 
	} 
	public void setChngId(String chngId) { 
		this.chngId = chngId; 
	} 
	public String getChngId() { 
		return this.chngId; 
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