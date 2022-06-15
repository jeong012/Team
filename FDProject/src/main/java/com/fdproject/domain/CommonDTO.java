package com.fdproject.domain;

import java.time.LocalDateTime;

import com.fdproject.paging.Criteria;
import com.fdproject.paging.PaginationInfo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CommonDTO extends Criteria{
	
	/** 페이징 정보*/
	private PaginationInfo paginationInfo;
    
    /** 삭제 여부 */
    private String deleteYn;
    
    /** 등록일 */
    private LocalDateTime insertTime;
    
    /** 수정일 */
    private LocalDateTime updateTime;
    
    /** 삭제일 */
    private LocalDateTime deleteTime;

}