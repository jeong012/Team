package com.fdproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	@GetMapping(value="/joinForm")
	public String getJoinForm(){
		return "user/joinForm";
	}

	@PostMapping(value="/joinForm2")
	public String getJoinForm2(){
		return "user/joinForm2";
	}
	
	@GetMapping(value="/loginForm")
	public String getLoginForm(){
		return "user/loginForm";
	}

}
