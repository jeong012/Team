package com.fdproject.domain;

import lombok.Data;

@Data
public class UserDrugDTO {

	/** 번호 (PK) */
	private int userDiseaseNo;
	
	/** 약 번호 */
	private int drugNo;
	
	/** 사용자 아이디 */
	private String userId;
	
}
