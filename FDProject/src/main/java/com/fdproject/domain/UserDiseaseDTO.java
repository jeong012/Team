package com.fdproject.domain;

import lombok.Data;

@Data
public class UserDiseaseDTO {

	/** 번호 (PK) */
	private int userDiseaseNo;
	
	/** 질병 번호 */
	private int diseaseNo;
	
	/** 사용자 아이디 */
	private String userId;
	
}
