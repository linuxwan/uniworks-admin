/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.mapper;

import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.Ogan;
import org.uniworks.groupware.admin.domain.OganTree;

/**
 * @author Park Chungwan
 *
 */
public interface OganMapper {
	/**
	 * 조직레벨과 조직코드에 해당하는 조직정보를 가져온다.
	 * @param map
	 * @return
	 */
	Ogan selectByOganInfo(Map<String, Object> map);
	/**
	 * 조직 레별에 맞는 조직 목록 가져오기.
	 * @param map
	 * @return
	 */
	List<Ogan> selectByLevelOganList(Map<String, Object> map);
		
	/**
	 * 하위 조직 목록 가져오기.
	 * @param map
	 * @return
	 */
	List<Ogan> selectBySubOganList(Map<String, Object> map);
	
	/**
	 * 조직 목록 가져오기 - easyui tree를 그리기 위한 형태
	 * @param map
	 * @return
	 */
	List<OganTree> selectByAllOganTree(Map<String, Object> map);
	
	/**
	 * 조직명칭으로 검색해서(Like 검색) 해당하는 조직목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<Ogan> selectOganListByOganName(Map<String, Object> map);
	/**
	 * 조직명칭으로 검색해서(Like 검색) 조직책임자가 있는 조직목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<Ogan> selectOganListByChief(Map<String, Object> map);
}
