/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.pojomatic.annotations.AutoProperty;

/**
 * @author Park Chungwan
 *
 */
@XmlRootElement(name = "oganTree")
@XmlType(propOrder = {"id","text","oganLev", "parentId", "parentLev"})
@AutoProperty 
@SuppressWarnings("serial")
public class OganTree implements Serializable {
	private String id;
	private String text;
	private String oganLev;
	private String parentId;
	private String parentLev;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getOganLev() {
		return oganLev;
	}
	public void setOganLev(String oganLev) {
		this.oganLev = oganLev;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parendId) {
		this.parentId = parendId;
	}
	public String getParentLev() {
		return parentLev;
	}
	public void setParentLev(String parendLev) {
		this.parentLev = parendLev;
	}
}
