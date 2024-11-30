-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_number` int(11) DEFAULT NULL,
  `account_status` enum('active','inactive') DEFAULT NULL,
  `account_type` enum('checking','credit','saving') DEFAULT NULL,
  `balance` decimal(38,2) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `customer_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `UK66gkcp94endmotfwb8r4ocxm9` (`account_number`),
  KEY `FKnnwpo0lfq4xai1rs6887sx02k` (`customer_id`),
  CONSTRAINT `FKnnwpo0lfq4xai1rs6887sx02k` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,2001,'active','saving',4934.20,'2024-11-12',4),(2,2002,'active','saving',5000.00,'2024-11-12',4),(3,3001,'active','saving',2065.80,'2024-11-13',1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_status` enum('active','inactive') DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `UKdwk6cx0afu8bs9o4t536v1j5v` (`email`),
  UNIQUE KEY `UKrosd2guvs3i1agkplv5n8vu82` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'active','2024-11-12','2020-11-12','mony@gmail.com','Mony Dev','09919393'),(4,'active','2024-11-12','2020-11-12','mony2@gmail.com','Mony Dev2','02919393'),(5,'active','2024-11-12','2020-10-12','mony3@gmail.com','Mony Dev3','02919394');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `message_en` varchar(255) DEFAULT NULL,
  `message_kh` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'400 BAD_REQUEST','Default message','Bad Req to Server','ស្នើរសុំមិនត្រឹមត្រូវ'),(2,'500 INTERNAL_SERVER_ERROR','Default message','Internal Server.','ខាងក្រៅ​សេវើរ'),(3,'404 NOT_FOUND','Another default message','Not found ID','មិនមានទិន្ទ័យ');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recent_transaction`
--

DROP TABLE IF EXISTS `recent_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recent_transaction` (
  `recent_transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `access_timestamp` datetime(6) DEFAULT NULL,
  `transaction_id` int(11) NOT NULL,
  PRIMARY KEY (`recent_transaction_id`),
  KEY `FK7y8ahkuea5jjv2vaaxgy5fb20` (`transaction_id`),
  CONSTRAINT `FK7y8ahkuea5jjv2vaaxgy5fb20` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`transaction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recent_transaction`
--

LOCK TABLES `recent_transaction` WRITE;
/*!40000 ALTER TABLE `recent_transaction` DISABLE KEYS */;
INSERT INTO `recent_transaction` VALUES (1,'2024-11-12 16:04:58.000000',3),(2,'2024-11-12 16:05:01.000000',3),(3,'2024-11-12 16:05:02.000000',3),(4,'2024-11-12 16:08:44.000000',13),(5,'2024-11-12 16:08:46.000000',13),(6,'2024-11-12 16:08:46.000000',13),(7,'2024-11-12 16:08:47.000000',13);
/*!40000 ALTER TABLE `recent_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `search_transaction`
--

DROP TABLE IF EXISTS `search_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `search_transaction` (
  `search_transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_amount` decimal(38,2) DEFAULT NULL,
  `transaction_date` date DEFAULT NULL,
  `account_id` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL,
  `transaction_type_id` int(11) NOT NULL,
  PRIMARY KEY (`search_transaction_id`),
  UNIQUE KEY `UK1fpx46nixet70blnflnmmc867` (`transaction_id`),
  KEY `FKicj9i7ejhosjbm3rlde7dfqwn` (`account_id`),
  KEY `FKf895p4bjpvxsxjn01wtnrhad1` (`transaction_type_id`),
  CONSTRAINT `FKf895p4bjpvxsxjn01wtnrhad1` FOREIGN KEY (`transaction_type_id`) REFERENCES `transaction_type` (`transaction_type_id`),
  CONSTRAINT `FKicj9i7ejhosjbm3rlde7dfqwn` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `FKr9ut1103fl3btg42aicgq6b6` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`transaction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `search_transaction`
--

LOCK TABLES `search_transaction` WRITE;
/*!40000 ALTER TABLE `search_transaction` DISABLE KEYS */;
INSERT INTO `search_transaction` VALUES (1,100.00,'2024-11-12',2,1,1),(2,200.00,'2024-11-12',2,2,2),(3,250.00,'2024-11-12',2,3,4),(4,250.00,'2024-11-12',2,4,2),(5,250.00,'2024-11-12',2,5,2),(6,250.00,'2024-11-12',2,6,1),(7,250.00,'2024-11-12',2,7,1),(8,250.00,'2024-11-12',2,8,3),(9,250.00,'2024-11-12',2,9,3),(10,250.00,'2024-11-12',1,10,3),(11,250.00,'2024-11-12',1,11,3),(12,450.00,'2024-11-12',1,12,3),(13,50.00,'2024-11-12',1,13,2);
/*!40000 ALTER TABLE `search_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) DEFAULT NULL,
  `transaction_amount` decimal(38,2) DEFAULT NULL,
  `transaction_date` date DEFAULT NULL,
  `transaction_status` enum('completed','failed','pending') DEFAULT NULL,
  `transaction_type_id` int(11) NOT NULL,
  `to_transaction_account_account_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `FKnl0vpl01y6vu03hkpi4xupugo` (`transaction_type_id`),
  KEY `FK3tbrwax0emq3vlxn9bjp9f7pq` (`to_transaction_account_account_number`),
  CONSTRAINT `FK3tbrwax0emq3vlxn9bjp9f7pq` FOREIGN KEY (`to_transaction_account_account_number`) REFERENCES `account` (`account_number`),
  CONSTRAINT `FKnl0vpl01y6vu03hkpi4xupugo` FOREIGN KEY (`transaction_type_id`) REFERENCES `transaction_type` (`transaction_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,2,100.00,'2024-11-12','completed',1,3001),(2,2,200.00,'2024-11-12','completed',2,3001),(3,2,250.00,'2024-11-12','completed',4,3001),(4,2,250.00,'2024-11-12','completed',2,3001),(5,2,250.00,'2024-11-12','completed',2,3001),(6,2,250.00,'2024-11-12','completed',1,3001),(7,2,250.00,'2024-11-12','completed',1,3001),(8,2,250.00,'2024-11-12','completed',3,3001),(9,2,250.00,'2024-11-12','completed',3,3001),(10,1,250.00,'2024-11-12','completed',3,3001),(11,1,250.00,'2024-11-12','completed',3,3001),(12,1,450.00,'2024-11-12','completed',3,3001),(13,1,50.00,'2024-11-12','completed',2,3001),(14,1,65.80,'2024-11-13','pending',4,3001),(15,1,65.80,'2024-11-13','pending',4,3001),(16,3,65.80,'2024-11-13','pending',3,2001);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_type`
--

DROP TABLE IF EXISTS `transaction_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_type` (
  `transaction_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_at` date DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `transaction_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transaction_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_type`
--

LOCK TABLES `transaction_type` WRITE;
/*!40000 ALTER TABLE `transaction_type` DISABLE KEYS */;
INSERT INTO `transaction_type` VALUES (1,'2024-11-12','Hello Deposit','deposit'),(2,'2024-11-12','Hello Withdrawal ','Withdrawal '),(3,'2024-11-12','Hello Refund ','Refund '),(4,'2024-11-12','Hello Payment ','Payment ');
/*!40000 ALTER TABLE `transaction_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'demo'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-13 16:25:03
