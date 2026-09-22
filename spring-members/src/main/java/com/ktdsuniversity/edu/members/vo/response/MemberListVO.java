package com.ktdsuniversity.edu.members.vo.response;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberListVO {
	
	
	private long memberCount;

	private List<MembersVO> memberList;


}
