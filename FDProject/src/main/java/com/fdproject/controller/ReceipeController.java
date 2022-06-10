package com.fdproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/receipe")
public class ReceipeController {
	
	@GetMapping(value="/list")
	public String getReceipeList(){
		return "receipe/list";
	}

	@GetMapping(value="/view")
	public String getReceipe(){
		return "receipe/view";
	}

	@GetMapping(value="/writeForm")
	public String getReceipeForm(){
		return "receipe/writeForm";
	}
}
