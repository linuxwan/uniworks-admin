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
 * 관련 테이블 : Nw020m
 */ 
@XmlRootElement(name = "nw020m") 
@XmlType(propOrder = {"menuId", "coId", "highMenuId", "menuDsplName", "menuLevel", "menuOrd", "topUrl", "leftUrl", "bodyUrl", "cntnId", "menuDsplIndc", "cntnLinkIndc", "linkType", "menuDesc", "dfltMenuIndc", "dlgtMenuIndc", "myMenuSetIndc", "iconFileUrl", "crtDate", "crtId", "chngDate", "chngId"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Nw020m implements Serializable {  
	private String menuId; 
	private String coId; 
	private String highMenuId; 
	private String menuDsplName; 
	private long menuLevel; 
	private long menuOrd; 
	private String topUrl; 
	private String leftUrl; 
	private String bodyUrl; 
	private String cntnId; 
	private String menuDsplIndc; 
	private String cntnLinkIndc; 
	private String linkType; 
	private String menuDesc; 
	private String dfltMenuIndc; 
	private String dlgtMenuIndc; 
	private String myMenuSetIndc; 
	private String iconFileUrl; 
	private Date crtDate; 
	private String crtId; 
	private Date chngDate; 
	private String chngId; 

	public void setMenuId(String menuId) { 
		this.menuId = menuId; 
	} 
	public String getMenuId() { 
		return this.menuId; 
	} 
	public void setCoId(String coId) { 
		this.coId = coId; 
	} 
	public String getCoId() { 
		return this.coId; 
	} 
	public void setHighMenuId(String highMenuId) { 
		this.highMenuId = highMenuId; 
	} 
	public String getHighMenuId() { 
		return this.highMenuId; 
	} 
	public void setMenuDsplName(String menuDsplName) { 
		this.menuDsplName = menuDsplName; 
	} 
	public String getMenuDsplName() { 
		return this.menuDsplName; 
	} 
	public void setMenuLevel(long menuLevel) { 
		this.menuLevel = menuLevel; 
	} 
	public long getMenuLevel() { 
		return this.menuLevel; 
	} 
	public void setMenuOrd(long menuOrd) { 
		this.menuOrd = menuOrd; 
	} 
	public long getMenuOrd() { 
		return this.menuOrd; 
	} 
	public void setTopUrl(String topUrl) { 
		this.topUrl = topUrl; 
	} 
	public String getTopUrl() { 
		return this.topUrl; 
	} 
	public void setLeftUrl(String leftUrl) { 
		this.leftUrl = leftUrl; 
	} 
	public String getLeftUrl() { 
		return this.leftUrl; 
	} 
	public void setBodyUrl(String bodyUrl) { 
		this.bodyUrl = bodyUrl; 
	} 
	public String getBodyUrl() { 
		return this.bodyUrl; 
	} 
	public void setCntnId(String cntnId) { 
		this.cntnId = cntnId; 
	} 
	public String getCntnId() { 
		return this.cntnId; 
	} 
	public void setMenuDsplIndc(String menuDsplIndc) { 
		this.menuDsplIndc = menuDsplIndc; 
	} 
	public String getMenuDsplIndc() { 
		return this.menuDsplIndc; 
	} 
	public void setCntnLinkIndc(String cntnLinkIndc) { 
		this.cntnLinkIndc = cntnLinkIndc; 
	} 
	public String getCntnLinkIndc() { 
		return this.cntnLinkIndc; 
	} 
	public void setLinkType(String linkType) { 
		this.linkType = linkType; 
	} 
	public String getLinkType() { 
		return this.linkType; 
	} 
	public void setMenuDesc(String menuDesc) { 
		this.menuDesc = menuDesc; 
	} 
	public String getMenuDesc() { 
		return this.menuDesc; 
	} 
	public void setDfltMenuIndc(String dfltMenuIndc) { 
		this.dfltMenuIndc = dfltMenuIndc; 
	} 
	public String getDfltMenuIndc() { 
		return this.dfltMenuIndc; 
	} 
	public void setDlgtMenuIndc(String dlgtMenuIndc) { 
		this.dlgtMenuIndc = dlgtMenuIndc; 
	} 
	public String getDlgtMenuIndc() { 
		return this.dlgtMenuIndc; 
	} 
	public void setMyMenuSetIndc(String myMenuSetIndc) { 
		this.myMenuSetIndc = myMenuSetIndc; 
	} 
	public String getMyMenuSetIndc() { 
		return this.myMenuSetIndc; 
	} 
	public void setIconFileUrl(String iconFileUrl) { 
		this.iconFileUrl = iconFileUrl; 
	} 
	public String getIconFileUrl() { 
		return this.iconFileUrl; 
	} 
	public void setCrtDate(Date crtDate) { 
		this.crtDate = crtDate; 
	} 
	public Date getCrtDate() { 
		return this.crtDate; 
	} 
	public void setCrtId(String crtId) { 
		this.crtId = crtId; 
	} 
	public String getCrtId() { 
		return this.crtId; 
	} 
	public void setChngDate(Date chngDate) { 
		this.chngDate = chngDate; 
	} 
	public Date getChngDate() { 
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