package com.fdproject.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.fdproject.domain.UserDTO;

import lombok.RequiredArgsConstructor;

@Mapper
public interface UserMapper {
	
//	public static final SqlSession sqlSession = null;
	
	boolean insertUser(UserDTO users); //회원정보 저장
	
	UserDTO selectUserDetail(String UserId); //UserId로 찾기
	
	int updateUser(UserDTO users); //회원정보 수정
	
	int deleteUser(Long UserId); //회원정보 삭제
	
}
