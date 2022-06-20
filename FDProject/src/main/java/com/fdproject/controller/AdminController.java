package com.fdproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fdproject.security.Auth;

@Auth(role = Auth.Role.ADMIN)
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping({"","/main"})
	public String main() {
		return "admin/main";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
	
	public String site() {
		return "";
	}

}
