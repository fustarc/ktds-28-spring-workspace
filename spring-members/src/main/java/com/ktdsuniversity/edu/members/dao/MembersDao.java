package com.ktdsuniversity.edu.members.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.members.vo.response.MembersVO;

@Mapper
public interface MembersDao {
	
	/**
	 * 총 회원 수 반환
	 * @return
	 */
	public long getMembersCount();
	
	public List<MembersVO> getAllMembers(); 

}
