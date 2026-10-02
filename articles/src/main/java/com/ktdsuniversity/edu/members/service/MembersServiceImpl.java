package com.ktdsuniversity.edu.members.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktdsuniversity.edu.commons.crypto.AES;
import com.ktdsuniversity.edu.commons.crypto.encrypt.hash.SHA;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.request.LoginMemberVO;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MembersServiceImpl implements MembersService {
	
	@Value("${app.encrypt.aes.key}")
	private String aesSecretKey;
	
	private final MembersDao membersDao;

	@Override
	public MembersVO createNewMember(RegistMembersVO registMembersVO) {
		
		String email = registMembersVO.getEmail();
		int emailCount = this.membersDao.selectEmailCount(email);
		if (emailCount > 0) {
			throw new IllegalArgumentException(email + "이미 사용중입니다.");
		}
		
		String nickname = registMembersVO.getNickname();
		int nicknameCount = this.membersDao.selectNicknameCount(nickname);
		if (nicknameCount > 0) {
			throw new IllegalArgumentException(nickname + "은 이미 사용중입니다.");
		}
		
		String rawName = registMembersVO.getName();
		String encryptedName = AES.encode(this.aesSecretKey, rawName);
		registMembersVO.setName(encryptedName);
		
		String rawNickname = registMembersVO.getNickname();
		String encryptedNickname = AES.encode(this.aesSecretKey, rawNickname);
		registMembersVO.setNickname(encryptedNickname);
		
		String rawPassword = registMembersVO.getPassword();
		String salt = SHA.generateSalt();
		String encryptedPassword = SHA.getEncrypt(rawPassword, salt);
		
		registMembersVO.setSalt(salt);
		registMembersVO.setPassword(encryptedPassword);
		
		int insertCount = this.membersDao.insertNewMembers(registMembersVO);
		if (insertCount == 0) {
			throw new IllegalArgumentException("회원가입을 할 수 없습니다. 다시 시도해주세요");
		}
		
		MembersVO newMember = this.membersDao.selectMembersByEmail(email);
		newMember.setName( AES.decode(this.aesSecretKey, newMember.getName()) );
		newMember.setNickname( AES.decode(this.aesSecretKey, newMember.getNickname()) );
		return newMember;
	}

	@Override
	public MembersVO readMember(LoginMemberVO loginMemberVO) {
		MembersVO membersVO = this.membersDao.selectMembersByEmail(loginMemberVO.getEmail());
		
		if (membersVO == null) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
		}
		
		if (membersVO.getLoginBlockYn().equals("Y")) {
			// 차단 계정
			
			// 차단 된 후 1시간이 지났는가?
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime loginBlockDate = LocalDateTime.parse( membersVO.getLoginBlockDate(), formatter );
			loginBlockDate = loginBlockDate.plusHours(1);
		
			if (now.equals(loginBlockDate) || now.isAfter(loginBlockDate)) {
				// 차단 후 1 시간 경과
				// 로그인 실패횟수 0으로 초기화 & 차단 여부 N으로 수정
				
				int updateRows = this.membersDao.updateResetBlock( loginMemberVO.getEmail() );
				System.out.println(updateRows + "건이 블락 해제됨");
			} else {
				throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
			
			}	
		}
		
		// 활성 계정
		// 사용자 slat 필요
		// 로그인 요청 비밀번호 필요
		// 암호화
		String rawPassword = loginMemberVO.getPassword();
		String storeSalt = membersVO.getSalt();
		String encryptedPassword = SHA.getEncrypt(rawPassword, storeSalt);
		
		if (encryptedPassword.equals(membersVO.getPassword() )) {
			// 비밀번호 일치함
			
			int updateRows = this.membersDao.updateLoginStatus(membersVO.getEmail());
			if (updateRows == 0) {
				throw new IllegalArgumentException("로그인에 실패했습니다. 잠시 후 다시 시도해주세요.");
			}
			
			MembersVO loggedMember = this.membersDao.selectMembersByEmail(membersVO.getEmail());
			loggedMember.setName( AES.decode(this.aesSecretKey, loggedMember.getName()) );
			loggedMember.setNickname( AES.decode(this.aesSecretKey, loggedMember.getNickname()) );
			
			return loggedMember;
		}
		
		// 비밀번호 불일치
		int updateRows = this.membersDao.updateLoginFailed( membersVO.getEmail() );
		System.out.println(membersVO.getEmail() + "로그인 실패");
		
		int blockUpdateRows = this.membersDao.updateBlock( membersVO.getEmail() );
		if (blockUpdateRows > 0) {
			// 계정이 차단됨
			throw new IllegalArgumentException("로그인 실패 횟수가 누적되어 계정이 차단되었습니다. 1시간 후 재시도 해주세요.");
		} else {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
		}		
	}
	
	public String updateLogoutStatus(String email) {
		int updatedRows = this.membersDao.updateLogoutStatus(email);
		if (updatedRows == 0 ) {
			throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
		}
		
		return null;
	}

	@Override
	public String deleteMember(String email, String password) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpSession session = request.getSession();
		MembersVO loggedMember = (MembersVO) session.getAttribute("__LOGIN_USER__");
		
		int updatedRows = this.membersDao.updateLoginStatus(email);
		if (updatedRows == 0 ) {
			throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
		}
		
		if(! loggedMember.getPassword().equals(password)) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		
		return null;
	}

}
