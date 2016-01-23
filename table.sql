/*
Author: Sakshi Agarwal
Date: 23-01-2016
Instruction: change delimiter to $$ beforehand */


drop database if exists awesome_labs;
create database awesome_labs;
use awesome_labs;
DROP TABLE IF EXISTS tuserdetails;

CREATE TABLE IF NOT EXISTS `tuserdetails` (
  `ID` tinyint(4) NOT NULL AUTO_INCREMENT,
  `Name` text NOT NULL,
  `Address` text NOT NULL,
  `Phone` int(10) NOT NULL,
  `Email` text NOT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Phone` (`Phone`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

drop procedure if exists ak;
CREATE PROCEDURE ak
 (
 IN p_name text,
 IN p_address text,
 IN p_phone int(10),
 IN p_email text,
 IN p_date date
 )
 BEGIN
 INSERT INTO tuserdetails
 (
 Name,
 Address,
 Phone,
 Email,
 Date
 )
 VALUES
 (
 p_name,
 p_address,
 p_phone,
 p_email,
 p_date
 );
 END
 $$

call ak
 ('sakshi',
 'vitu',
 8765422,
 'sakshi018@gmail.com',
 '1996-01-26');
 $$