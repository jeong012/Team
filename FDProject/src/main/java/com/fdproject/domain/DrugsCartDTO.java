package com.fdproject.domain;

import lombok.Data;

@Data
public class DrugsCartDTO {

	/** 번호 (PK) */
	private int drugCartNo;
	
	/** 약 이름 */
	private String drugName;
	
	/** 사용자 아이디 */
	private String userId;
	
}
