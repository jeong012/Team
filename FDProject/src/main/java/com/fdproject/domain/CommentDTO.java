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
	
	/** 참조 댓글 */
	private int ref;

	/** 댓글 위치 */
	private int pos;

	/** 댓글 깊이 */
	private int depth;	

	/** 작성일자 */
	private String regDate;

	/** 삭제여부 */
	private String deleteYn;
}
