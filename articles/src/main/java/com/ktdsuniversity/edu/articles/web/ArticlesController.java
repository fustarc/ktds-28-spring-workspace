package com.ktdsuniversity.edu.articles.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.articles.service.ArticlesService;
import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;
import com.ktdsuniversity.edu.commons.util.ApiResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class ArticlesController {
	
//	/**
//	 * BeanContainer에서 같은 타입의 객체가 있다면, 그것을 멤버변수에게 할당시켜라
//	 */
//	@Autowired
//	/**
//	 * BaenContainer 에 ArticlesService 타입의 객체가 여러 개 있을 경우
//	 * 객체 명이 "articlesServiceImpl" 인 객체를 멤버변수에 할당시켜라
//	 */
//	@Qualifier("articlesServiceImpl")
//	private ArticlesService articlesService;
	
	private ArticlesService articlesService;
	
	/**
	 * Spring Framework 7.0 이상
	 * Spring Boot 4.0 이상에서는 @Autowired 사용을 권장하지 않는다.
	 * 
	 * 대신, 생성자를 이용한 DI를 권장한다.
	 * ==> 이유: Lombok Library 때문에 (Getter, Setter, 생성자, toString 자동 생성)
	 */
	/*
	public ArticlesController(ArticlesService articlesService) {
		this.articlesService = articlesService;
	}
	*/
	
	
	@GetMapping("/articles")
	// 컨트롤러가 반환 시키는 "객체" 를 "JSON" 으로 변환시키는 View 를 사용해라 => @ResponseBody
	@ResponseBody
	public ApiResponse<ArticleListVO> getArticles() {
//		System.out.println(this.articlesService);
		ArticleListVO result = this.articlesService.readAllArticles();
		return ApiResponse.OK(result);
	}
	
	@GetMapping("/articles/{articleId}")
	@ResponseBody
	public ApiResponse<ArticlesVO> getOneArticle(
			@Size(min=18, max=20, message="잘못된 값입니다.") 
			@PathVariable String articleId) {
		try {
		ArticlesVO result = this.articlesService.readOneArticle(articleId);
		return ApiResponse.OK(result);
		} catch (IllegalArgumentException iae){
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}	
	}
	
	/*
	@PostMapping("/articles")
	@ResponseBody
	public ApiResponse<ArticlesVO> makeNewArticle(@RequestBody RegistArticleVO registArticleVO) {
		try {
			ArticlesVO result = this.articlesService.createNewArticle(registArticleVO);
			return ApiResponse.CREATE(result);
		} catch (IllegalArgumentException iae){
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
	}
	*/
	
	@PostMapping("/articles")
	@ResponseBody
	public ApiResponse<ArticlesVO> makeNewArticle(
			// Command Object
			// 클라이언트가 컨트롤러로 전송한 파라미터(폼 파라미터, 쿼리스트링 파라미터[url ?k=v])를 자동으로 받아오는 역할
			@Valid @ModelAttribute RegistArticleVO registArticleVO,
			BindingResult validationResult
			// 클라이언트가 컨트롤러로 전송한 파라미터(폼 파라미터, 쿼리스트링 파라미터)를 하나씩 받아오는 역할 
			// ,@RequestParam List<MultipartFile> file
	) {
		
		System.out.println(validationResult);
		
			// Validation 검사를 통화하지 못했다면
			if (validationResult.hasErrors()) {
				return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
			}
		
			try {
				ArticlesVO result = this.articlesService.createNewArticle(registArticleVO);
				return ApiResponse.CREATE(result);
			} catch (IllegalArgumentException iae){
				return ApiResponse.FORBIDDEN(iae.getMessage());
			}
	}
	
	@PutMapping("/articles/{articleId}")
	@ResponseBody
	public ApiResponse<ArticlesVO> updateArticle(
			@Size(min=18, max=20, message="잘못된 값입니다.") 
			@PathVariable String articleId, 
			@Valid @ModelAttribute ModifyArticleVO modifyArticleVO
			, BindingResult validationResult ) {
		
		System.out.println(validationResult);
		
		if (validationResult.hasErrors()) {
			return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
		}
		
		try {
			ArticlesVO result = this.articlesService.updateArticle(articleId, modifyArticleVO);
			return ApiResponse.OK(result);
		} catch (IllegalArgumentException iae){
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
	}
	
	@PutMapping("/articles/recommend/{articleId}")
	@ResponseBody
	public ApiResponse<Long> recommendOneArticle(
			@Size(min=18, max=20, message="잘못된 값입니다.") 
			@PathVariable String articleId) {
		try {
			long recommendResult = this.articlesService.recommendOneArticle(articleId);
			return ApiResponse.OK(recommendResult);
		} catch (IllegalArgumentException iae){
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
	}
	
	@DeleteMapping("/articles/{articleId}")
	@ResponseBody
	public ApiResponse<String> deleteArticle(
			@Size(min=18, max=20, message="잘못된 값입니다.") 
			@PathVariable String articleId) {
		try {
			String deleteResult = this.articlesService.deleteArticle(articleId);
			return ApiResponse.OK(deleteResult);
		} catch (IllegalArgumentException iae){
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
	}
	
	

}
