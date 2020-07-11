DROP TABLE IF EXISTS `team_mango`;
CREATE TABLE IF NOT EXISTS `team_mango` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `team_name` varchar(64) DEFAULT NULL,
  `team_member_count` decimal(2,0) DEFAULT '0',
  `on_begin_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `on_end_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_sp_sum` decimal(3,0) DEFAULT '0',
  `not_finished_sp_sum` decimal(3,0) DEFAULT '0',
  `to_do_sp_sum` decimal(3,0) DEFAULT '0',
  `in_progress_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_stories_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_bugs_sp_sum` decimal(3,0) DEFAULT '0',
  `time_estimation` decimal(4,0) DEFAULT '0',
  `time_planned` decimal(4,0) DEFAULT '0',
  `time_spent` decimal(4,0) DEFAULT '0',
  `not_closed_high_prior_stories` decimal(3,0) DEFAULT '0',
  `closed_high_prior_stories_success_rate` double(7,4) DEFAULT '0.0000',
  `delta_sp` double(7,4) DEFAULT '0.0000',
  `planned_sp_closed` double(7,4) DEFAULT '0.0000',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `refined_story_points` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `team_mango` (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `closed_high_prior_stories_success_rate`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `updated`, `finished_sp`, `refined_story_points`, `sprint_goals`) VALUES
	(1, 'Sprint 1', 'Mango', 8, 55, 67, 67, 0, 0, 0, 65, 2, 451, 531, 426, 0, 1.0000, 0.2182, 1.2182, '2019-03-14 00:00:00', '2019-04-03 00:00:00', '2020-06-02 08:02:37', '{"BASIC": 67, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '{"Sprint 2": "{\\"COMMERCIAL\\":0,\\"FUTURE\\":0,\\"BASIC\\":61,\\"ADVANCED\\":0}", "Sprint 3": "{\\"COMMERCIAL\\":0,\\"FUTURE\\":0,\\"BASIC\\":43,\\"ADVANCED\\":0}", "Sprint 4": "{\\"COMMERCIAL\\":0,\\"FUTURE\\":0,\\"BASIC\\":54,\\"ADVANCED\\":3}", "Sprint 5": "{\\"COMMERCIAL\\":0,\\"FUTURE\\":0,\\"BASIC\\":47,\\"ADVANCED\\":0}"}', '["Mango goal 1", "Mango goal 2", "Mango goal 3", "Mango goal 4", "Mango goal 5", "Mango goal 6", "Mango goal 7"]'),
	(2, 'Sprint 2', 'Mango', 8, 32, 61, 56, 5, 5, 0, 56, 0, 168, 384, 318, 0, -1.0000, 0.9062, 1.7500, '2019-04-04 00:00:00', '2019-04-24 00:00:00', '2020-06-02 08:05:25', '{"BASIC": 56, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '{"Sprint 3": "{\\"BASIC\\":43,\\"COMMERCIAL\\":0,\\"ADVANCED\\":0,\\"FUTURE\\":0}", "Sprint 4": "{\\"BASIC\\":54,\\"COMMERCIAL\\":0,\\"ADVANCED\\":3,\\"FUTURE\\":0}", "Sprint 5": "{\\"BASIC\\":47,\\"COMMERCIAL\\":0,\\"ADVANCED\\":0,\\"FUTURE\\":0}", "Sprint 6": "{\\"BASIC\\":46,\\"COMMERCIAL\\":0,\\"ADVANCED\\":0,\\"FUTURE\\":0}"}', '["Mango goal 8", "Mango goal 9", "Mango goal 10", "Mango goal 11", "Mango goal 12"]');

DROP TABLE IF EXISTS `team_apple`;
CREATE TABLE IF NOT EXISTS `team_apple` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `team_name` varchar(64) DEFAULT NULL,
  `team_member_count` decimal(2,0) DEFAULT '0',
  `on_begin_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `on_end_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_sp_sum` decimal(3,0) DEFAULT '0',
  `not_finished_sp_sum` decimal(3,0) DEFAULT '0',
  `to_do_sp_sum` decimal(3,0) DEFAULT '0',
  `in_progress_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_stories_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_bugs_sp_sum` decimal(3,0) DEFAULT '0',
  `time_estimation` decimal(4,0) DEFAULT '0',
  `time_planned` decimal(4,0) DEFAULT '0',
  `time_spent` decimal(4,0) DEFAULT '0',
  `not_closed_high_prior_stories` decimal(3,0) DEFAULT '0',
  `closed_high_prior_stories_success_rate` double(7,4) DEFAULT '0.0000',
  `delta_sp` double(7,4) DEFAULT '0.0000',
  `planned_sp_closed` double(7,4) DEFAULT '0.0000',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `refined_story_points` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `team_apple` (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `closed_high_prior_stories_success_rate`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `updated`, `finished_sp`, `refined_story_points`, `sprint_goals`) VALUES
	(1, 'Sprint 1', 'Apple', 11, 91, 108, 54, 54, 54, 0, 53, 1, 559, 644, 936, 0, 1.0000, 0.1868, 0.5934, '2019-03-14 00:00:00', '2019-04-03 00:00:00', '2020-06-02 08:02:37', '{"BASIC": 54, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '{"Sprint 2": "{\\"COMMERCIAL\\":0,\\"FUTURE\\":0,\\"BASIC\\":103,\\"ADVANCED\\":8}", "Sprint 3": "{\\"COMMERCIAL\\":0,\\"FUTURE\\":0,\\"BASIC\\":79,\\"ADVANCED\\":0}", "Sprint 4": "{\\"COMMERCIAL\\":0,\\"FUTURE\\":0,\\"BASIC\\":61,\\"ADVANCED\\":0}", "Sprint 5": "{\\"COMMERCIAL\\":0,\\"FUTURE\\":0,\\"BASIC\\":68,\\"ADVANCED\\":0}"}', '[""]'),
	(2, 'Sprint 2', 'Apple', 10, 83, 111, 78, 33, 33, 0, 77, 1, 547, 732, 1059, 0, -1.0000, 0.3373, 0.9398, '2019-04-04 00:00:00', '2019-04-24 00:00:00', '2020-06-02 08:05:25', '{"BASIC": 70, "FUTURE": 0, "ADVANCED": 8, "COMMERCIAL": 0}', '{"Sprint 3": "{\\"BASIC\\":79,\\"COMMERCIAL\\":0,\\"ADVANCED\\":0,\\"FUTURE\\":0}", "Sprint 4": "{\\"BASIC\\":61,\\"COMMERCIAL\\":0,\\"ADVANCED\\":0,\\"FUTURE\\":0}", "Sprint 5": "{\\"BASIC\\":68,\\"COMMERCIAL\\":0,\\"ADVANCED\\":0,\\"FUTURE\\":0}", "Sprint 6": "{\\"BASIC\\":53,\\"COMMERCIAL\\":0,\\"ADVANCED\\":0,\\"FUTURE\\":0}"}', '["Apple goal 1", "Apple goal 2", "Apple goal 3", "Apple goal 4"]');

