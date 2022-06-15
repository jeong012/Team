package com.fdproject.domain;

import com.fdproject.paging.Criteria;
import lombok.Data;

import java.util.List;

@Data
public class DrugDTO extends CommonDTO {

	/** 번호 (PK) */
	private int drugNo;

	/** 이름 */
	private String drugName;

	/** 제조업체 */
	private String manufacturer;
	
	/** 주성분 */
	private String basis;

	/** 효능 */
	private String effect;
	
	/** 복용 방법 */
	private String method;
	
	/** 주의사항1 */
	private String warning1;
	
	/** 주의사항2 */
	private String warning2;
	
	/** 이상반응 */
	private String sideEffect;
	
	/** 보관방법 */
	private String storage;
	
	/** 상비약 여부 */
	private String houseYn;
	
	/** 이미지 */
	private String imgFile;
	
	/** 키워드 (질병 분류) */
	private String keyword;

	/** 금지 약물 조회 조건 */
	private String params;

	/** 복용 가능 여부 */
	private String takeYn;

}
