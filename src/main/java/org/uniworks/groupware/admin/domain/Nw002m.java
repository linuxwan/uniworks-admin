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
 * 관련 테이블 : Nw002m
 */ 
@XmlRootElement(name = "nw002m") 
@XmlType(propOrder = {"coId", "boardId", "locale", "boardName"}) 
@AutoProperty 
@SuppressWarnings("serial") 
public class Nw002m implements Serializable {  
	private String coId; 
	private String boardId; 
	private String locale; 
	private String boardName; 

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
	public void setLocale(String locale) { 
		this.locale = locale; 
	} 
	public String getLocale() { 
		return this.locale; 
	} 
	public void setBoardName(String boardName) { 
		this.boardName = boardName; 
	} 
	public String getBoardName() { 
		return this.boardName; 
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