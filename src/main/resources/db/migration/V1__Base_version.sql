-- CREATE DATABASE  IF NOT EXISTS `test`;
-- USE `test`;


-- DROP TABLE IF EXISTS `user`;
CREATE TABLE `user2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `password2` varchar(45),
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;



-- LOCK TABLES `user` WRITE;
-- INSERT INTO `user` VALUES (1,'1','1'),(2,'2','1'),(4,'23','345345'),(5,'234','324'),(6,'2234','234'),(8,'23234','345'),(15,'y9y9p','['),(16,'34234','23423'),(17,'trololo','loef'),(18,'terg','erg');
-- UNLOCK TABLES;