# LIBSYS
A webbased library CRM

MYSQL CREATE STATEMENTS

CREATE DATABASE `libsys` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `admins` (
  `admin_id` varchar(100) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `authorities` (
  `authority_id` int(11) NOT NULL,
  `authority` varchar(50) NOT NULL,
  PRIMARY KEY (`authority_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bosses` (
  `boss_id` varchar(100) NOT NULL,
  PRIMARY KEY (`boss_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `librarians` (
  `librarian_id` varchar(100) NOT NULL,
  PRIMARY KEY (`librarian_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `persons` (
  `personal_number` varchar(45) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `adress` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`personal_number`),
  CONSTRAINT `personal_number` FOREIGN KEY (`personal_number`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(65) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `user_authority` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  KEY `fk_user_authority_idx` (`user_authority`),
  CONSTRAINT `fk_user_authority` FOREIGN KEY (`user_authority`) REFERENCES `authorities` (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `visitors` (
  `visitor_id` varchar(100) NOT NULL,
  PRIMARY KEY (`visitor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



