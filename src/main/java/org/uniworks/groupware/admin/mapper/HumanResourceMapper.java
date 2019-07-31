/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.mapper;

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.HumanResource;

/**
 * @author Park Chungwan
 *
 */
public interface HumanResourceMapper {
	/**
	 * groupware 사용자의 인사정보를 가져온다.
	 * @param map
	 * @return
	 */
	HumanResource selectByHumanResource(Map<String, Object> map);
	
	/**
	 * 사번에 해당하는 직원 정보를 가져온다. 본인이 속한 최종 조직의 조직코드와 명칭을 가져온다.(dept_code, dept_desc)
	 * @param map
	 * @return
	 */
	HumanResource selectEmployeeInfoByEmpNo(Map<String, Object> map);
	
	/**
	 * 해당 조직코드의 소속된 인원들을 가져온다. 기준조직레벨보다 작은 레벨의 경우에는 보직자만 가져온다. 그렇지 않을 경우에는 해당 조직레벨에 해당하는 인원들을 가져온다.
	 * 기준조직이 6레벨 이상이 될 경우에는 쿼리문을 더 추가해야 함.
	 * @param map
	 * @return
	 */
	List<HumanResource> selectByOganLevelEmpList(Map<String, Object> map);
	
	/**
	 * groupware 사용자를 성명으로 검색한다.(Like)
	 * @param map
	 * @return
	 */
	List<HumanResource> selectBySearchEmpName(Map<String, Object> map); 
}
