/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.service.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.uniworks.groupware.admin.domain.BoardMasterInfo;
import org.uniworks.groupware.admin.domain.Nw001m;
import org.uniworks.groupware.admin.domain.Nw002m;
import org.uniworks.groupware.admin.domain.Nw003m;
import org.uniworks.groupware.admin.mapper.BoardMasterMapper;
import org.uniworks.groupware.admin.mapper.Nw001mMapper;
import org.uniworks.groupware.admin.mapper.Nw002mMapper;
import org.uniworks.groupware.admin.mapper.Nw003mMapper;

/**
 * @author Park Chung Wan
 *
 */
@Service 
@Transactional(readOnly = true) 
public class BoardMasterServiceImpl implements org.uniworks.groupware.admin.service.BoardMasterService {
	@Autowired BoardMasterMapper boardMstMapper;
	@Autowired Nw001mMapper nw001mMapper;
	@Autowired Nw002mMapper nw002mMapper;
	@Autowired Nw003mMapper nw003mMapper;
	@Autowired MessageSource messageSource;
	
	/**
	 * 게시판 마스터 목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<BoardMasterInfo> getBoardMasterList(Map<String, Object> map) {
		return boardMstMapper.selectBoardMasterList(map);
	}

	/**
	 * 게시판 마스터 정보를 삭제한다.
	 * @param map
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String deleteBoardMasterInfo(Map<String, Object> map, Locale locale) {
		String result = "";
		
		int cnt = nw003mMapper.deleteByBoardId(map);		
		
		cnt = nw002mMapper.deleteByPrimaryKey(map);
		
		cnt = nw001mMapper.deleteByPrimaryKey(map);
		
		if (cnt > 0) {
			result = messageSource.getMessage("resc.msg.deleteOk", null, locale);
		} else {
			result = messageSource.getMessage("resc.msg.deleteFail", null, locale);
		}
		
		return result;
	}
	
	/**
	 * 게시판 마스터 정보를 등록한다.
	 * @param nw001m
	 * @param nw002m
	 * @param nw003m
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int addBoardMasterInfo(Nw001m nw001m, List<Nw002m> nw002mList, List<Nw003m> nw003mList) {
		Nw001m checkedNw001m = nullCheckNw001mIndcValue(nw001m);
		
		int cnt = nw001mMapper.insert(checkedNw001m);
		
		for (Nw002m nw002m : nw002mList) {
			cnt = nw002mMapper.insert(nw002m);
		}
		
		for (Nw003m nw003m : nw003mList) {
			cnt = nw003mMapper.insert(nw003m);
		}
		
		return cnt;
	}
	
	/**
	 * 게시판 마스터 정보를 수정한다.
	 * @param nw001m
	 * @param nw002mList
	 * @param nw003mList
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int modifyBoardMasterInfo(Nw001m nw001m, List<Nw002m> nw002mList, List<Nw003m> nw003mList) {
		Nw001m checkedNw001m = nullCheckNw001mIndcValue(nw001m);
		
		int cnt = nw001mMapper.updateByPrimaryKey(checkedNw001m);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coId", nw001m.getCoId());
		map.put("boardId", nw001m.getBoardId());
		
		//nw002m 삭제 후 Insert
		cnt = nw002mMapper.deleteByPrimaryKey(map);
		for (Nw002m nw002m : nw002mList) {
			cnt = nw002mMapper.insert(nw002m);
		}
		//nw003m 삭제 후 Insert
		cnt = nw003mMapper.deleteByBoardId(map);
		for (Nw003m nw003m : nw003mList) {
			cnt = nw003mMapper.insert(nw003m);
		}
		
		return cnt;
	}
	
	/**
	 * Nw001m의 Indc 필드가 null일 경우, N 값을 설정
	 * @param nw001m
	 * @return
	 */
	private Nw001m nullCheckNw001mIndcValue(Nw001m nw001m) {
		if (nw001m.getRplyIndc() == null) nw001m.setRplyIndc("N");
		if (nw001m.getCmntIndc() == null) nw001m.setCmntIndc("N");
		if (nw001m.getAtchIndc() == null) nw001m.setAtchIndc("N");
		if (nw001m.getEvalIndc() == null) nw001m.setEvalIndc("N");
		if (nw001m.getValidTermIndc() == null) nw001m.setValidTermIndc("N");
		if (nw001m.getAnonyIndc() == null) nw001m.setAnonyIndc("N");
		if (nw001m.getAnceIndc() == null) nw001m.setAnceIndc("N");
		if (nw001m.getApprIndc() == null) nw001m.setApprIndc("N");
		if (nw001m.getUseIndc() == null) nw001m.setUseIndc("N");
		if (nw001m.getEntrOpenIndc() == null) nw001m.setEntrOpenIndc("N");
		
		return nw001m;
	}
}
