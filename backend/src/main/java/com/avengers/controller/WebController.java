package com.avengers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
	
	@GetMapping ("/login")
	public String getIndexPage() {
		return "forward:index.html";
	}

}
