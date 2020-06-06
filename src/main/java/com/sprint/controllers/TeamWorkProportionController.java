package com.sprint.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sprint.repository.impl.TeamWorkProportionDAO;

@Controller
public class TeamWorkProportionController {

	private static final String TEAM_TABLE_PREFIX = "team_";

	private TeamWorkProportionDAO team;

	@Autowired
	public TeamWorkProportionController(TeamWorkProportionDAO team) {
		this.team = team;
	}
}
