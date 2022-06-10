package com.fdproject.domain;

import lombok.Data;

@Data
public class UserDTO {

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
	
}
