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
Drop database if exists notizen;
CREATE DATABASE  `notizen` ;

USE `notizen`;

--
-- Table structure for table `notiz`
--

DROP TABLE IF EXISTS `kategorie`;

CREATE TABLE `kategorie` (
	`Name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
	`Kategorie_ID` int NOT NULL auto_increment,
     PRIMARY KEY (`Kategorie_ID`)
);

INSERT INTO kategorie(Name) VALUE ("ToDo");
INSERT INTO kategorie(Name) VALUE ("inProgress");
INSERT INTO kategorie(Name) VALUE ("Done");

DROP TABLE IF EXISTS `notiz`;

CREATE TABLE `notiz` (
	`Ueberschrift` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
	`Beschreibung` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
    `Kategorie_ID` int,
    foreign key(kategorie_ID) references kategorie(Kategorie_ID),
	`Benutzer_ID`	int,
	foreign key(Benutzer_ID) references benutzer(Benutzer_ID),
	`Notiz_ID` int NOT NULL auto_increment,
     PRIMARY KEY (`Notiz_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `benutzer`;

CREATE TABLE `benutzer` (
	`BenutzerName` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
	`Password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
	`Benutzer_ID` int NOT NULL auto_increment,
     PRIMARY KEY (`Benutzer_ID`)
);


