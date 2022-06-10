package com.fdproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="drug")
public class DrugController {

	@GetMapping(value="/list")
	public String getDrugList(){
		return "drug/list";
	}
	
	@GetMapping(value="/view")
	public String getDrug(){
		return "drug/view";
	}
	
	@GetMapping(value="/find_pharmacy")
	public String getFindPharmacy(){
		return "drug/find_pharmacy";
	}
}
