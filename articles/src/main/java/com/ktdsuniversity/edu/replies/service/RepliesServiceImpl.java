package com.ktdsuniversity.edu.replies.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.files.components.MultipartHandler;
import com.ktdsuniversity.edu.replies.dao.RepliesDao;
import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesListVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RepliesServiceImpl implements RepliesService{
	
	private RepliesDao repliesDao;
	private MultipartHandler multipartHandler;

	@Override
	public RepliesListVO readAllReplies(String articleId) {
		
		long count = this.repliesDao.selectRepliesCount();
		List<RepliesVO> replieList = this.repliesDao.selectAllReplies();
		
		RepliesListVO list = new RepliesListVO();
		list.setRepliesCount(count);
		list.setReplieList(replieList);
		
		return list;
	}

	@Override
	public RepliesVO createNewReplies(String articleId, RegistRepliesVO registRepliesVO) {
		
		String fileSetId = this.multipartHandler.storeFiles(
									registRepliesVO.getFile(), 
									registRepliesVO.getEmail());
		registRepliesVO.setFileSetId(fileSetId);
		
		int insertedRows = this.repliesDao.insertNewReplies(articleId, registRepliesVO);
		
		System.out.println(insertedRows + "개의 댓글이 생성되었습니다.");
		
		if (insertedRows > 0) {
			return this.repliesDao.selectRepliesByReplieId(registRepliesVO.getId() );
		}
		
		throw new IllegalArgumentException("입력값이 유효하지 않습니다.");
	}

}
