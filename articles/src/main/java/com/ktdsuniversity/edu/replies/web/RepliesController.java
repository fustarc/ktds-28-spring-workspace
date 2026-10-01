package com.ktdsuniversity.edu.replies.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.replies.service.RepliesService;
import com.ktdsuniversity.edu.replies.vo.request.ModifyRepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistRepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesListVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController // (@Controller + @ResponseBody) 메소드에 @ResponseBody 생략 가능
public class RepliesController {
	
	private RepliesService repliesService;
	
	// GET /replies/{게시글아이디}
	// 게시글에 등록된 댓글을 반환
	@GetMapping("/articles/{articleId}/replies")
	public ApiResponse<RepliesListVO> getReplies(
			@Size(min=18, max=20, message="잘못된 값입니다.")
			@PathVariable String articleId) {
		RepliesListVO result = this.repliesService.readAllReplies(articleId);
		return ApiResponse.OK(result);
	}
	
	// POST /replies/{게시글아이디}
	// 게시글에 댓글 작성 (파일 첨부 가능)
	@PostMapping("/articles/{articleId}/replies")
	public ApiResponse<RepliesVO> makeNewReplies(
			@Size(min=18, max=20, message="잘못된 값입니다.")
			@PathVariable String articleId,
			@Valid @ModelAttribute RegistRepliesVO registRepliesVO,
			BindingResult validationResult) {
				
		System.out.println(validationResult);
		
		if (validationResult.hasErrors()) {
			return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
		}
		
		try {
			RepliesVO result = this.repliesService.createNewReplies(articleId, registRepliesVO);
			return ApiResponse.CREATE(result);
		} catch (IllegalArgumentException iae){
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
	}
	
	// PUT /replies/{게시글아이디}/{댓글아이디}
	// 게시글에 등록된 댓글을 수정 (파일 첨부 가능)
	@PutMapping("/articles/{articleId}/repiles")
	public ApiResponse<RepliesVO> updateReplies(
			@Size(min=18, max=20, message="잘못된 값입니다.")
			@PathVariable String articleId,
			@Valid @ModelAttribute ModifyRepliesVO modifyRepliesVO,
			BindingResult validationResult) {
		
		return null;
	}
	
	// DELETE /replies/{게시글아이디}/{댓글아이디}
	// 게시글에 등록된 댓글 하나 삭제
	// 첨부된 파일 제거
	
	// PUT /replies/{게시글아이디}/recommend/{댓글아이디}
	// 게시글에 등록된 댓글 하나를 추천
	
}
