package com.fdproject.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fdproject.domain.UserDTO;
import com.fdproject.security.Auth;
import com.fdproject.security.AuthUser;
import com.fdproject.service.UserService;
import com.fdproject.util.UiUtils;

@Controller
@RequestMapping("/user")
public class UserController extends UiUtils {
	
	public UserController() {
		System.out.println("userController 생성");
	}
	
	@Autowired
	private UserService userService;

	@PostMapping(value="/joinForm.do")
	public String getJoinForm(@ModelAttribute @Valid UserDTO users, BindingResult result, Model model){
		
		//Valid 체크가 틀릴 시, join form으로 넘김
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error : list) {
				System.out.println(error);
			}
			model.addAllAttributes(result.getModel()); // Map으로 보내줌
			return "user/join";
		}
		userService.registerUser(users);
		return "user/joinForm";
	}

	@PostMapping(value="/joinForm2.do")
	public String getJoinForm2(@ModelAttribute @Valid UserDTO users, BindingResult result, Model model){
		return "user/joinForm2";
	}

	@GetMapping(value="/joinsuccess.do")
	public String getJoinsuccess(){
		return "user/joinsuccess";
	}	
	
	
	@GetMapping(value="/loginForm.do")
	public String getLoginForm(){
		return "user/loginForm";
	}
	
	@Auth
	@GetMapping(value="/myPage.do")
	public String getMyPage(@AuthUser UserDTO authUser,Model model){
		return "user/myPage";
	}
	
	@PostMapping(value="/update.do")
	public String getUpdate(){
		return "user/myPage";
	}

}
