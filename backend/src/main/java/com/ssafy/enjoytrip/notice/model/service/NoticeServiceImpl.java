package com.ssafy.enjoytrip.notice.model.service;


import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.notice.model.NoticeDto;
import com.ssafy.enjoytrip.notice.model.mapper.NoticeMapper;
import com.ssafy.enjoytrip.util.PageNavigation;
import com.ssafy.enjoytrip.util.SizeConstant;


@Service("NoticeServiceImpl")
public class NoticeServiceImpl implements NoticeService{

	
	private NoticeMapper noticeMapper;
	
	public NoticeServiceImpl(NoticeMapper noticeMapper) {
		super();
		this.noticeMapper = noticeMapper;
	}

	@Override
	public void writeArticle(NoticeDto noticeDto) throws Exception {
		noticeMapper.writeArticle(noticeDto);
	}

	// 공지 글 목록 가져오기
	@Override
	public List<NoticeDto> listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		System.out.println(map);
		if("userid".equals(key))
			key = "n.user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);

		return noticeMapper.listArticle(param);
	}

	// 미구현
	@Override
	public List<NoticeDto> sortListArticle(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	// 페이지 네이게이션
	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = noticeMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	// 공지 글 내용 가져오기
	@Override
	public NoticeDto getArticle(int articleNo) throws Exception {
		return noticeMapper.getArticle(articleNo);
	}

	// 조회수
	@Override
	public void updateHit(int articleNo) throws Exception {
		noticeMapper.updateHit(articleNo);
	}

	// 공지 수정
	@Override
	public void modifyArticle(NoticeDto noticeDto) throws Exception {
		noticeMapper.modifyArticle(noticeDto);
	}

	// 공지 삭제
	@Override
	public void deleteArticle(int articleNo) throws Exception {
		noticeMapper.deleteArticle(articleNo);
	}

	// 미구현
	@Override
	public void deleteArticleAll(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}


	
	
}
