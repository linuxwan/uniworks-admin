/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uniworks.groupware.admin.domain.Ogan;
import org.uniworks.groupware.admin.domain.OganTree;

/**
 * @author Park Chungwan
 *
 */
public interface OganService {
	/**
	 * 조직레벨과 조직코드에 해당하는 조직정보를 가져온다.
	 * @param map
	 * @return
	 */
	Ogan getByOganInfo(Map<String, Object> map);
	/**
	 * 조직 레별에 맞는 조직 목록 가져오기.
	 * @param map
	 * @return
	 */
	List<Ogan> getByLevelOganList(Map<String, Object> map);
	/**
	 * 하위 조직 목록 가져오기.
	 * @param map
	 * @return
	 */
	List<Ogan> getBySubOganList(Map<String, Object> map);
	/**
	 * 조직 목록 가져오기 - easyui tree를 그리기 위함.
	 * @param map
	 * @return
	 */
	List<OganTree> getByAllOganTree(Map<String, Object> map);
	/**
	 * 조직명칭으로 검색해서(Like 검색) 해당하는 조직목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<Ogan> getOganListByOganName(Map<String, Object> map);
	/**
	 * 조직명칭으로 검색해서(Like 검색) 조직책임자가 있는 조직목록을 가져온다.
	 * @param map
	 * @return
	 */
	List<Ogan> getOganListByChief(Map<String, Object> map);		
	
	/**
	 * 선택한 조직코드를 기준으로 최상위 조직까지의 조직정보를 가져온다. 재귀호출.
	 * @param ogan
	 * @param lang
	 */
	void getHighOganInfoRecursiveFunction(Ogan ogan, String lang);
	
	/**
	 * 상위조직 정보 모두를 가져온다.
	 * @return
	 */	
	ArrayList<Ogan> getHighOganList();
	
	/**
	 * highOganList를 Clear 한다.
	 */	
	void clearArrayList();
}
