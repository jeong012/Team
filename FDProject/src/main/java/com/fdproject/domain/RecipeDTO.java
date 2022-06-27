package com.fdproject.domain;

import lombok.Data;

@Data
public class RecipeDTO extends CommonDTO {

	/** 번호 (PK) */
	private int recipeNo;
	
	/** 질병 필드명 */
	private String diseaseField;
	
	/** 제목 */
	private String title;
	
	/** 작성자 */
	private String writer;
	
	/** 재료 */
	private String foodIngredients;
	
	/** 조리방법 */
	private String step;
	
	/** 이미지 */
	private String imgFile;

	/** 작성일자 */
	private String regDate;	

	/** 조회수 */
	private int hit;	

	/** 추천수 */
	private int recommendedNumber ;	

	/** 삭제여부 */
	private String deleteYn;
	
	/** 레시피 카트 객체 */
	private RecipesCartDTO cartDTO;
}
