package com.fdproject.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping(value="/")
	public String getIndex(HttpSession httpSession){
		if(httpSession.getAttribute("oAuth2User") != null) {
			httpSession.removeAttribute("oAuth2User");
		}
		return "index";
	}
	
}
