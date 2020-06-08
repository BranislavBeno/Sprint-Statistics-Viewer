package com.sprint.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AboutController implements ErrorController {

	@Override
    @GetMapping("/error")
    @ResponseBody
	public String getErrorPath() {
		// Error occurred
        return "Error occurred";
	}

}
