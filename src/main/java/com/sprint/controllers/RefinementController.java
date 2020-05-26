package com.sprint.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RefinementController {

	/** The log. */
	private static Log log = LogFactory.getLog(RefinementController.class);

	private static final String REFINEMENT_TABLE_PREFIX = "sprint";

	@GetMapping("/refinement")
	public String refinement() {
		return "refinement";
	}
}
