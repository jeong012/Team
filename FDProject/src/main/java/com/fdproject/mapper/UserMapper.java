package com.fdproject.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.fdproject.domain.UserDTO;

@Mapper
public interface UserMapper {
	
	int insertUser(UserDTO users); //회원정보 저장
	
	UserDTO selectUserDetail(Long UserId); //UserId로 찾기
	
	int updateUser(UserDTO users); //회원정보 수정
	
	int deleteUser(Long UserId); //회원정보 삭제
	
}
