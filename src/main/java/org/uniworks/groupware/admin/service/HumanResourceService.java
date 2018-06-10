/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.uniworks.groupware.admin.domain.Hr010m;
import org.uniworks.groupware.admin.domain.Hr011m;
import org.uniworks.groupware.admin.domain.HumanResource;

/**
 * @author Park Chungwan
 *
 */
@Service
public interface HumanResourceService {
	/**
	 * 인사마스터 정보에서 직원 정보를 가져온다.
	 * @param map
	 * @return
	 */
	HumanResource getByHumanResource(Map<String, Object> map);
	
	/**
	 * 인사마스터 정보에서 직원정보를 가져온다. 기본 조직정보 포함.
	 * @param map
	 * @return
	 */
	HumanResource getEmployeeInfoByEmpNo(Map<String, Object> map);
	
	/**
	 * 특정 조직의 속한 직원 모두를 가져온다. BaseOganLev의 값보다 작은 상위 조직의 경우에는 보직자만 가져온다.
	 * @param map
	 * @return
	 */
	List<HumanResource> getByOganLevelEmpList(Map<String, Object> map);
	
	/**
	 * 성명으로 직원 찾기
	 * @param map
	 * @return
	 */
	List<HumanResource> getBySearchEmpName(Map<String, Object> map);
	
	/**
	 * 회사별 소속 직원 정보 등록.
	 * @param record
	 * @param arr
	 */
	int addEmpInfo(Hr010m record, ArrayList<Hr011m> arr);
	
	/**
	 * 회사별 소속 직원 정보 수정
	 * @param record
	 * @param arr
	 * @return
	 */
	int updateEmpInfo(Hr010m record, ArrayList<Hr011m> arr);
	
	/**
	 * 회사별 소속 직원 정보 삭제
	 * @param hr010m
	 * @return
	 */
	int deleteEmpInfo(Map<String, Object> map);
}
