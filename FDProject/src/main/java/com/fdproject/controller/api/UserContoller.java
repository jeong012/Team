package com.fdproject.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fdproject.dto.JSONResult;
import com.fdproject.service.UserService;

@Controller("userAPIController")
@RequestMapping("/user/api")
public class UserContoller {
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkId")
	public JSONResult checkId(@RequestParam(value="userId",required=true,defaultValue = "")String userId) {
		Boolean exist = userService.existId(userId);
		return JSONResult.success(exist);
	}
}
