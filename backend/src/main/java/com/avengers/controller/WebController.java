package com.avengers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
	
	@RequestMapping ("/auditApp")
	public String getIndexPage() {
		return "forward:index.html";
	}

}
