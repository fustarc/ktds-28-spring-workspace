package com.ktdsuniversity.edu.members.vo.response;

import lombok.Data;

@Data
public class MembersVO {

	private String email;
	private String name;
	private String nickname;
	private String password;
	private String regist_date;
	private String modify_date;
	private String latest_login_success_date;
	private String latest_login_fail_date;
	private String latest_logout_date;
	private int login_fail_count;
	private String login_block_yn;
	private String login_block_date;
	private String login_yn;
	private String salt;
	private String del_yn;
}
