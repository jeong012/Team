package com.fdproject.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fdproject.domain.OAuth2UserDTO;
import com.fdproject.domain.UserDTO;
import com.fdproject.domain.UserDiseaseDTO;
import com.fdproject.domain.UserDrugDTO;
import com.fdproject.mapper.UserMapper;
import com.fdproject.paging.PaginationInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
	@Override
    @Transactional
	public int joinUser(UserDTO userDto, ArrayList<UserDiseaseDTO> userDiseaseList, ArrayList<UserDrugDTO> userDrugList) {
    	int isInserted = 0;
    	
    	/** 회원가입 - 사용자 정보 추가*/
		String encodePw = passwordEncoder.encode(userDto.getPw());
		userDto.setPw(encodePw);
    	isInserted = userMapper.saveUser(userDto);
    	
    	/** 회원가입 - 사용자 지병 데이터 추가*/
    	for (UserDiseaseDTO userDiseaseDTO : userDiseaseList) {
            isInserted = userMapper.insertUserDisease(userDiseaseDTO);
    	}

        /** 회원가입 - 사용자 복용중인 약 데이터 추가*/
    	for (UserDrugDTO userDrugDTO : userDrugList) {
	        isInserted = userMapper.insertUserDrug(userDrugDTO);
    	}
    	
		return isInserted;
	}

	/** OAuth2 기존 회원 여부 조회*/
	@Override
	public UserDTO findByOAuth2User(OAuth2UserDTO oAuth2UserDTO) {
		UserDTO user = userMapper.findByOAuth2User(oAuth2UserDTO);
		return user;
	}

	/** ID 중복체크*/
	@Override
	public int findById(String userId) {
		int cnt = userMapper.findById(userId);
		return cnt;
	}

	@Override
	public UserDTO loadUserByUsername(String userId) throws UsernameNotFoundException {
		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession httpSession = servletRequestAttribute.getRequest().getSession(true);
		OAuth2UserDTO oAuth2User = (OAuth2UserDTO) httpSession.getAttribute("oAuth2User");

		UserDTO principal = new UserDTO();
		if(oAuth2User == null) {
			principal = userMapper.findByUser(userId);
		} else {
			principal = userMapper.loginByOAuth2(userId, oAuth2User.getRegistrationId());
		}
		
		if(principal == null) {
			throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + userId);
		}
		return principal;
	}

	@Transactional
	public int updateUser(UserDTO userDTO, ArrayList<UserDiseaseDTO> userDiseaseList, ArrayList<UserDrugDTO> userDrugList) {
        int isUpdated = 0;
        int isDeleted = 0;
        int isInserted = 0;
        
        /** 사용자 정보 업데이트 */
		isUpdated = userMapper.updateUser(userDTO);
		
        /** 사용자의 기존 앓고있는 지병 데이터 삭제 */
		isDeleted = userMapper.deleteUserDisease(userDTO.getUserId());

        /** 새로운 앓고있는 지병 데이터 추가 */
		for (UserDiseaseDTO userDiseaseDTO : userDiseaseList) {
            isInserted = userMapper.insertUserDisease(userDiseaseDTO);
    	}
		
        /** 사용자의 기존 앓고있는 약 데이터 삭제 */
    	isDeleted = userMapper.deleteUserDrug(userDTO.getUserId());
        
    	/** 새로운 복용중인 약 데이터 추가 */
    	for (UserDrugDTO userDrugDTO : userDrugList) {
	        isInserted = userMapper.insertUserDrug(userDrugDTO);
    	}
		
		return isUpdated + isDeleted + isInserted;
	}

	/** 회원 정보 수정 - 비밀번호 확인 */
	@Override
	public int checkPw(String id, String pw) {
		int count = userMapper.checkPw(id, passwordEncoder.encode(pw));
		return count;
	}
	
	/** 회원 리스트 */
	public List<UserDTO> getUserList(UserDTO params){
		int userTotalCount = userMapper.getTotalCount();

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(userTotalCount);
		params.setPaginationInfo(paginationInfo);
		
		List<UserDTO> userList = userMapper.getUserList(params);
		
		return userList;
	}
	
	/** 회원 정보 가져오기 */
	@Override
	public UserDTO getUserDetail(int userNo) {
		UserDTO userDTO = userMapper.getUserDetail(userNo);
		return userDTO;
	}

	/** 회원 삭제 */		
	@Override
	public int deleteUser(int userNo) {
		int isDeleted = userMapper.deleteUser(userNo);
		return isDeleted;
	}
}
