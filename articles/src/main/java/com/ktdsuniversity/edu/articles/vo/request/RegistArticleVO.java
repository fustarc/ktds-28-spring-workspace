package com.ktdsuniversity.edu.articles.vo.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 게시글 등록을 위해 클라이언트가 보내준 정보를 받아오기 위한 VO 클래스.
 * 보통, 테이블에서 필수입력값(Not Null) 컬럼들을 작성한다.
 */
@Data
public class RegistArticleVO {

	private String id;
	@NotBlank(message="제목을 입력해주세요.")
	@Size(min = 2, message = "제목은 두 글자 이상 입력하세요.")
	private String subject;
	private String content;
	private String email;
	private List<MultipartFile> file;
	private String fileSetId;
	
}
