package com.fdproject.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class OAuth2UserDTO implements Serializable {
	private String name;
    private String userId;
    private String pw;
    private String phoneNumber;
    private String sex;
    private String birthYear;
    private String birthMonth;
    private String birthDay;
    private String registrationId;
    private String pathFlag;
}
