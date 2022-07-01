package com.fdproject.service;

import javax.servlet.http.HttpSession;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fdproject.configuration.OAuthAttributes;
import com.fdproject.domain.OAuth2UserDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    
    private final HttpSession httpSession;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
    	
    	OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 키가 되는 필드값 (=Primary Key)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        
        // 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        String name = attributes.getName();
        String userId = attributes.getUserId();
        String pw = attributes.getPw();

        String birthYear = attributes.getBirthYear();
        String birthMonth = null;
        String birthDay = attributes.getBirthDay();
        if (birthDay != null) {
            if(birthDay.contains("-")) {
            	birthDay = birthDay.replace("-", "");
            }	
            
            if(birthDay != null) {
            	birthMonth = birthDay.substring(0, 2);
            	birthDay = birthDay.substring(2);
            }
        }
        
        String phoneNumber = attributes.getPhoneNumber();
        if(phoneNumber != null) {
            phoneNumber = phoneNumber.replace("-", "");	
        }
        
        String sex = attributes.getSex();
        if(sex != null) {
            if(sex.equals("F") || sex.equals("female")) {
            	sex = "여자";
            } else if(sex.equals("M") || sex.equals("male")) {
            	sex = "남자";
            }
        }
        
        OAuth2UserDTO oAuth2UserDTO = (OAuth2UserDTO) httpSession.getAttribute("oAuth2User");
        oAuth2UserDTO.setUserId(userId);
        oAuth2UserDTO.setPw(pw);
        oAuth2UserDTO.setName(name);
        oAuth2UserDTO.setPhoneNumber(phoneNumber);
        oAuth2UserDTO.setSex(sex);
        oAuth2UserDTO.setBirthYear(birthYear);
        oAuth2UserDTO.setBirthMonth(birthMonth);
        oAuth2UserDTO.setBirthDay(birthDay);
        oAuth2UserDTO.setRegistrationId(registrationId);

        httpSession.setAttribute("oAuth2User", oAuth2UserDTO);

        return oAuth2User;
    }
}
