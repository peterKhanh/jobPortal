-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.44-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for springbootdb
CREATE DATABASE IF NOT EXISTS `springbootdb` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `springbootdb`;

-- Dumping structure for table springbootdb.categories
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `category_status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table springbootdb.categories: ~10 rows (approximately)
DELETE FROM `categories`;
INSERT INTO `categories` (`id`, `category_name`, `category_status`) VALUES
	(5, 'Vagetablesh', b'1'),
	(6, 'Breads', b'1'),
	(7, 'Meats', b'1'),
	(8, 'Peter', b'1'),
	(9, 'Computer', b'1'),
	(10, 'Khoai tây', b'1'),
	(12, 'Fruits', b'1'),
	(16, 'Công nhân', b'1'),
	(21, 'Vietnam', b'1'),
	(28, 'trên xe', b'1');

-- Dumping structure for table springbootdb.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- Dumping data for table springbootdb.users: ~8 rows (approximately)
DELETE FROM `users`;
INSERT INTO `users` (`id`, `address`, `email`, `enabled`, `fullname`, `gender`, `password`, `telephone`, `username`) VALUES
	(1, 'Ha Noi', 'abc@email.com', b'1', 'Admin', b'1', '$2a$10$dwSr42e4uHN2rk3H8G1xOuEb3pQrO1ECWAkXXWwA8czoKBhieXVo2', '89655', 'Admin'),
	(7, NULL, 'dfdfdf@dd.com', NULL, NULL, NULL, 'matkhau', NULL, 'Tên '),
	(8, NULL, 'khanhnv1980@gmail.com', NULL, NULL, NULL, '$2b$10$PuU9tSXcOzDfVYmAT.bfR.3.Z57BP3FTnO7XoeEtTS/VKqrr4UTx2', NULL, 'ptk'),
	(9, NULL, 'yuanfei0704@126.com', NULL, NULL, NULL, '$2b$10$S.2UfTZ2mWf9bT1QzCaA..5ExwIWi07wpn4gla.Un/JOuYa71GJim', NULL, 'ttttt'),
	(10, NULL, 'dfdfdf@dd.com', NULL, NULL, NULL, '$2b$10$3YwOQWLnZ1U4qZPLs.ticu7BmjlDR5CJr.pK645OM1WyMogf5TTjK', NULL, 'ptk'),
	(11, NULL, 'sales.04@asahi-plating.com.vn', NULL, NULL, NULL, '$2b$10$TJ6pGGTAoPayoG33yvA49e7weD09HCRTi4pZOgDGIOIXXCz/DUXsq', NULL, 'ptk'),
	(12, NULL, 'dfdfdf@dd.com', NULL, NULL, NULL, '$2b$10$EXyWPNCj6EE2zF.JW5irWunjQG30NWfQwxTXIBSYTTenaat5/vrq.', NULL, 'ptk'),
	(13, NULL, '', NULL, NULL, NULL, '$2b$10$080A9JY/udnXtPJzFFVZlecaAlRsH1rK1QjguwinJKN5epb4HfdBW', NULL, 'ptk');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
