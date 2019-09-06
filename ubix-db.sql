CREATE DATABASE  IF NOT EXISTS `ubix` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ubix`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ubix
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.30-MariaDB

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
-- Table structure for table `carello`
--

DROP TABLE IF EXISTS `carello`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carello` (
  `ID_user` int(11) NOT NULL,
  `ID_prod` int(11) NOT NULL,
  `qnt` int(11) NOT NULL DEFAULT '1',
  KEY `ID_user_idx` (`ID_user`),
  KEY `ID_prod_idx` (`ID_prod`),
  CONSTRAINT `ID_prod` FOREIGN KEY (`ID_prod`) REFERENCES `prodotto` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ID_user` FOREIGN KEY (`ID_user`) REFERENCES `utenti` (`idutenti`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carello`
--

LOCK TABLES `carello` WRITE;
/*!40000 ALTER TABLE `carello` DISABLE KEYS */;
/*!40000 ALTER TABLE `carello` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `ID` int(11) NOT NULL,
  `nome_categoria` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (0,'Anime'),(1,'Azione'),(2,'Bambini'),(3,'Commedie'),(4,'Crime'),(5,'Documentari'),(6,'Dramma'),(7,'Fantascienza & Fantasy'),(8,'Horror'),(9,'Indipendenti'),(10,'Sport'),(11,'Musical'),(12,'Stand-up'),(13,'Thriller'),(14,'Reality & Talk Show');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dati_anagrafici`
--

DROP TABLE IF EXISTS `dati_anagrafici`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dati_anagrafici` (
  `codice_fiscale` varchar(45) NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  `cap` varchar(30) DEFAULT NULL,
  `citta` varchar(45) DEFAULT NULL,
  `via` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `carta_di_credito` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`codice_fiscale`),
  CONSTRAINT `codice_fiscale` FOREIGN KEY (`codice_fiscale`) REFERENCES `utenti` (`codice_fiscale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dati_anagrafici`
--

LOCK TABLES `dati_anagrafici` WRITE;
/*!40000 ALTER TABLE `dati_anagrafici` DISABLE KEYS */;
/*!40000 ALTER TABLE `dati_anagrafici` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotto`
--

DROP TABLE IF EXISTS `prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prodotto` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `cast` varchar(45) DEFAULT NULL,
  `descrizione` varchar(300) DEFAULT NULL,
  `preview_img` varchar(100) NOT NULL,
  `src` varchar(100) NOT NULL,
  `type` tinyint(2) DEFAULT NULL COMMENT '0: film\n1: serie tv',
  `sellable` tinyint(4) DEFAULT '0' COMMENT '1 : ha la copia fisica\n0 : non ha la copia fisica',
  `highlight` tinyint(4) DEFAULT '0' COMMENT '1: in evidenza\n0: normale',
  `price` float DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` VALUES (1,'Rick & Morty','Just Roiland ','Bizzarra serie','rick&morty.jpg','RickMorty',1,0,1,0);
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utenti`
--

DROP TABLE IF EXISTS `utenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utenti` (
  `idutenti` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `lvl` int(11) NOT NULL DEFAULT '0' COMMENT '0: utenti normale\n1: admin',
  `codice_fiscale` varchar(45) NOT NULL,
  `lpayment` date DEFAULT NULL COMMENT 'Ultimo pagamento effettuato',
  PRIMARY KEY (`idutenti`),
  UNIQUE KEY `user_UNIQUE` (`user`),
  KEY `codice_fiscale_idx` (`codice_fiscale`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utenti`
--

LOCK TABLES `utenti` WRITE;
/*!40000 ALTER TABLE `utenti` DISABLE KEYS */;
/*!40000 ALTER TABLE `utenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wrap_prodotto`
--

DROP TABLE IF EXISTS `wrap_prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wrap_prodotto` (
  `ID_prod` int(11) NOT NULL,
  `ID_cat` int(11) NOT NULL,
  KEY `ID_prod_idx` (`ID_prod`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wrap_prodotto`
--

LOCK TABLES `wrap_prodotto` WRITE;
/*!40000 ALTER TABLE `wrap_prodotto` DISABLE KEYS */;
INSERT INTO `wrap_prodotto` VALUES (0,5),(0,7);
/*!40000 ALTER TABLE `wrap_prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'ubix'
--

--
-- Dumping routines for database 'ubix'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-03  2:30:36
