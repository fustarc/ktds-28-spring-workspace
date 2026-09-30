package com.ktdsuniversity.edu.replies.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;

@Mapper
public interface RepliesDao {
	
	long selectRepliesCount();
	
	List<RepliesVO> selectAllReplies();
	
	int insertNewReplies(@Param("articleId") String articleId, 
						 @Param("registRepliesVO") RegistRepliesVO registRepliesVO);
	
	RepliesVO selectRepliesByReplieId(String replieId);

	
}
