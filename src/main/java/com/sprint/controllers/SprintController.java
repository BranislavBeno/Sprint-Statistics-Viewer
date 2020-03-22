/**
 * 
 */
package com.sprint.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author benito
 *
 */
@RestController
public class SprintController {

	@RequestMapping("/")
	public String home() {
		return "Hello, I'm sprit statistics viewer!";
	}
}
