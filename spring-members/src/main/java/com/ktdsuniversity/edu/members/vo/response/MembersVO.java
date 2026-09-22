package com.ktdsuniversity.edu.members.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembersVO {

	private String email;
	private String name;
	private String nickname;
	private String password;
	private String registDate;
	private String modifyDate;
	private String latestLoginSucsessDate;
	private String latestLoginFailDate;
	private String latestLogoutDate;
	private long loginFailCount;
	private String loginBlockYn;
	private String loginBlockDate;
	private String loginYn;
	private String sAlt;
	private String delYn;
	
}	