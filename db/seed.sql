-- Seed data for hostedFtpExercice
-- Creates or updates a login user with a BCrypt password hash

USE `webapp_db`;

-- Create login user
INSERT INTO `user_login` (`username`, `password`)
VALUES ('hostedftp', '*************************************************');

-- Personal info for hostedftp
INSERT INTO `user_personal_info` (`user_id`, `first_name`, `last_name`)
SELECT `id`, 'Youssef', 'Bannouni'
FROM `user_login` WHERE `username` = 'hostedftp';

-- Professional info for hostedftp
INSERT INTO `user_professional_info` (`user_id`, `company_name`, `salary`, `start_date`)
SELECT `id`, 'Capgemini', 100000.00, '2024-01-01'
FROM `user_login` WHERE `username` = 'hostedftp';
