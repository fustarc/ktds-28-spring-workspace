package com.ktdsuniversity.edu.replies.service;

import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesListVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;

public interface RepliesService {
	
	RepliesListVO readAllReplies(String articleId);

	RepliesVO createNewReplies(String articleId, RegistRepliesVO registRepliesVO);
	
	
}
