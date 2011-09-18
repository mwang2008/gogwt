CREATE TABLE `tracking_customer` (  
  `id` mediumint(9) NOT NULL auto_increment,
  `groupId` varchar(30) NOT NULL,
  `group_name` varchar(50) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(30) NOT NULL,
  `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`),
  UNIQUE KEY tracking_customer_u1 (groupId)
);
ALTER TABLE tracking_customer AUTO_INCREMENT = 1221;


CREATE TABLE `tracking_mobile` (  
  `id` mediumint(9) NOT NULL auto_increment,
  `groupId` varchar(30) NOT NULL,
  `phone_number` varchar(30) NOT NULL,
  `display_name` varchar(30) NOT NULL,
  `latitude` int NOT NULL,
  `longitude` int NOT NULL,
  `altitude` decimal(10,3) NOT NULL,
  `provider` varchar(25) NOT NULL,
  `accuracy` decimal(10,3) NOT NULL,
  `bearing` decimal(10,6) NOT NULL,
  `distance` decimal(10,2) NOT NULL,
  `speed` decimal(10,6) NOT NULL,
  `time` long NOT NULL,
  `start_time` long NOT NULL,
  `total_distance` decimal(10,2) NOT NULL,
  `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
);
ALTER TABLE tracking_mobile AUTO_INCREMENT = 1221;

////////////////////////////////////////////////
/// NOT USED
CREATE TABLE `mobile_login_user` (  
   `id` mediumint(9) NOT NULL auto_increment,
   `groupId` varchar(30) NOT NULL,
   `phone_number` varchar(30) NOT NULL,
   `display_name` varchar(30) NOT NULL,
   `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
   PRIMARY KEY  (`id`),
   UNIQUE KEY mobile_login_user_u1 (groupId, display_name)
);
	
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `STOCK_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `STOCK_CODE` VARCHAR(10) NOT NULL,
  `STOCK_NAME` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`STOCK_ID`) USING BTREE,
  UNIQUE KEY `UNI_STOCK_NAME` (`STOCK_NAME`),
  UNIQUE KEY `UNI_STOCK_ID` (`STOCK_CODE`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
 
 
 
CREATE TABLE `stock_daily_record` (
  `RECORD_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `PRICE_OPEN` FLOAT(6,2) DEFAULT NULL,
  `PRICE_CLOSE` FLOAT(6,2) DEFAULT NULL,
  `PRICE_CHANGE` FLOAT(6,2) DEFAULT NULL,
  `VOLUME` BIGINT(20) UNSIGNED DEFAULT NULL,
  `DATE` DATE NOT NULL,
  `STOCK_ID` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`RECORD_ID`) USING BTREE,  
  KEY `FK_STOCK_TRANSACTION_STOCK_ID` (`STOCK_ID`),
  CONSTRAINT `FK_STOCK_TRANSACTION_STOCK_ID` FOREIGN KEY (`STOCK_ID`) 
  REFERENCES `stock` (`STOCK_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
	
	