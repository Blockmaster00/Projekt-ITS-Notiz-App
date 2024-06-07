-- MySQL dump 10.13  Distrib 5.5.41, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: notizen
-- ------------------------------------------------------
-- Server version	5.5.41-0ubuntu0.12.04.1
UNLOCK TABLES;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `notizen`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `notizen` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `notizen`;

--
-- Table structure for table `notiz`
--

DROP TABLE IF EXISTS `notiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Notiz` (
  `Ueberschrift` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Beschreibung` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
     Notiz_ID integer auto_increment,PRIMARY KEY (`Notiz_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- CREATE TABLE Notiz2(NotizID Integer AUTO_INCREMENT, primary key (NotizID)); von felix

LOCK TABLES `notiz` WRITE;
/*!40000 ALTER TABLE `notiz` DISABLE KEYS */;
INSERT INTO `notiz` VALUES ("Test Notiz",'Diese Notiz ist ein Test',1);
INSERT INTO `notiz` VALUES ("Test Notiz2",'Diese Notiz ist vielleicht ein Test',2);
/*!40000 ALTER TABLE `notiz` ENABLE KEYS */;
UNLOCK TABLES;
