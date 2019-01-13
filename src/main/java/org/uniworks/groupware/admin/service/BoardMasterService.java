/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.uniworks.groupware.admin.domain.BoardMasterInfo;
import org.uniworks.groupware.admin.domain.Nw001m;
import org.uniworks.groupware.admin.domain.Nw002m;
import org.uniworks.groupware.admin.domain.Nw003m;

/**
 * @author Park Chung Wan
 *
 */
public interface BoardMasterService {
	/**
	 * 게시판 마스터 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<BoardMasterInfo> getBoardMasterList(Map<String, Object> map);
	/**
	 * 게시판 마스터 정보를 삭제한다.
	 * @param map
	 * @return
	 */
	String deleteBoardMasterInfo(Map<String, Object> map, Locale locale);
	/**
	 * 게시판 마스터 정보를 등록한다.
	 * @param nw001m
	 * @param nw002m
	 * @param nw003m
	 * @return
	 */
	int addBoardMasterInfo(Nw001m nw001m, List<Nw002m> nw002mList, List<Nw003m> nw003mList);
	/**
	 * 게시판 마스터 정보를 수정한다.
	 * @param nw001m
	 * @param nw002mList
	 * @param nw003mList
	 * @return
	 */
	int modifyBoardMasterInfo(Nw001m nw001m, List<Nw002m> nw002mList, List<Nw003m> nw003mList);
}
