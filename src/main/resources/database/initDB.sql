DROP TABLE IF EXISTS `todo`;
CREATE TABLE `todo` (
  `id` bigint NOT NULL,
  `completed` bit(1) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `finish` varchar(255) DEFAULT NULL,
  `start` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `todo_seq`;
CREATE TABLE `todo_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user_seq`;
CREATE TABLE `user_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user_todo_list`;
CREATE TABLE `user_todo_list` (
  `user_id` bigint NOT NULL,
  `todo_list_id` bigint NOT NULL,
  UNIQUE KEY `UK_s5nqi5ytliiatincrlhrsx8gi` (`todo_list_id`),
  KEY `FK7pa4vv5hsvm6x5ntn9n7cr2ed` (`user_id`),
  CONSTRAINT `FK7pa4vv5hsvm6x5ntn9n7cr2ed` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKbue0jgxaa1vbfs3map8npvbq9` FOREIGN KEY (`todo_list_id`) REFERENCES `todo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

