/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.mapper;

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.ApprTypeByApprInfo;
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
	 * 결재 마스터 정보를 가져온다.
	 * @param map
	 * @return
	 */
	ApprovalMasterInfo selectApprMasterInfo(Map<String, Object> map);
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
	
	/**
	 * 결재 유형별 결재 Master 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<ApprTypeByApprInfo> selectApprTypeByApprInfo(Map<String, Object> map);
	
	/**
	 * 결재 마스터를 기반으로 생성된 결재문서가 있는지 체크해서 결재문서 개수를 반환
	 * @param map
	 * @return
	 */
	int selectApprovalDocCount(Map<String, Object> map);
}
