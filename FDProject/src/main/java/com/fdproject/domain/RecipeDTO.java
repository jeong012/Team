package com.fdproject.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RecipeDTO extends CommonDTO {

	/** 번호 (PK) */
	private int recipeNo;
	
	/** 질병 필드명 */
	private String diseaseField;
	
	// 접근 쉽게 하려고 만든 변수
	private String diseaseName;
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
	
	private String storage;
	
	private String tip;
	
	private String randomImgFile;
	
	private MultipartFile file;
		
}
