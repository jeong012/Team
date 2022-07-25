package com.fdproject.domain;

import lombok.Data;

@Data
public class RecipeRecommendedDTO {

	/** 번호 (PK) */
	private int recipeRecommendedNo;
	
	/** 레시피 번호*/
	private int recipeNo;
	
	/** 추천 사용자 아이디*/
	private String userId;
	
}
