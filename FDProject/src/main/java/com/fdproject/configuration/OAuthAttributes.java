package com.fdproject.configuration;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String userId;
    private String pw;
    private String name;
    private String phoneNumber;
    private String sex;
    private String birthYear;
    private String birthDay;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String userId, String pw,
                           String name, String phoneNumber, String sex,
                           String birthYear, String birthDay){
    	
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.userId = userId;
        this.pw = pw;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.birthYear = birthYear;
        this.birthDay = birthDay;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes){

        if("naver".equals(registrationId)){
        	return ofNaver("id", attributes);
        } else if ("kakao".equals(registrationId)) {
        	return ofKakao("id", attributes);
        } else {
        	return ofGoogle(userNameAttributeName, attributes);
        }
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes){
    	
    	return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .userId((String) attributes.get("email"))
                .pw((String) attributes.get("sub"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName,
                                           Map<String, Object> attributes){

    	Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .userId((String) response.get("email"))
                .pw((String) response.get("id"))
                .name((String) response.get("name"))
                .phoneNumber((String) response.get("mobile"))
                .sex((String) response.get("gender"))
                .birthYear((String) response.get("birthyear"))
                .birthDay((String) response.get("birthday"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName,
                                           Map<String, Object> attributes){
    	
    	String pw = String.valueOf(attributes.get("id"));
        
    	Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        String userId = (String) kakao_account.get("email");
        
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");
        String name = (String) profile.get("nickname");
        String sex = (String) kakao_account.get("gender");
        String birthday = (String) kakao_account.get("birthday");

        return OAuthAttributes.builder()
                .userId(userId)
                .pw(pw)
                .name(name)
                .sex(sex)
                .birthDay(birthday)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
}
