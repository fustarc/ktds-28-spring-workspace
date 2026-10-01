package com.ktdsuniversity.edu.members.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.members.service.MembersService;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

import jakarta.validation.Valid;

@RestController
public class MembersController {

	private MembersService membersService;
	
	@PostMapping("/members")
	public ApiResponse<MembersVO> createNewMember(
					@Valid @RequestBody RegistMembersVO registMembersVO,
					BindingResult validationResults
			) {
		System.out.println(validationResults);
		
		if(validationResults.hasErrors()) {
			ApiResponse.BAD_REQUEST(validationResults.getFieldErrors());
		}
		
		// 가입된 회원의 정보를 반환
		try {
			MembersVO result = this.membersService.createNewMember(registMembersVO);
			return ApiResponse.OK(result);
		} catch(IllegalArgumentException iae) {
			return ApiResponse.FORBIDDEN(iae.getMessage());
		}
		
		
	}
	
}
