package com.fdproject.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DrugsCartDTO {

	/** 번호 (PK) */
	private int drugCartNo;
	
	/** 약 이름 */
	private int drugNo;
	
	/** 사용자 아이디 */
	private String userId;
	
}
