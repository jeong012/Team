package com.fdproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	@GetMapping(value="/joinForm.do")
	public String getJoinForm(){
		return "user/joinForm";
	}

	@PostMapping(value="/joinForm2.do")
	public String getJoinForm2(){
		return "user/joinForm2";
	}
	
	@GetMapping(value="/loginForm.do")
	public String getLoginForm(){
		return "user/loginForm";
	}
	
	@GetMapping(value="/myPage.do")
	public String getMyPage(){
		return "user/myPage";
	}

}
