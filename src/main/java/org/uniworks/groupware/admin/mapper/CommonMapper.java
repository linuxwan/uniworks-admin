/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.mapper;

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.CommonCode;

/**
 * @author Park Chungwan
 *
 */
public interface CommonMapper {
	/**
	 * 주코드에 속하는 하위 코드 목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<CommonCode> selectByCommonSubCodeList(Map<String, Object> map);	
}
