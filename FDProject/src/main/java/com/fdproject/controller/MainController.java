package com.fdproject.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fdproject.domain.UserDTO;

@Controller
public class MainController {

	@GetMapping(value="/")
	public String getIndex(HttpSession httpSession, Authentication authentication){
		if(httpSession.getAttribute("oAuth2User") != null) {
			httpSession.removeAttribute("oAuth2User");
		}
		
		if(authentication != null) {
			String authorities = authentication.getAuthorities().toString();
			if(!authorities.contains("ROLE_USER")) {
				UserDTO userDTO = (UserDTO)authentication.getPrincipal(); 
				String authority = userDTO.getAuthority();
				if(authority.equals("ROLE_ADMIN")) {
					return "admin/main";
				} else {
					return "index";	
				}
			}else {
				return "index";	
			}
		} else {
			return "index";	
		}
	}
	
	@GetMapping(value = "/err/denied-page")
	public String accessDenied(){
	    return "error/deniedPage";
	}
}
