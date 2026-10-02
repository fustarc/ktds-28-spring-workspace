package com.ktdsuniversity.edu.replies.service;

import com.ktdsuniversity.edu.replies.vo.request.ModifyRepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesListVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;

public interface RepliesService {
	
	RepliesListVO readAllReplies(String articleId);

	RepliesVO createNewReply(String articleId, RegistRepliesVO registRepliesVO);
	
	RepliesVO updateReply(String articleId, String replyId, ModifyRepliesVO modifyRepliesVO);
	
	String deleteReply(String articleId, String replyId);
	
	long recommendOneReply(String articleId, String replyId);
}
