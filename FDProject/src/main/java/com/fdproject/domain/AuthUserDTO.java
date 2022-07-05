package com.fdproject.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

@Data
public class AuthUserDTO extends User {

	/** 번호 (PK) */
	private int userNo;
	
	/** 아이디 */
	private String userId;
	
	/** 비밀번호 */
	private String pw;
	
	/** 이름 */
	private String name;
	
	/** 휴대폰 번호 */
	private String phoneNumber;
	
	/** 성별 */
	private String sex;
	
	/** 생년월일 */
	private String birthDate;
	
	/** 가입플랫폼 */
	private String registrationId;

	/** 권한 */
	private String role;
	
	
	public AuthUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.userId = username;
        this.pw = password;
	}
	
}
