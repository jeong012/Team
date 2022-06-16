package com.fdproject.mapper;

import com.fdproject.domain.UserDTO;

public interface UserMapper {
	
	int insertUser(UserDTO users); //회원정보 저장
	
	UserDTO selectUserDetail(Long UserId); //UserId로 찾기
//	public Optional<UserDTO> findById(Long UserId); //UserId로 찾기
	
	int updateUser(UserDTO users); //회원정보 수정
	
	int deleteUser(Long UserId); //회원정보 삭제
	
}
