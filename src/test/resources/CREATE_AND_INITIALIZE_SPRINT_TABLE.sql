DROP TABLE IF EXISTS `sprint`;
CREATE TABLE IF NOT EXISTS `sprint` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `refined_SP` json DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;

INSERT INTO `sprint` (`id`, `sprint`, `refined_SP`, `updated`) VALUES
	(1, 'Sprint 2', '{"BASIC": 259, "FUTURE": 0, "ADVANCED": 8, "COMMERCIAL": 0}', '2020-05-11 16:20:57'),
	(2, 'Sprint 3', '{"BASIC": 242, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '2020-05-11 16:24:28'),
	(3, 'Sprint 4', '{"BASIC": 281, "FUTURE": 0, "ADVANCED": 3, "COMMERCIAL": 0}', '2020-05-11 16:28:00'),
	(4, 'Sprint 5', '{"BASIC": 286, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '2020-05-11 16:31:13');
