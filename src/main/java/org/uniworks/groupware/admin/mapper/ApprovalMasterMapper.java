/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.mapper;

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.ApprovalMasterInfo;
import org.uniworks.groupware.admin.domain.Nw013m;

/**
 * @author Park Chung Wan
 *
 */
public interface ApprovalMasterMapper {
	/**
	 * 결재 마스터 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<ApprovalMasterInfo> selectApprMasterList(Map<String, Object> map);
	
	/**
	 * 결재 유형 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<Nw013m> selectApprTypeList(Map<String, Object> map);
	
	/**
	 * 결재 유형 정보를 가져온다.
	 * @param map
	 * @return
	 */
	Nw013m selectApprTypeInfo(Map<String, Object> map);
}
