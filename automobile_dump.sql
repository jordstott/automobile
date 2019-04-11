-- MySQL dump 10.14  Distrib 5.5.29-MariaDB, for Win64 (x86)
--
-- Host: localhost    Database: automobile
-- ------------------------------------------------------
-- Server version	5.5.29-MariaDB

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
-- Current Database: `automobile`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `automobile` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `automobile`;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `COMMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(50) DEFAULT NULL,
  `TEXT` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`COMMENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'GoodUser1','Hello! I love this website, its so easy to rent cars!'),(2,'TrollUser1','HIHIHIH I LOVE CHEESE');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `USERNAME` varchar(20) NOT NULL,
  `CUSTOMER_NAME` varchar(30) NOT NULL,
  `PASSWORD` varchar(16) NOT NULL,
  `MOBILE_NUMBER` varchar(12) NOT NULL,
  `EMAIL` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES ('amcquaide4','Alonzo McQuaide','CFoD1IJMD','06069166708','amcquaide4@theatlantic.com'),('hcurnucke1','Honor Curnucke','HADMaQP5l44','03998482610','hcurnucke1@goo.gl'),('igiblin3','Issie Giblin','r8VKEy','05558969834','igiblin3@indiegogo.com'),('Test','Test User','test1','02035449972','kgodfery0@tripadvisor.com'),('wlyndon2','Wallace Lyndon','lJnsr8','04989449763','wlyndon2@npr.org');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `records`
--

DROP TABLE IF EXISTS `records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `records` (
  `RECORD_ID` int(11) NOT NULL AUTO_INCREMENT,
  `VEHICLE_ID` int(11) NOT NULL,
  `DATE_SERVICED` date NOT NULL,
  `MOT` date NOT NULL,
  `TAX` varchar(10) NOT NULL,
  PRIMARY KEY (`RECORD_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `records`
--

LOCK TABLES `records` WRITE;
/*!40000 ALTER TABLE `records` DISABLE KEYS */;
INSERT INTO `records` VALUES (1,1,'2017-02-18','2017-03-17','£351.32'),(2,2,'2017-02-16','2017-01-26','£412.60'),(3,3,'2018-06-22','2017-02-10','£402.76'),(4,4,'2017-01-11','2017-03-13','£58.00'),(5,5,'2017-02-04','2018-01-03','£126.70');
/*!40000 ALTER TABLE `records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rents`
--

DROP TABLE IF EXISTS `rents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rents` (
  `RENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `VEHICLE_ID` int(11) NOT NULL,
  `DATE` date NOT NULL,
  `USERNAME` varchar(20) NOT NULL,
  PRIMARY KEY (`RENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rents`
--

LOCK TABLES `rents` WRITE;
/*!40000 ALTER TABLE `rents` DISABLE KEYS */;
INSERT INTO `rents` VALUES (1,1,'2018-03-26','hcurnucke1'),(2,2,'2018-03-26','wlyndon2');
/*!40000 ALTER TABLE `rents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicles` (
  `VEHICLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BRAND` varchar(20) NOT NULL,
  `VEHICLE_TYPE` varchar(20) NOT NULL,
  `AVAILABILITY` tinyint(1) NOT NULL,
  `INSURANCE` varchar(10) NOT NULL,
  PRIMARY KEY (`VEHICLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
/*!40000 ALTER TABLE `vehicles` DISABLE KEYS */;
INSERT INTO `vehicles` VALUES (1,'Volkswagen','rio',0,'£137.64'),(2,'Subaru','Impreza',0,'£265.14'),(3,'Chevrolet','Equinox',1,'£155.38'),(4,'Eagle','Summit',1,'£269.87'),(5,'Porsche','Cayman',1,'£132.53'),(6,'GMC','2500',1,'£121.15'),(7,'Acura','MDX',1,'£283.51'),(8,'Saturn','Relay',1,'£215.76'),(9,'Lexus','IS F',1,'£402.48'),(10,'Acura','ZDX',1,'£149.24'),(11,'Pontiac','Montana',1,'£379.76'),(12,'Ford','Ranger',1,'£369.36'),(13,'GMC','Savana 2500',1,'£387.47'),(14,'Honda','Civic',1,'£272.85'),(15,'Hyundai','Accent',1,'£138.67'),(16,'Chrysler','LHS',1,'£236.99'),(17,'Pontiac','LeMans',1,'£455.90'),(18,'GMC','Vandura G2500',1,'£426.27'),(19,'Chevrolet','Express 3500',1,'£115.82'),(20,'Mercury','Tracer',1,'£480.39');
/*!40000 ALTER TABLE `vehicles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-26  9:37:31
