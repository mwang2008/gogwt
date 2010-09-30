CREATE TABLE `g_property` (
  `id` mediumint(9) NOT NULL auto_increment,
  `name` varchar(100) NOT NULL,
  `property_description` varchar(2000) default NULL,
  `address` varchar(100) NOT NULL,
  `address2` varchar(100) default NULL,
  `city` varchar(40) NOT NULL,
  `state` varchar(40) default NULL,
  `zip_code` varchar(10) default NULL,
  `country` varchar(40) default NULL,
  `lat` decimal(10,6) default NULL,
  `lng` decimal(10,6) default NULL,
  `NumberOfRooms` varchar(10) default NULL,
  `NumberOfSuites` varchar(10) default NULL,
  `NumberOfFloors` varchar(10) default NULL,
  `CheckInTime` varchar(20) default NULL,
  `CheckOutTime` varchar(20) default NULL,
  `HasTennisCourt` varchar(5) default NULL,
  `HasPetsAllowed` varchar(5) default NULL,
  `HasIndoorPool` varchar(5) default NULL,
  `HasOutdoorPool` varchar(5) default NULL,
  `HasKitchen` varchar(5) default NULL,
  PRIMARY KEY  (`id`)
)


CREATE TABLE `g_reservation` (
  `id` mediumint(9) NOT NULL auto_increment,
  `property_id` int(11) default NULL,
  `first_name` varchar(10) NOT NULL,
  `last_name` varchar(10) NOT NULL,
  `address` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `zip` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `language_id` varchar(10) NOT NULL,
  `country_id` varchar(10) NOT NULL,
  `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
)

CREATE TABLE `g_state` (
  `id` mediumint(9) NOT NULL auto_increment,
  `state` varchar(12) NOT NULL default '',
  `languageId` varchar(5) NOT NULL default '',
  `state_name` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
)

CREATE TABLE `g_keyword` (
  `keyword` varchar(50) NOT NULL default '',
  `lat` decimal(10,6) default NULL,
  `lng` decimal(10,6) default NULL,
  `searchkey` varchar(50) NOT NULL,
  `type` varchar(5) NOT NULL,
   PRIMARY KEY  (`keyword`)
)
CREATE INDEX id_index ON g_keyword(keyword)
CREATE INDEX searchkey_index ON g_keyword(searchkey)

 

