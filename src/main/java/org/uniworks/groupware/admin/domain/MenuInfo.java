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
 * @author Park Chung Wan
 *
 */
@XmlRootElement(name = "MenuInfo") 
@XmlType(propOrder = {"coId", "menuId", "highMenuId", "menuDsplName", "menuLevel", "menuOrd", "topUrl", "leftUrl", "bodyUrl", "cntnId", "menuDsplIndc", "cntnLinkIndc", "linkType", "linkTypeName", "menuDesc", "dfltMenuIndc", "dlgtMenuIndc", "myMenuSetIndc", "iconFileUrl", "crtDate", "crtId", "chngDate", "chngId"}) 
@AutoProperty 
@SuppressWarnings("serial")
public class MenuInfo implements Serializable {
	private String coId;
	private String menuId; 
	private String highMenuId;
	private String menuDsplName;
	private int menuLevel;
	private int menuOrd;
	private String topUrl;
	private String leftUrl;
	private String bodyUrl;
	private String cntnId;
	private String menuDsplIndc;
	private String cntnLinkIndc;
	private String linkType;
	private String linkTypeName;
	private String menuDesc;
	private String dfltMenuIndc;
	private String dlgtMenuIndc;
	private String myMenuSetIndc;
	private String iconFileUrl;
	private Date crtDate;
	private String crtId;
    private Date chngDate;
    private String chngId;
    
	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getHighMenuId() {
		return highMenuId;
	}
	public void setHighMenuId(String highMenuId) {
		this.highMenuId = highMenuId;
	}
	public String getMenuDsplName() {
		return menuDsplName;
	}
	public void setMenuDsplName(String menuDsplName) {
		this.menuDsplName = menuDsplName;
	}
	public int getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}
	public int getMenuOrd() {
		return menuOrd;
	}
	public void setMenuOrd(int menuOrd) {
		this.menuOrd = menuOrd;
	}
	public String getTopUrl() {
		return topUrl;
	}
	public void setTopUrl(String topUrl) {
		this.topUrl = topUrl;
	}
	public String getLeftUrl() {
		return leftUrl;
	}
	public void setLeftUrl(String leftUrl) {
		this.leftUrl = leftUrl;
	}
	public String getBodyUrl() {
		return bodyUrl;
	}
	public void setBodyUrl(String bodyUrl) {
		this.bodyUrl = bodyUrl;
	}
	public String getCntnId() {
		return cntnId;
	}
	public void setCntnId(String cntnId) {
		this.cntnId = cntnId;
	}
	public String getMenuDsplIndc() {
		return menuDsplIndc;
	}
	public void setMenuDsplIndc(String menuDsplIndc) {
		this.menuDsplIndc = menuDsplIndc;
	}
	public String getCntnLinkIndc() {
		return cntnLinkIndc;
	}
	public void setCntnLinkIndc(String cntnLinkIndc) {
		this.cntnLinkIndc = cntnLinkIndc;
	}
	public String getLinkType() {
		return linkType;
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	public String getLinkTypeName() {
		return linkTypeName;
	}
	public void setLinkTypeName(String linkTypeName) {
		this.linkTypeName = linkTypeName;
	}
	public String getMenuDesc() {
		return menuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
	public String getDfltMenuIndc() {
		return dfltMenuIndc;
	}
	public void setDfltMenuIndc(String dfltMenuIndc) {
		this.dfltMenuIndc = dfltMenuIndc;
	}
	public String getDlgtMenuIndc() {
		return dlgtMenuIndc;
	}
	public void setDlgtMenuIndc(String dlgtMenuIndc) {
		this.dlgtMenuIndc = dlgtMenuIndc;
	}
	public String getMyMenuSetIndc() {
		return myMenuSetIndc;
	}
	public void setMyMenuSetIndc(String myMenuSetIndc) {
		this.myMenuSetIndc = myMenuSetIndc;
	}
	public String getIconFileUrl() {
		return iconFileUrl;
	}
	public void setIconFileUrl(String iconFileUrl) {
		this.iconFileUrl = iconFileUrl;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getCrtId() {
		return crtId;
	}
	public void setCrtId(String crtId) {
		this.crtId = crtId;
	}
	public Date getChngDate() {
		return chngDate;
	}
	public void setChngDate(Date chngDate) {
		this.chngDate = chngDate;
	}
	public String getChngId() {
		return chngId;
	}
	public void setChngId(String chngId) {
		this.chngId = chngId;
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
