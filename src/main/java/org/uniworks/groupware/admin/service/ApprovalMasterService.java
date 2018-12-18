/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.uniworks.groupware.admin.domain.ApprTypeByApprInfo;
import org.uniworks.groupware.admin.domain.ApprovalMasterInfo;
import org.uniworks.groupware.admin.domain.Nw010m;
import org.uniworks.groupware.admin.domain.Nw011m;
import org.uniworks.groupware.admin.domain.Nw012m;
import org.uniworks.groupware.admin.domain.Nw013m;
import org.uniworks.groupware.admin.domain.Nw014m;
import org.uniworks.groupware.admin.domain.Nw015m;

/**
 * @author Park Chung Wan
 *
 */
public interface ApprovalMasterService {
	/**
	 * 결재 마스터 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<ApprovalMasterInfo> getApprMasterList(Map<String, Object> map);
	/**
	 * 결재 마스터 목록을 가져온다.(전체)
	 * @param map
	 * @return
	 */
	List<ApprovalMasterInfo> getApprMasterListAll(Map<String, Object> map);
	/**
	 * 결재 마스터 정보를 등록한다.
	 * @param nw010m
	 * @param nw011mList
	 * @return
	 */
	int addApprovalMasterInfo(Nw010m nw010m, List<Nw011m> nw011mList, Nw012m nw012m, Nw015m nw015m);
	/**
	 * 결재 마스터 정보를 수정한다.
	 * @param nw010m
	 * @param nw011mList
	 * @return
	 */
	int modifyApprovalMasterInfo(Nw010m nw010m, List<Nw011m> nw011mList, Nw012m nw012m, Nw015m nw015m);
	/**
	 * 결재 마스터 정보를 삭제한다.
	 * @param map
	 * @return
	 */
	int deleteApprovalMasterInfo(Map<String, Object> map);
	/**
	 * 결재 유형 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<Nw013m> getApprTypeList(Map<String, Object> map);
	/**
	 * 결재 유형 정보를 등록한다.
	 * @param nw013m
	 * @param nw014m
	 * @return
	 */
	int addApprovalTypeInfo(Nw013m nw013m, List<Nw014m> nw014mList);
	/**
	 * 결재 유형 정보를 가져온다.
	 * @param map
	 * @return
	 */
	Nw013m getApprTypeInfo(Map<String, Object> map);
	/**
	 * 결재 유형 정보를 Update한다.
	 * @param nw013m
	 * @param nw014mList
	 * @return
	 */
	int updateApprovalTypeInfo(Nw013m nw013m, List<Nw014m> nw014mList);
	/**
	 * 결재 유형 정보 삭제
	 * @param map
	 * @return
	 */
	String deleteApprovalTypeInfo(Map<String, Object> map, Locale locale);
	/**
	 * 결재 유형별 결재 Master 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<ApprTypeByApprInfo> getApprTypeByApprInfo(Map<String, Object> map);
}
