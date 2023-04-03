
--
-- Table structure for table `performances`
--

DROP TABLE IF EXISTS `performances`;
CREATE TABLE `performances` (
  `pid` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `genre` varchar(255) NOT NULL,
  `sid` int DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `sid` (`sid`),
  CONSTRAINT `performances_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `shows` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `performances`
--

LOCK TABLES `performances` WRITE;
INSERT INTO `performances` VALUES (1,'Armin van Buuren','EDM',2),(2,'Tiesto','EDM',2),(3,'Martin Garrix','EDM',1),(4,'Imagine Dragons','Pop rock',1),(5,'The Weeknd','R&B',1),(6,'David Guetta','EDM',3),(7,'Steve Aoki','EDM',3);
UNLOCK TABLES;

--
-- Table structure for table `shows`
--

DROP TABLE IF EXISTS `shows`;
CREATE TABLE `shows` (
  `sid` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `date_time` datetime NOT NULL,
  `max_tickets` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `shows`
--

LOCK TABLES `shows` WRITE;
INSERT INTO `shows` VALUES (1,'Summer Nights','2023-07-21 19:00:00',14995,400.00),(2,'Electric Dreams','2023-07-23 20:00:00',19991,150.00),(3,'Music Magic','2023-07-22 21:00:00',39990,300.00);
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
CREATE TABLE `tickets` (
  `tid` int NOT NULL,
  `sid` int NOT NULL,
  `nrSeats` int NOT NULL,
  PRIMARY KEY (`tid`),
  KEY `sid` (`sid`),
  CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `shows` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
INSERT INTO `tickets` VALUES (1,2,6),(2,3,10),(3,1,5),(4,2,2);
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `uid` int NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES (1,'admin','YWRtaW4=','admin'),(2,'cashier','Y2FzaGllcg==','cashier'),(3,'Michael','Y2FzaGllcg==','cashier');
UNLOCK TABLES;
