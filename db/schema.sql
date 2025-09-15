
CREATE DATABASE IF NOT EXISTS `webapp_db`;

USE `webapp_db`;

-- Users table: username and BCrypt password
CREATE TABLE IF NOT EXISTS `user_login` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(60) NOT NULL, 
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_login_username` (`username`)
);

-- Personal info: first/last names
CREATE TABLE IF NOT EXISTS `user_personal_info` (
  `user_id` INT NOT NULL,
  `first_name` VARCHAR(100) NOT NULL,
  `last_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_personal_info_user`
    FOREIGN KEY (`user_id`) REFERENCES `user_login`(`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Professional info: company/salary/start date
CREATE TABLE IF NOT EXISTS `user_professional_info` (
  `user_id` INT NOT NULL,
  `company_name` VARCHAR(255) NOT NULL,
  `salary` DECIMAL(15,2) NOT NULL,
  `start_date` DATE NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_professional_info_user`
    FOREIGN KEY (`user_id`) REFERENCES `user_login`(`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
