package com.ktdsuniversity.edu.replies.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktdsuniversity.edu.articles.dao.ArticlesDao;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;
import com.ktdsuniversity.edu.files.components.MultipartHandler;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import com.ktdsuniversity.edu.replies.dao.RepliesDao;
import com.ktdsuniversity.edu.replies.vo.request.ModifyRepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesListVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RepliesServiceImpl implements RepliesService{
	
	private ArticlesDao articlesDao;
	private RepliesDao repliesDao;
	private MultipartHandler multipartHandler;

	@Override
	public RepliesListVO readAllReplies(String articleId) {
		
		ArticlesVO articles = this.articlesDao.selectArticleByArticleId(articleId);
		if (articles == null) {
			throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
		}
		
		long count = this.repliesDao.selectRepliesCount(articleId);
		List<RepliesVO> replieList = this.repliesDao.selectAllReplies(articleId);
		
		RepliesListVO list = new RepliesListVO();
		list.setRepliesCount(count);
		list.setReplieList(replieList);
		
		return list;
	}

	@Override
	public RepliesVO createNewReply(String articleId, RegistRepliesVO registRepliesVO) {
		ArticlesVO articles = this.articlesDao.selectArticleByArticleId(articleId);
		if (articles == null) {
			throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
		}
		
		String fileSetId = this.multipartHandler.storeFiles(
									registRepliesVO.getFile(), 
									registRepliesVO.getEmail());
		registRepliesVO.setFileSetId(fileSetId);
		
		int insertedRows = this.repliesDao.insertNewReplies(articleId, registRepliesVO);
		
		System.out.println(insertedRows + "개의 댓글이 생성되었습니다.");
		
		if (insertedRows == 0) {
			throw new IllegalArgumentException("입력값이 유효하지 않습니다.");
		}
		
		return this.repliesDao.selectReplyByReplyId(articleId, registRepliesVO.getId() );
		
	}

	@Override
	public RepliesVO updateReply(String articleId, String replyId, ModifyRepliesVO modifyRepliesVO) {
		
		ArticlesVO articles = this.articlesDao.selectArticleByArticleId(articleId);
		if (articles == null) {
			throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
		}
		
		RepliesVO reply = this.repliesDao.selectReplyByReplyId(articleId, replyId);
		if (reply == null) {
			throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
		}
		
		String fileSetId = this.multipartHandler.storeFiles(
												modifyRepliesVO.getFile(), 
												modifyRepliesVO.getEmail(), 
												reply.getFileSetId());
		modifyRepliesVO.setFileSetId(fileSetId);
		
		int updatedRows = this.repliesDao.updateReply(articleId, replyId, modifyRepliesVO);		
		if (updatedRows == 0) {
			throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
		}
		
		return this.repliesDao.selectReplyByReplyId(articleId, replyId);
	}

	@Override
	public String deleteReply(String articleId, String replyId) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpSession session = request.getSession();
		MembersVO loggedMember = (MembersVO) session.getAttribute("__LOGIN_USER__");
		
		RepliesVO reply = this.repliesDao.selectReplyByReplyId(articleId, replyId);
		
		if ( ! loggedMember.getEmail().equals(reply.getEmail()) ) {
			throw new IllegalArgumentException("삭제할 수 없는 댓글입니다.");
		}
		
		ArticlesVO articles = this.articlesDao.selectArticleByArticleId(articleId);
		if (articles == null) {
			throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
		}
		
		int deletedRows = this.repliesDao.deleteReplyByReplyId(articleId, replyId);
		if (deletedRows == 0) {
			throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
		}
		
		int deleteCount = this.multipartHandler.deleteFiles(reply.getFileSetId());
		System.out.println(deleteCount + "개의 파일이 삭제되었습니다.");
		return replyId;
	}

	@Override
	public long recommendOneReply(String articleId, String replyId) {
		ArticlesVO articles = this.articlesDao.selectArticleByArticleId(articleId);
		if (articles == null) {
			throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
		}
		
		int updatedRows = this.repliesDao.updateIncreaseRecommendCount(articleId, replyId);
		if (updatedRows == 0) {
			throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
		}
		
		RepliesVO reply = this.repliesDao.selectReplyByReplyId(articleId, replyId);
		return reply.getRecommendCnt();
	
	}

}
