CREATE DATABASE IF NOT EXISTS `sprints`

USE `sprints`;

DROP TABLE IF EXISTS `sprint`;
CREATE TABLE IF NOT EXISTS `sprint` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `refined_SP` json DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

INSERT INTO `sprint` (`id`, `sprint`, `refined_SP`, `updated`) VALUES
	(1, 'Sprint 2', '{"BASIC": 259, "FUTURE": 0, "ADVANCED": 8, "COMMERCIAL": 0}', '2020-05-11 16:20:57'),
	(2, 'Sprint 3', '{"BASIC": 242, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '2020-05-11 16:24:28'),
	(3, 'Sprint 4', '{"BASIC": 281, "FUTURE": 0, "ADVANCED": 3, "COMMERCIAL": 0}', '2020-05-11 16:28:00'),
	(4, 'Sprint 5', '{"BASIC": 286, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '2020-05-11 16:31:13'),
	(5, 'Sprint 6', '{"BASIC": 297, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '2020-05-11 16:34:21'),
	(6, 'Sprint 7', '{"BASIC": 211, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '2020-05-11 16:37:10'),
	(7, 'Sprint 8', '{"BASIC": 221, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '2020-05-11 16:40:10'),
	(8, 'Sprint 9', '{"BASIC": 200, "FUTURE": 0, "ADVANCED": 3, "COMMERCIAL": 0}', '2020-05-11 16:42:46'),
	(9, 'Sprint 10', '{"BASIC": 233, "FUTURE": 0, "ADVANCED": 6, "COMMERCIAL": 1}', '2020-05-11 16:46:04'),
	(10, 'Sprint 11', '{"BASIC": 254, "FUTURE": 0, "ADVANCED": 6, "COMMERCIAL": 0}', '2020-05-11 16:48:53'),
	(11, 'Sprint 12', '{"BASIC": 213, "FUTURE": 0, "ADVANCED": 17, "COMMERCIAL": 0}', '2020-05-11 16:51:46'),
	(12, 'Sprint 13', '{"BASIC": 275, "FUTURE": 0, "ADVANCED": 16, "COMMERCIAL": 0}', '2020-05-11 16:56:04');

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
  `delta_sp` double(7,4) DEFAULT '0.0000',
  `planned_sp_closed` double(7,4) DEFAULT '0.0000',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

INSERT INTO `team_mango` (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `updated`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 1', 'Mango', 7, 63, 63, 58, 5, 5, 0, 58, 0, 491, 491, 561, 1, 0.0000, 0.9206, '2019-04-25 00:00:00', '2019-05-15 00:00:00', '2020-05-11 16:28:00', '{"BASIC": 58, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Mango goal 1", "Mango goal 2", "Mango goal 3", "Mango goal 4", "Mango goal 5", "Mango goal 6", "Mango goal 7"]'),
	(2, 'Sprint 2', 'Mango', 8, 84, 85, 59, 26, 26, 0, 56, 3, 578, 581, 722, 3, 0.0119, 0.7024, '2019-05-16 00:00:00', '2019-06-05 00:00:00', '2020-05-11 16:31:13', '{"BASIC": 59, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Mango goal 8", "Mango goal 9", "Mango goal 10", "Mango goal 11", "Mango goal 12"]'),
	(3, 'Sprint 3', 'Mango', 8, 95, 95, 66, 29, 29, 0, 63, 3, 625, 625, 749, 1, 0.0000, 0.6947, '2019-06-06 00:00:00', '2019-06-26 00:00:00', '2020-05-11 16:34:21', '{"BASIC": 66, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Mango goal 13", "Mango goal 14", "Mango goal 15", "Mango goal 16", "Mango goal 17"]'),
	(4, 'Sprint 4', 'Mango', 10, 88, 89, 66, 23, 23, 0, 61, 5, 647, 648, 761, 3, 0.0114, 0.7500, '2019-06-27 00:00:00', '2019-07-15 00:00:00', '2020-05-11 16:37:10', '{"BASIC": 66, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Mango goal 18", "Mango goal 19", "Mango goal 20", "Mango goal 21", "Mango goal 22", "Mango goal 23"]'),
	(5, 'Sprint 5', 'Mango', 8, 43, 44, 41, 3, 3, 0, 22, 19, 378, 380, 329, 2, 0.0233, 0.9535, '2019-07-18 00:00:00', '2019-08-05 00:00:00', '2020-05-11 16:40:10', '{"BASIC": 41, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(6, 'Sprint 6', 'Mango', 8, 68, 50, 42, 8, 8, 0, 16, 26, 460, 438, 462, 0, 0.2647, 0.6176, '2019-08-08 00:00:00', '2019-09-02 00:00:00', '2020-05-11 16:42:46', '{"BASIC": 42, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Mango goal 24", "Mango goal 25", "Mango goal 26"]'),
	(7, 'Sprint 7', 'Mango', 9, 46, 48, 33, 15, 15, 0, 28, 5, 329, 339, 399, 0, 0.0435, 0.7174, '2019-09-05 00:00:00', '2019-09-16 00:00:00', '2020-05-11 16:46:04', '{"BASIC": 33, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Mango goal 27", "Mango goal 28", "Mango goal 29", "Mango goal 30", "Mango goal 31"]'),
	(8, 'Sprint 8', 'Mango', 8, 59, 63, 54, 9, 9, 0, 52, 2, 423, 451, 449, 0, 0.0678, 0.9153, '2019-09-19 00:00:00', '2019-10-07 00:00:00', '2020-05-11 16:48:53', '{"BASIC": 53, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 1}', '["Mango goal 32", "Mango goal 33"]'),
	(9, 'Sprint 9', 'Mango', 8, 81, 85, 66, 19, 19, 0, 61, 5, 530, 548, 590, 1, 0.0494, 0.8148, '2019-10-10 00:00:00', '2019-10-28 00:00:00', '2020-05-11 16:51:46', '{"BASIC": 66, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]');

DROP TABLE IF EXISTS `team_plum`;
CREATE TABLE IF NOT EXISTS `team_plum` (
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
  `delta_sp` double(7,4) DEFAULT '0.0000',
  `planned_sp_closed` double(7,4) DEFAULT '0.0000',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

INSERT INTO `team_plum` (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `updated`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 1', 'Plum', 6, 32, 32, 24, 8, 8, 0, 24, 0, 240, 240, 226, 0, 0.0000, 0.7500, '2019-04-25 00:00:00', '2019-05-15 00:00:00', '2020-05-11 16:28:00', '{"BASIC": 24, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Plum goal 1", "Plum goal 2", "Plum goal 3", "Plum goal 4", "Plum goal 5"]'),
	(2, 'Sprint 2', 'Plum', 5, 47, 47, 47, 0, 0, 0, 47, 0, 294, 294, 270, 0, 0.0000, 1.0000, '2019-05-16 00:00:00', '2019-06-05 00:00:00', '2020-05-11 16:31:13', '{"BASIC": 47, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Plum goal 6", "Plum goal 7", "Plum goal 8", "Plum goal 9", "Plum goal 10"]'),
	(3, 'Sprint 3', 'Plum', 4, 37, 37, 37, 0, 0, 0, 37, 0, 262, 266, 221, 0, 0.0000, 1.0000, '2019-06-06 00:00:00', '2019-06-26 00:00:00', '2020-05-11 16:34:21', '{"BASIC": 37, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Plum goal 11", "Plum goal 12", "Plum goal 13"]'),
	(4, 'Sprint 4', 'Plum', 5, 55, 57, 49, 8, 8, 0, 47, 2, 330, 335, 347, 0, 0.0364, 0.8909, '2019-06-27 00:00:00', '2019-07-15 00:00:00', '2020-05-11 16:37:10', '{"BASIC": 49, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Plum goal 14", "Plum goal 15", "Plum goal 16", "Plum goal 17", "Plum goal 18"]'),
	(5, 'Sprint 5', 'Plum', 4, 29, 32, 24, 8, 8, 0, 22, 2, 214, 230, 210, 0, 0.1034, 0.8276, '2019-07-18 00:00:00', '2019-08-05 00:00:00', '2020-05-11 16:40:10', '{"BASIC": 24, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Plum goal 19", "Plum goal 20"]'),
	(6, 'Sprint 6', 'Plum', 4, 45, 47, 47, 0, 0, 0, 45, 2, 326, 331, 300, 0, 0.0444, 1.0444, '2019-08-08 00:00:00', '2019-09-02 00:00:00', '2020-05-11 16:42:46', '{"BASIC": 47, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Plum goal 21"]'),
	(7, 'Sprint 7', 'Plum', 5, 43, 44, 44, 0, 0, 0, 44, 0, 314, 317, 239, 0, 0.0233, 1.0233, '2019-09-05 00:00:00', '2019-09-16 00:00:00', '2020-05-11 16:46:04', '{"BASIC": 44, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(8, 'Sprint 8', 'Plum', 4, 40, 41, 38, 3, 3, 0, 37, 1, 253, 256, 265, 0, 0.0250, 0.9500, '2019-09-19 00:00:00', '2019-10-07 00:00:00', '2020-05-11 16:48:53', '{"BASIC": 38, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(9, 'Sprint 9', 'Plum', 4, 38, 38, 38, 0, 0, 0, 37, 1, 271, 271, 243, 0, 0.0000, 1.0000, '2019-10-10 00:00:00', '2019-10-28 00:00:00', '2020-05-11 16:51:46', '{"BASIC": 38, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Plum goal 22", "Plum goal 23", "Plum goal 24", "Plum goal 25", "Plum goal 26"]');
	
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
  `delta_sp` double(7,4) DEFAULT '0.0000',
  `planned_sp_closed` double(7,4) DEFAULT '0.0000',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

INSERT INTO `team_apple` (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `updated`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 1', 'Apple', 8, 80, 79, 53, 26, 26, 0, 51, 2, 593, 585, 801, 0, 0.0125, 0.6625, '2019-04-25 00:00:00', '2019-05-15 00:00:00', '2020-05-11 16:28:00', '{"BASIC": 53, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(2, 'Sprint 2', 'Apple', 8, 61, 61, 51, 10, 10, 0, 43, 8, 483, 483, 684, 0, 0.0000, 0.8361, '2019-05-16 00:00:00', '2019-06-05 00:00:00', '2020-05-11 16:31:13', '{"BASIC": 51, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apple goal 1", "Apple goal 2", "Apple goal 3", "Apple goal 4"]'),
	(3, 'Sprint 3', 'Apple', 9, 66, 68, 47, 21, 21, 0, 43, 4, 529, 534, 688, 0, 0.0303, 0.7121, '2019-06-06 00:00:00', '2019-06-26 00:00:00', '2020-05-11 16:34:21', '{"BASIC": 47, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apple goal 5", "Apple goal 6", "Apple goal 7", "Apple goal 8"]'),
	(4, 'Sprint 4', 'Apple', 7, 48, 53, 41, 12, 12, 0, 30, 11, 339, 358, 518, 0, 0.1042, 0.8542, '2019-06-27 00:00:00', '2019-07-15 00:00:00', '2020-05-11 16:37:10', '{"BASIC": 41, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apple goal 9", "Apple goal 10", "Apple goal 11", "Apple goal 12"]'),
	(5, 'Sprint 5', 'Apple', 8, 61, 67, 33, 34, 34, 0, 22, 11, 536, 544, 777, 0, 0.0984, 0.5410, '2019-07-18 00:00:00', '2019-08-05 00:00:00', '2020-05-11 16:40:10', '{"BASIC": 33, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apple goal 13", "Apple goal 14", "Apple goal 15", "Apple goal 16", "Apple goal 17"]'),
	(6, 'Sprint 6', 'Apple', 8, 41, 47, 40, 7, 7, 0, 38, 2, 339, 387, 613, 0, 0.1463, 0.9756, '2019-08-08 00:00:00', '2019-09-02 00:00:00', '2020-05-11 16:42:46', '{"BASIC": 40, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apple goal 18", "Apple goal 19"]'),
	(7, 'Sprint 7', 'Apple', 8, 47, 48, 41, 7, 7, 0, 35, 6, 284, 285, 382, 0, 0.0213, 0.8723, '2019-09-05 00:00:00', '2019-09-16 00:00:00', '2020-05-11 16:46:04', '{"BASIC": 38, "FUTURE": 0, "ADVANCED": 3, "COMMERCIAL": 0}', '["Apple goal 20", "Apple goal 21", "Apple goal 22", "Apple goal 23", "Apple goal 24"]'),
	(8, 'Sprint 8', 'Apple', 9, 57, 63, 50, 13, 13, 0, 29, 21, 501, 556, 532, 1, 0.1053, 0.8772, '2019-09-19 00:00:00', '2019-10-07 00:00:00', '2020-05-11 16:48:53', '{"BASIC": 50, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apple goal 25", "Apple goal 26", "Apple goal 27", "Apple goal 28", "Apple goal 29", "Apple goal 30"]'),
	(9, 'Sprint 9', 'Apple', 7, 57, 57, 47, 10, 10, 0, 40, 7, 499, 499, 558, 0, 0.0000, 0.8246, '2019-10-10 00:00:00', '2019-10-28 00:00:00', '2020-05-11 16:51:46', '{"BASIC": 47, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apple goal 31", "Apple goal 32", "Apple goal 33", "Apple goal 34", "Apple goal 35", "Apple goal 36"]');
	
DROP TABLE IF EXISTS `team_apricot`;
CREATE TABLE IF NOT EXISTS `team_apricot` (
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
  `delta_sp` double(7,4) DEFAULT '0.0000',
  `planned_sp_closed` double(7,4) DEFAULT '0.0000',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

INSERT INTO `team_apricot` (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `updated`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 1', 'Apricot', 2, 17, 17, 1, 16, 16, 0, 1, 0, 84, 84, 42, 0, 0.0000, 0.0588, '2019-04-25 00:00:00', '2019-05-15 00:00:00', '2020-05-11 16:28:00', '{"BASIC": 1, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(2, 'Sprint 2', 'Apricot', 2, 34, 34, 29, 5, 5, 0, 29, 0, 190, 190, 93, 0, 0.0000, 0.8529, '2019-05-16 00:00:00', '2019-06-05 00:00:00', '2020-05-11 16:31:13', '{"BASIC": 29, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(3, 'Sprint 3', 'Apricot', 2, 39, 39, 0, 39, 39, 0, 0, 0, 134, 134, 210, 0, 0.0000, 0.0000, '2019-06-06 00:00:00', '2019-06-26 00:00:00', '2020-05-11 16:34:21', '{"BASIC": 0, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(4, 'Sprint 4', 'Apricot', 2, 52, 52, 0, 52, 52, 0, 0, 0, 204, 204, 220, 0, 0.0000, 0.0000, '2019-06-27 00:00:00', '2019-07-15 00:00:00', '2020-05-11 16:37:10', '{"BASIC": 0, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apricot goal 1", "Apricot goal 2", "Apricot goal 3"]'),
	(5, 'Sprint 5', 'Apricot', 2, 31, 31, 0, 31, 31, 0, 0, 0, 303, 303, 367, 0, 0.0000, 0.0000, '2019-07-18 00:00:00', '2019-08-05 00:00:00', '2020-05-11 16:40:10', '{"BASIC": 0, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apricot goal 4", "Apricot goal 5", "Apricot goal 6", "Apricot goal 7"]'),
	(6, 'Sprint 6', 'Apricot', 2, 34, 34, 0, 34, 34, 0, 0, 0, 258, 258, 303, 0, 0.0000, 0.0000, '2019-08-08 00:00:00', '2019-09-02 00:00:00', '2020-05-11 16:42:46', '{"BASIC": 0, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apricot goal 8", "Apricot goal 9", "Apricot goal 10", "Apricot goal 11"]'),
	(7, 'Sprint 7', 'Apricot', 2, 31, 31, 10, 21, 21, 0, 10, 0, 303, 303, 367, 0, 0.0000, 0.3226, '2019-09-05 00:00:00', '2019-09-16 00:00:00', '2020-05-11 16:46:04', '{"BASIC": 10, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apricot goal 12", "Apricot goal 13", "Apricot goal 14", "Apricot goal 15", "Apricot goal 16"]'),
	(8, 'Sprint 8', 'Apricot', 2, 26, 26, 0, 26, 26, 0, 0, 0, 253, 253, 282, 0, 0.0000, 0.0000, '2019-09-19 00:00:00', '2019-10-07 00:00:00', '2020-05-11 16:48:53', '{"BASIC": 0, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apricot goal 17", "Apricot goal 18", "Apricot goal 19"]'),
	(9, 'Sprint 9', 'Apricot', 2, 29, 29, 26, 3, 3, 0, 26, 0, 185, 185, 265, 0, 0.0000, 0.8966, '2019-10-10 00:00:00', '2019-10-28 00:00:00', '2020-05-11 16:51:46', '{"BASIC": 26, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Apricot goal 20", "Apricot goal 21", "Apricot goal 22", "Apricot goal 23"]');
	
DROP TABLE IF EXISTS `team_strawberry`;
CREATE TABLE IF NOT EXISTS `team_strawberry` (
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
  `delta_sp` double(7,4) DEFAULT '0.0000',
  `planned_sp_closed` double(7,4) DEFAULT '0.0000',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

INSERT INTO `team_strawberry` (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `updated`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 1', 'Strawberry', 6, 36, 43, 43, 0, 0, 0, 41, 2, 224, 276, 228, 0, 0.1944, 1.1944, '2019-04-25 00:00:00', '2019-05-15 00:00:00', '2020-05-11 16:28:00', '{"BASIC": 43, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Strawberry goal 1", "Strawberry goal 2", "Strawberry goal 3", "Strawberry goal 4"]'),
	(2, 'Sprint 2', 'Strawberry', 6, 49, 57, 57, 0, 0, 0, 55, 2, 346, 382, 311, 0, 0.1633, 1.1633, '2019-05-16 00:00:00', '2019-06-05 00:00:00', '2020-05-11 16:31:13', '{"BASIC": 54, "FUTURE": 0, "ADVANCED": 3, "COMMERCIAL": 0}', '["Strawberry goal 5", "Strawberry goal 6", "Strawberry goal 7"]'),
	(3, 'Sprint 3', 'Strawberry', 6, 33, 47, 47, 0, 0, 0, 35, 12, 193, 226, 171, 0, 0.4242, 1.4242, '2019-06-06 00:00:00', '2019-06-26 00:00:00', '2020-05-11 16:34:20', '{"BASIC": 47, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Strawberry goal 8", "Strawberry goal 9", "Strawberry goal 10"]'),
	(4, 'Sprint 4', 'Strawberry', 5, 45, 46, 42, 4, 1, 3, 40, 2, 246, 247, 215, 0, 0.0222, 1.0222, '2019-06-27 00:00:00', '2019-07-15 00:00:00', '2020-05-11 16:37:10', '{"BASIC": 46, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Strawberry goal 11", "Strawberry goal 12", "Strawberry goal 13", "Strawberry goal 14", "Strawberry goal 15", "Strawberry goal 16"]'),
	(5, 'Sprint 5', 'Strawberry', 5, 33, 37, 35, 2, 2, 0, 17, 18, 205, 233, 210, 0, 0.1212, 1.0606, '2019-07-18 00:00:00', '2019-08-05 00:00:00', '2020-05-11 16:40:10', '{"BASIC": 35, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Strawberry goal 17", "Strawberry goal 18", "Strawberry goal 19"]'),
	(6, 'Sprint 6', 'Strawberry', 5, 29, 41, 41, 0, 0, 0, 30, 11, 157, 195, 174, 0, 0.4138, 1.4138, '2019-08-08 00:00:00', '2019-09-02 00:00:00', '2020-05-11 16:42:45', '{"BASIC": 41, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Strawberry goal 20", "Strawberry goal 21", "Strawberry goal 22"]'),
	(7, 'Sprint 7', 'Strawberry', 5, 32, 32, 32, 0, 0, 0, 30, 2, 230, 230, 189, 0, 0.0000, 1.0000, '2019-09-05 00:00:00', '2019-09-16 00:00:00', '2020-05-11 16:46:04', '{"BASIC": 32, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Strawberry goal 23", "Strawberry goal 24", "Strawberry goal 25", "Strawberry goal 26", "Strawberry goal 27"]'),
	(8, 'Sprint 8', 'Strawberry', 6, 42, 47, 44, 3, 3, 0, 36, 8, 305, 322, 227, 0, 0.1190, 1.0476, '2019-09-19 00:00:00', '2019-10-07 00:00:00', '2020-05-11 16:48:53', '{"BASIC": 38, "FUTURE": 0, "ADVANCED": 6, "COMMERCIAL": 0}', '["Strawberry goal 28", "Strawberry goal 29", "Strawberry goal 30"]'),
	(9, 'Sprint 9', 'Strawberry', 6, 41, 51, 43, 8, 8, 0, 39, 4, 304, 343, 318, 0, 0.2439, 1.0488, '2019-10-10 00:00:00', '2019-10-28 00:00:00', '2020-05-11 16:51:46', '{"BASIC": 37, "FUTURE": 0, "ADVANCED": 6, "COMMERCIAL": 0}', '["Strawberry goal 31", "Strawberry goal 32", "Strawberry goal 33"]');
