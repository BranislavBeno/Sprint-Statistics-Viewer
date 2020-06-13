package com.sprint.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The Class SprintStatsErrorController.
 */
@Controller
public class SprintStatsErrorController implements ErrorController {

	/**
	 * Handle error.
	 *
	 * @param request the request
	 * @return the string
	 */
	@GetMapping(value = "/error")
	public String handleError(HttpServletRequest request) {

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {

			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error-404";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "error-500";
			}
		}
		return "error";
	}

	/**
	 * Gets the error path.
	 *
	 * @return the error path
	 */
	@Override
	public String getErrorPath() {
		return "/error";
	}
}
