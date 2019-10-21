/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service;

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.ChargeUser;
import org.uniworks.groupware.admin.domain.ContentAuth;
import org.uniworks.groupware.admin.domain.ContentInfo;
import org.uniworks.groupware.admin.domain.Nw030m;
import org.uniworks.groupware.admin.domain.Nw031m;
import org.uniworks.groupware.admin.domain.Nw032m;

/**
 * @author Park Chung Wan
 *
 */
public interface ContentService {
	/**
	 * 컨텐츠 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<ContentInfo> getContentList(Map<String, Object> map);
	/**
	 * 컨텐츠 정보를 가져온다.
	 * @param map
	 * @return
	 */
	ContentInfo getContentInfo(Map<String, Object> map);
	/**
	 * 컨텐츠 정보를 등록한다.
	 * @param nw030m
	 * @param nw031mList
	 * @param nw032mList
	 * @return
	 */
	int addContentInfo(Nw030m nw030m, List<Nw031m> nw031mList, List<Nw032m> nw032mList);
	/**
	 * 컨텐츠 정보를 수정한다.
	 * @param nw030m
	 * @param nw031mList
	 * @param nw032mList
	 * @return
	 */
	int modifyContentInfo(Nw030m nw030m, List<Nw031m> nw031mList, List<Nw032m> nw032mList);
	/**
	 * 컨텐츠 정보를 삭제한다.
	 * @param map
	 * @return
	 */
	int removeContentInfo(Map<String, Object> map);
	/**
	 * 컨텐츠 사용유무를 미사용(N)으로 변경
	 * @param map
	 * @return
	 */
	int modifyContentUseIndc(Map<String, Object> map);
	/**
	 * 컨텐츠 권한 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<ContentAuth> getContentAuthList(Map<String, Object> map);
	/**
	 * 컨텐츠 담당자 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<ChargeUser> getContentChargeList(Map<String, Object> map);
}
