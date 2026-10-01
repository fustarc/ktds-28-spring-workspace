package com.ktdsuniversity.edu.members.service;

import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.commons.crypto.encrypt.hash.SHA;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MembersServiceImpl implements MembersService {
	
	private MembersDao membersDao;

	@Override
	public MembersVO createNewMember(RegistMembersVO registMembersVO) {
		
		int insertedRows = this.membersDao.insertNewMembers(registMembersVO);
		System.out.println(insertedRows + "개의 Row 생성");
				
		String salt = SHA.generateSalt();
		String encryptedPassword = SHA.getEncrypt(registMembersVO.getPassword(), salt);
		
		registMembersVO.setPassword(encryptedPassword);
		registMembersVO.setSalt(salt);
		
		if (insertedRows > 0) {
			return this.membersDao.searchMembersByEmail( registMembersVO.getEmail() );
		}
		
		throw new IllegalArgumentException("입력값이 유효하지 않습니다.");
	}

}
