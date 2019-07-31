/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uniworks.groupware.admin.common.util.DateUtil;
import org.uniworks.groupware.admin.domain.Ogan;
import org.uniworks.groupware.admin.domain.OganTree;
import org.uniworks.groupware.admin.domain.Om001m;
import org.uniworks.groupware.admin.mapper.OganMapper;
import org.uniworks.groupware.admin.service.OganService;

/**
 * @author Park Chungwan
 *
 */
@Service
@Transactional(readOnly = true)
public class OganServiceImpl implements OganService {
	@Autowired OganMapper oganMapper;
	private ArrayList<Ogan> highOganList = new ArrayList<Ogan>();
	
	/**
	 * 조직레벨과 조직코드에 해당하는 조직정보를 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public Ogan getByOganInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Ogan ogan = oganMapper.selectByOganInfo(map);
		return ogan;
	}

	/**
	 * 조직 레별에 맞는 조직 목록 가져오기.
	 * @param map
	 * @return
	 */
	@Override
	public List<Ogan> getByLevelOganList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<Ogan> list = oganMapper.selectByLevelOganList(map);
		return list;
	}	
	
	/**
	 * 하위 조직 목록 가져오기.
	 * @param map
	 * @return
	 */
	@Override
	public List<Ogan> getBySubOganList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<Ogan> list = oganMapper.selectBySubOganList(map);
		return list;
	}

	/**
	 * 하위 조직 목록 가져오기 - easyui tree를 그리기 위함.
	 * @param map
	 * @return
	 */
	@Override
	public List<OganTree> getByAllOganTree(Map<String, Object> map) {
		List<OganTree> list = oganMapper.selectByAllOganTree(map);
		return list;
	}
	
	/**
	 * 조직명칭으로 검색해서(Like 검색) 해당하는 조직목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<Ogan> getOganListByOganName(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<Ogan> list = oganMapper.selectOganListByOganName(map);
		return list;
	}

	/**
	 * 조직명칭으로 검색해서(Like 검색) 조직책임자가 있는 조직목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<Ogan> getOganListByChief(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<Ogan> list = oganMapper.selectOganListByChief(map);
		return list;
	}

	/**
	 * 선택한 조직코드를 기준으로 최상위 조직까지의 조직정보를 가져온다. 재귀호출.
	 * @param ogan
	 * @param lang
	 */
	@Override
	public void getHighOganInfoRecursiveFunction(Ogan ogan, String lang) { 
		int lev = 0;
		if (ogan.getHighOganLev() != null && !ogan.getHighOganLev().trim().equals("")) {
			lev = Integer.parseInt(ogan.getHighOganLev());
		}
		
		if (lev >= 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("coId", ogan.getCoId());
			map.put("oganLev", ogan.getHighOganLev());
			map.put("oganCode", ogan.getHighOganCode());
			map.put("oganEstbDate", DateUtil.getCurrentDate());
			map.put("lang", lang);
			
			Ogan oganInfo = oganMapper.selectByOganInfo(map);
			Ogan highOgan = null;
			
			if (oganInfo != null) {
				highOgan = oganInfo;
				highOganList.add(highOgan);
				getHighOganInfoRecursiveFunction(highOgan, lang);			
			}
		}
	}
	
	/**
	 * 상위조직 정보 모두를 가져온다.
	 * @return
	 */
	@Override
	public ArrayList<Ogan> getHighOganList() {
		return highOganList;
	}
	
	/**
	 * highOganList를 Clear 한다.
	 */
	@Override
	public void clearArrayList() {
		highOganList.clear();
	}
}
