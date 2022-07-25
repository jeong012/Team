package com.fdproject.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.UserDiseaseDTO;
	
@Mapper
public interface DiseaseMapper {

	List<UserDiseaseDTO> getUserDisease(UserDiseaseDTO userDisease);

	List<DiseaseDTO> diseaseList(DiseaseDTO params);
	
    List<DiseaseDTO> selectDiseaseList();

    /** 회원가입 - 질병 리스트 조회 사용*/
    List<DiseaseDTO> getJoinDiseaseList();
    
    List<DiseaseDTO> selectUserDiseaseList(String userId);

	/** 마이페이지 - 질병 리스트 조회 (사용자가 앓고 있는 지병 제외) */
	List<DiseaseDTO> getMyPageDiseaseList(String userId);
	
	/** 마이페이지 - 사용자가 앓고 있는 병 조회 */
	List<DiseaseDTO> getUserDiseaseList(String userId);
	
	/** 회원 관리 - 사용자 앓고있는 지병 조회 */
	List<Map<String,Object>> getUserDiseaseByAdmin(int userNo);
}
