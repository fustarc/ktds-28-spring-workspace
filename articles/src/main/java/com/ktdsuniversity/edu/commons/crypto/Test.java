package com.ktdsuniversity.edu.commons.crypto;

import com.ktdsuniversity.edu.commons.crypto.encrypt.hash.SHA;

public class Test {
	
	private static final String AES_SECRET_KEY = "ABCDE12345ABCDE12345ABCDE12345AB";
	
	public static void testSHA() {
		String rawPassword = "1qaz2wsx";
		
		// SHA 를 이용한 이중 암호화
		// 1.이중암호화를 위한 SALT 발급
		String salt = "033c5b2d07afd38d"; //SHA.generateSalt();
		System.out.println(salt);
		
		// 2.rawPassword 와 SALT 를 위한 암호화	
		String encryptedPassword = SHA.getEncrypt(rawPassword, salt);
		System.out.println(encryptedPassword);
	}
	
	public static void testAESEnc() {
		// AES 암호화
		String name = "이름";
		String encryptedName = AES.encode(AES_SECRET_KEY, name);
		System.out.println(encryptedName);
		
		
		
	}
	
	public static void testAESDec() {
		// AES 복호화
		String encryptedName = "a3a85d6ee4673ef9a86cc17538b57cfe";
		String rawName = AES.decode(AES_SECRET_KEY, encryptedName);
		System.out.println(rawName);
	}
	
	public static void main(String[] args) {
		testSHA();
		testAESEnc();
		testAESDec();
		
	}
	
}
