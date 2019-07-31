/** 
 * 박충완(Park Chungwan)이 작성한 코드 입니다. 
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다. 
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.	
 */ 
package org.uniworks.groupware.admin.domain; 

import java.io.Serializable; 
import java.util.Date; 

import javax.xml.bind.annotation.XmlRootElement; 
import javax.xml.bind.annotation.XmlType; 

import org.pojomatic.Pojomatic; 
import org.pojomatic.annotations.AutoProperty; 
/** 
 * @author Park Chungwan 
 * 관련 테이블 : Nw030m
 */ 
@XmlRootElement(name = "contentInfo") 
@XmlType(propOrder = {"coId", "cntnId", "cntnName", "cntnOfferType", "cntnOfferTypeDesc", "linkSysHost", "url", "cntnType", "cntnTypeDesc", "masterId", "cntnJointFlag", "cntnDesc", "cntnRgsrDate", "cntnRgsrId", "cntnChngDate", "cntnChngId", "portNo"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class ContentInfo implements Serializable {
	private String coId;
	private String cntnId; 
	private String cntnName;
	private String cntnOfferType;
	private String cntnOfferTypeDesc;
	private String linkSysHost; 
	private String url; 
	private String cntnType; 
	private String cntnTypeDesc;
	private String masterId; 
	private String cntnJointFlag; 
	private String cntnDesc; 
	private Date cntnRgsrDate; 
	private String cntnRgsrId; 
	private Date cntnChngDate; 
	private String cntnChngId; 
	private long portNo; 

	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	public void setCntnId(String cntnId) { 
		this.cntnId = cntnId; 
	} 
	public String getCntnId() { 
		return this.cntnId; 
	} 
	public String getCntnName() {
		return cntnName;
	}
	public void setCntnName(String cntnName) {
		this.cntnName = cntnName;
	}
	public void setCntnOfferType(String cntnOfferType) { 
		this.cntnOfferType = cntnOfferType; 
	} 
	public String getCntnOfferType() { 
		return this.cntnOfferType; 
	} 
	public String getCntnOfferTypeDesc() {
		return cntnOfferTypeDesc;
	}
	public void setCntnOfferTypeDesc(String cntnOfferTypeDesc) {
		this.cntnOfferTypeDesc = cntnOfferTypeDesc;
	}
	public void setLinkSysHost(String linkSysHost) { 
		this.linkSysHost = linkSysHost; 
	} 
	public String getLinkSysHost() { 
		return this.linkSysHost; 
	} 
	public void setUrl(String url) { 
		this.url = url; 
	} 
	public String getUrl() { 
		return this.url; 
	} 
	public void setCntnType(String cntnType) { 
		this.cntnType = cntnType; 
	} 
	public String getCntnType() { 
		return this.cntnType; 
	} 
	public String getCntnTypeDesc() {
		return cntnTypeDesc;
	}
	public void setCntnTypeDesc(String cntnTypeDesc) {
		this.cntnTypeDesc = cntnTypeDesc;
	}
	public void setMasterId(String masterId) { 
		this.masterId = masterId; 
	} 
	public String getMasterId() { 
		return this.masterId; 
	} 
	public void setCntnJointFlag(String cntnJointFlag) { 
		this.cntnJointFlag = cntnJointFlag; 
	} 
	public String getCntnJointFlag() { 
		return this.cntnJointFlag; 
	} 
	public void setCntnDesc(String cntnDesc) { 
		this.cntnDesc = cntnDesc; 
	} 
	public String getCntnDesc() { 
		return this.cntnDesc; 
	} 
	public void setCntnRgsrDate(Date cntnRgsrDate) { 
		this.cntnRgsrDate = cntnRgsrDate; 
	} 
	public Date getCntnRgsrDate() { 
		return this.cntnRgsrDate; 
	} 
	public void setCntnRgsrId(String cntnRgsrId) { 
		this.cntnRgsrId = cntnRgsrId; 
	} 
	public String getCntnRgsrId() { 
		return this.cntnRgsrId; 
	} 
	public void setCntnChngDate(Date cntnChngDate) { 
		this.cntnChngDate = cntnChngDate; 
	} 
	public Date getCntnChngDate() { 
		return this.cntnChngDate; 
	} 
	public void setCntnChngId(String cntnChngId) { 
		this.cntnChngId = cntnChngId; 
	} 
	public String getCntnChngId() { 
		return this.cntnChngId; 
	} 
	public void setPortNo(long portNo) { 
		this.portNo = portNo; 
	} 
	public long getPortNo() { 
		return this.portNo; 
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