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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.uniworks.groupware.admin.domain.ChargeUser;
import org.uniworks.groupware.admin.domain.ContentAuth;
import org.uniworks.groupware.admin.domain.ContentInfo;
import org.uniworks.groupware.admin.domain.Nw030m;
import org.uniworks.groupware.admin.domain.Nw031m;
import org.uniworks.groupware.admin.domain.Nw032m;
import org.uniworks.groupware.admin.mapper.ContentMapper;
import org.uniworks.groupware.admin.service.ContentService;
import org.uniworks.groupware.admin.service.Nw030mService;
import org.uniworks.groupware.admin.service.Nw031mService;
import org.uniworks.groupware.admin.service.Nw032mService;
import org.uniworks.groupware.admin.service.Nw033mService;
import org.uniworks.groupware.admin.service.Nw034mService;

/**
 * @author Park Chung Wan
 *
 */
@Service
@Transactional(readOnly = true) 
public class ContentServiceImpl implements ContentService {
	@Autowired ContentMapper contentMapper;
	@Autowired Nw030mService nw030mService;
	@Autowired Nw031mService nw031mService;
	@Autowired Nw032mService nw032mService;
	@Autowired Nw033mService nw033mService;
	@Autowired Nw034mService nw034mService;
	
	/**
	 * 컨텐츠 목록을 가져온다.
	 */
	@Override
	public List<ContentInfo> getContentList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return contentMapper.selectContentList(map);
	}

	/**
	 * 컨텐츠 정보를 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public ContentInfo getContentInfo(Map<String, Object> map) {
		return contentMapper.selectContentInfo(map);
	}
	
	/**
	 * 컨텐츠 정보를 등록한다.
	 * @param nw030m
	 * @param nw031mList
	 * @param nw032mList
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int addContentInfo(Nw030m nw030m, List<Nw031m> nw031mList, List<Nw032m> nw032mList) {
		int cnt = nw030mService.addNw030m(nw030m);
		
		for (Nw031m nw031m : nw031mList) {
			int nw031mCnt = nw031mService.addNw031m(nw031m);
		}
		
		for (Nw032m nw032m : nw032mList) {
			int nw032mCnt = nw032mService.addNw032m(nw032m);
		}
		
		return cnt;
	}
	
	/**
	 * 컨텐츠 정보를 수정한다.
	 * @param nw030m
	 * @param nw031mList
	 * @param nw032mList
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int modifyContentInfo(Nw030m nw030m, List<Nw031m> nw031mList, List<Nw032m> nw032mList) {
		int cnt = nw030mService.updateNw030m(nw030m);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cntnId", nw030m.getCntnId());
		
		int delCnt1 = nw031mService.deleteNw031m(map);		
		for (Nw031m nw031m : nw031mList) {
			int nw031mCnt = nw031mService.addNw031m(nw031m);
		}
		
		List<String> arr = new ArrayList<String>(); 
		for (Nw032m nw032m : nw032mList) {			
			map.put("coId", nw032m.getCoId());
			Nw032m tempNw032m = nw032mService.getNw032m(map);
			arr.add(nw032m.getCoId());
			if (tempNw032m != null && tempNw032m.getCoId().equalsIgnoreCase(nw032m.getCoId())) {
				int nw032mCnt = nw032mService.updateNw032m(nw032m);
			} else {
				int nw032mCnt = nw032mService.addNw032m(nw032m);
			}
		}
		
		Map<String, Object> nw032mUseIndc = new HashMap<String, Object>();
		nw032mUseIndc.put("coId", arr);
		nw032mUseIndc.put("cntnId", nw030m.getCntnId());
		modifyContentUseIndc(nw032mUseIndc);
		
		return cnt;
	}
	
	/**
	 * 컨텐츠 정보를 삭제한다.
	 * @param map
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int removeContentInfo(Map<String, Object> map) {
		int cnt = nw034mService.deleteNw034m(map);
		
		cnt = nw033mService.deleteNw033m(map);
		
		cnt = nw032mService.deleteNw032m(map);
		
		cnt = nw031mService.deleteNw031m(map);
		
		cnt = nw030mService.deleteNw030m(map);
		
		return cnt;
	}
	
	/**
	 * 컨텐츠 사용유무를 미사용(N)으로 변경
	 * @param map
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int modifyContentUseIndc(Map<String, Object> map) {
		int cnt = contentMapper.updateUseIndc(map);
		return cnt;
	}
	
	/**
	 * 컨텐츠 권한 목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<ContentAuth> getContentAuthList(Map<String, Object> map) {
		return contentMapper.selectContentAuthList(map);
	}
	
	/**
	 * 컨텐츠 담당자 목록을 가져온다.
	 * @param map
	 * @return
	 */
	@Override
	public List<ChargeUser> getContentChargeList(Map<String, Object> map) {
		return contentMapper.selectContentChargeList(map);
	}
}
