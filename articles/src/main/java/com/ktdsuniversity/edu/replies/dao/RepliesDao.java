package com.ktdsuniversity.edu.replies.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktdsuniversity.edu.replies.vo.request.ModifyRepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;

@Mapper
public interface RepliesDao {
	
	long selectRepliesCount(String articleId);
	
	List<RepliesVO> selectAllReplies(String articleId);
	
	int insertNewReplies(@Param("articleId") String articleId, 
						 @Param("registRepliesVO") RegistRepliesVO registRepliesVO);
	
	RepliesVO selectReplyByReplyId(@Param("articleId") String articleId,
									@Param("replyId") String replyId);

	int updateReply(@Param("articleId") String articleId,
					@Param("replyId") String replyId,
					@Param("modifyReplies") ModifyRepliesVO modifyRepliesVO);
	
	int deleteReplyByReplyId(@Param("articleId") String articleId, 
			 				 @Param("replyId") String replyId);

	int updateIncreaseRecommendCount(@Param("articleId") String articleId, 
					 				 @Param("replyId") String replyId);
}
