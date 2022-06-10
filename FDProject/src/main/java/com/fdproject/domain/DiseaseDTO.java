package com.fdproject.domain;

import lombok.Data;

@Data
public class DiseaseDTO {

	/** 번호 (PK) */
	private int diseaseNo;
	
	/** 필드명 */
	private String diseaseField;
	
	/** 이름 */
	private String diseaseName;
	
	/** 설명 */
	private String description;
	
	/** 증상 */
	private String symptom;
	
}
