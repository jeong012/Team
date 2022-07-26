package com.fdproject.domain;

import lombok.Data;

@Data
public class RecipesCartDTO {

	/** 번호 (PK) */
	private int recipeCartNo;
	
	/** 레시피 번호 */
	private int recipeNo;
	
	/** 사용자 아이디 */
	private String userId;
	
}
