package com.fdproject.domain;

import lombok.Data;

@Data
public class CommentDTO {

	/** 번호 (PK) */
	private int commentNo;
	
	/** 레시피 번호 */
	private int recipeNo;
	
	/** 작성자 */
	private String writer;
	
	/** 내용 */
	private String content;
	
	/** 댓글 그룹 */
	private int ref;
	
	/** 부모 댓글*/
	private int parentNo;

	/** 댓글 위치 */
	private int pos;

	/** 댓글 깊이 */
	private int depth;	

	/** 작성일자 */
	private String regDate;
	
	/** 수정 날짜 */
	private String modDate;
	
	/** 삭제 날짜 */
	private String delDate;
	
	/** 삭제 메모 */
	private String delMemo;
	
	/** 작성자 이름 */
	private String name;
	
}
