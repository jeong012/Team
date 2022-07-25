package com.fdproject.mapper;

import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.DrugsCartDTO;
import com.fdproject.domain.UserDrugDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DrugMapper {

    List<DrugDTO> drugList(DrugDTO drugDTO);

    List<String> selectKeywords(UserDrugDTO userDrugDTO);

    int selectDrugTotalCount(DrugDTO drugDTO);
    
    List<DrugDTO> housedrugList(DrugDTO drugDTO);

    DrugDTO getDrugDetail(int drugNo);

    List<DrugDTO> getMyDrugList(DrugDTO drugDTO);

    int addCart(DrugsCartDTO cartDTO);

    int deleteCart(DrugsCartDTO cartDTO);

    DrugsCartDTO getMyDrug(int drugNo);

    List<DrugsCartDTO> myDrugList(String id);

    int selectMyDrugTotalCount(DrugDTO drugDTO);

    List<String> getSearchKeyword(String keyword);

    List<String> getNameKeyword(String keyword);

    /** 회원가입 - 약 리스트 조회 사용*/
    List<DrugDTO> getJoinDrugList();

	List<UserDrugDTO> getUserDrug(UserDrugDTO userDrug);
	
	List<DrugDTO> userdrugList(DrugDTO params);
	
	/** 마이페이지 - 약 리스트 조회 (사용자가 복용중인 약 제외) */
	List<DrugDTO> getMyPageDrugList(String userId);

    /** 마이페이지 - 사용자가 복용중인 약 조회 */
	List<DrugDTO> getUserDrugList(String userId);
	
	/** 회원 관리 - 사용자 복용중인 약 조회 */
	List<Map<String,Object>> getUserDrugByAdmin(int userNo);
    
}
