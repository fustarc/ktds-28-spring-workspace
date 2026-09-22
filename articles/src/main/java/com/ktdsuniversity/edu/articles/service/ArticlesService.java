package com.ktdsuniversity.edu.articles.service;

import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;

public interface ArticlesService {

	/**
	 * 게시글의 목록 조회
	 * @return (게시글 개수, 게시글 목록)
	 */
	ArticleListVO readAllArticles();
	
	/**
	 * 게시글 생성
	 * @param registArticleVO 클라이언트가 보내준 게시글 등록 정보 (제목, 내용, 이메일)
	 */
	ArticlesVO createNewArticle(RegistArticleVO registArticleVO);

	/**
	 * 게시글 수정
	 * @param articleId 수정하려는 게시글의 ID
	 * @param modifyArticleVO 수정하려는 내용
	 * @return 수정된 결과
	 */
	ArticlesVO updateArticle(String articleId, ModifyArticleVO modifyArticleVO);

	/**
	 * 게시글 삭제
	 * @param articleId 삭제하려는 게시글의 ID
	 * @return 삭제된 결과
	 */
	String deleteArticle(String articleId);
	
	/**
	 * 게시글 조회
	 * @param articleId 조회하려는 게시글의 ID
	 * @return 조회된 결과
	 */
	ArticlesVO readOneArticle(String articleId);
	
	/**
	 * 게시글 추천
	 * @param articleId 조회하려는 게시글의 ID
	 * @return 추천한 결과
	 */
	long recommendOneArticle(String articleId);
}
