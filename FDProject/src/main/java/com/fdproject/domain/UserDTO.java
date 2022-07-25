package com.fdproject.domain;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserDTO extends CommonDTO implements UserDetails {

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
	private String authority;
	
	/** 등록 날짜 */
	private String regDate;
	
	/** 수정 날짜 */
	private String modDate;
	
	/** 삭제 날짜 */
	private String delDate;

	//사용자의 권한을 콜렉션 형태로 반환 (클래스 자료형은 GrantedAuthority를 구현해야함)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(this.authority));
	}

	//사용자의 id를 반환 (id는 unique한 값이여야함)
	@Override
	public String getPassword() {
		return this.pw;
	}

	//사용자의 password를 반환
	@Override
	public String getUsername() {
		return this.userId;
	}

	//계정 만료 여부 반환 (true = 만료되지 않음을 의미)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정 잠금 여부 반환 (true = 잠금되지 않음을 의미)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//패스워드 만료 여부 반환 (true = 만료되지 않음을 의미)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정 사용 가능 여부 반환 (true = 사용 가능을 의미)
	@Override
	public boolean isEnabled() {
		return true;
	}

}
