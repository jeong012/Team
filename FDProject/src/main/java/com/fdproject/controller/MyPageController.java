package com.fdproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="mypage")
public class MyPageController {

	@GetMapping(value="/disease")
	public String getDiseaseList(){
		return "mypage/diseaselist";
	}
	
	@GetMapping(value="/drug")
	public String getDrugList(){
		return "mypage/druglist";
	}
}
