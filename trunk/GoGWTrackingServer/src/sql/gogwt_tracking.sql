CREATE TABLE `tracking_customer` (  
  `id` mediumint(9) NOT NULL auto_increment,
  `groupId` varchar(200) NOT NULL,
  `group_name` varchar(400) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `first_name` varchar(300) NOT NULL,
  `last_name` varchar(300) NOT NULL,
  `email` varchar(500) NOT NULL,
  `phone_number` varchar(50) NOT NULL,
  `password` varchar(300) NOT NULL,
  `active` tinyint(1),
  `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`),
  UNIQUE KEY tracking_customer_u1 (groupId)
);
ALTER TABLE tracking_customer AUTO_INCREMENT = 1221;
ALTER TABLE tracking_customer ADD index(groupId);

////////////////////////////////////////////////////
// change log:
// Nov 22, 2011 remove auto_increment
//`id` mediumint(9) NOT NULL auto_increment,
//  `id` varchar(100) NOT NULL,
//  PRIMARY KEY  (`id`)
//  `start_time` long NOT NULL,
PRIMARY KEY  (`groupId`,`display_name`,start_time`)  
//
CREATE TABLE `tracking_mobile` (  
  `id` INT auto_increment,
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
-- 1 to 1999 for use for local test;
-- 2000 and above for production.
//ALTER TABLE tracking_mobile AUTO_INCREMENT = 2000;   
ALTER TABLE tracking_mobile ADD INDEX(groupId,display_name);
ALTER TABLE tracking_mobile ADD INDEX(groupId,latitude);

///////////////////////////////////
// read [0:1] 0 -- new,  1 -- read 
// type [1:2] 1 -- receive(inbox), 2 -- send  
//
CREATE TABLE `tracking_sms` (  
  `id` INT auto_increment,
  `groupId` varchar(30) NOT NULL,
  `display_name` varchar(30) NOT NULL,
  `address` varchar(30) NOT NULL,  
  `read` int,
  `type` int,
  `body` varchar(1024),
  `date` long,
  `start_time` long,
  `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)    
);
//ALTER TABLE tracking_sms AUTO_INCREMENT = 2000;   
ALTER TABLE tracking_sms ADD INDEX(groupId,display_name);
    
CREATE TABLE `tracking_c2mdregistration` (
   `phone` varchar(30) NOT NULL, 
   `groupId` varchar(30) NOT NULL,
   `registrationid` varchar(1024) NOT NULL, 
   `deviceid` varchar(130) NOT NULL,
   `email` varchar(130),
   `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
   PRIMARY KEY  (`phone`)
);

////////////////////////////////////////////////
/// NOT USED
CREATE TABLE `tracking_c2mdregistration` (
   --`id` INT auto_increment,
   `phone` varchar(30) NOT NULL, 
   `registrationid` varchar(1024) NOT NULL, 
   `deviceid` varchar(130) NOT NULL,
   `email` varchar(130),
   `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
   PRIMARY KEY  (`phone`)
   --UNIQUE KEY c2md_registration (phone)
);

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
	
	