package com.fdproject.domain;

import lombok.Data;

@Data
public class ReceipesCartDTO {

	/** 번호 (PK) */
	private int receipeCartNo;
	
	/** 레시피 번호 */
	private int receipeNo;
	
	/** 사용자 아이디 */
	private String userId;
	
}
