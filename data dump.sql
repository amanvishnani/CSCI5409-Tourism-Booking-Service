-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema auth_service
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema auth_service
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `auth_service` DEFAULT CHARACTER SET latin1 ;
USE `auth_service` ;

-- -----------------------------------------------------
-- Table `auth_service`.`City`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auth_service`.`City` (
  `CityId` INT(11) NOT NULL AUTO_INCREMENT,
  `City` VARCHAR(50) NOT NULL,
  `Province` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`CityId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `auth_service`.`Route`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auth_service`.`Route` (
  `RouteId` INT(11) NOT NULL,
  `Source` INT(11) NOT NULL,
  `Destnation` INT(11) NOT NULL,
  `Amount` INT(11) NOT NULL,
  `Duration` INT(11) NOT NULL,
  PRIMARY KEY (`RouteId`),
  INDEX `Route_Source_idx` (`Source` ASC),
  INDEX `Route_Destination_idx` (`Destnation` ASC),
  CONSTRAINT `Route_Source`
    FOREIGN KEY (`Source`)
    REFERENCES `auth_service`.`City` (`CityId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Route_Destination`
    FOREIGN KEY (`Destnation`)
    REFERENCES `auth_service`.`City` (`CityId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `auth_service`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auth_service`.`User` (
  `UserId` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) NOT NULL,
  `Contact` VARCHAR(10) NOT NULL,
  `Email` VARCHAR(50) NOT NULL,
  `Gender` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`UserId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `auth_service`.`Bus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auth_service`.`Bus` (
  `BusNo` INT(11) NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(50) NOT NULL,
  `Capacity` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`BusNo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `auth_service`.`Jouney`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auth_service`.`Jouney` (
  `JourneyId` INT NOT NULL AUTO_INCREMENT,
  `BusNo` INT(11) NOT NULL,
  `Date` DATE NOT NULL,
  `Duration` INT NOT NULL,
  PRIMARY KEY (`JourneyId`),
  INDEX `fk_Jouney_Bus1_idx` (`BusNo` ASC),
  CONSTRAINT `fk_Jouney_Bus1`
    FOREIGN KEY (`BusNo`)
    REFERENCES `auth_service`.`Bus` (`BusNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `auth_service`.`Booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auth_service`.`Booking` (
  `BookingId` INT(11) NOT NULL AUTO_INCREMENT,
  `UserId` INT(11) NOT NULL,
  `RouteId` INT(11) NOT NULL,
  `JourneyId` INT NOT NULL,
  `ReceiptNo` VARCHAR(10) NOT NULL,
  `TransactionMode` VARCHAR(50) NOT NULL,
  `Amount` VARCHAR(50) NOT NULL,
  `Timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Jouney_Date` DATETIME NOT NULL,
  PRIMARY KEY (`BookingId`),
  INDEX `Booking_User_idx` (`UserId` ASC),
  INDEX `Booking_Route_idx` (`RouteId` ASC),
  INDEX `fk_Booking_Jouney1_idx` (`JourneyId` ASC),
  CONSTRAINT `Booking_Route`
    FOREIGN KEY (`RouteId`)
    REFERENCES `auth_service`.`Route` (`RouteId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Booking_User`
    FOREIGN KEY (`UserId`)
    REFERENCES `auth_service`.`User` (`UserId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Booking_Jouney1`
    FOREIGN KEY (`JourneyId`)
    REFERENCES `auth_service`.`Jouney` (`JourneyId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `auth_service`.`Booking_Audit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auth_service`.`Booking_Audit` (
  `JourneyId` INT NOT NULL,
  `seat_capacity` INT(11) NOT NULL,
  `seats_available` INT(11) NOT NULL,
  PRIMARY KEY (`JourneyId`),
  INDEX `fk_Booking_Audit_Jouney1_idx` (`JourneyId` ASC),
  CONSTRAINT `fk_Booking_Audit_Jouney1`
    FOREIGN KEY (`JourneyId`)
    REFERENCES `auth_service`.`Jouney` (`JourneyId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `auth_service`.`TouristLocation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auth_service`.`TouristLocation` (
  `LocationId` INT(11) NOT NULL AUTO_INCREMENT,
  `CityId` INT(11) NOT NULL,
  `Name` VARCHAR(50) NOT NULL,
  `Type` VARCHAR(50) NOT NULL,
  `ImageURL` VARCHAR(250) NOT NULL,
  `Features` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`LocationId`),
  INDEX `Location_City_idx` (`CityId` ASC),
  CONSTRAINT `Location_City`
    FOREIGN KEY (`CityId`)
    REFERENCES `auth_service`.`City` (`CityId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

##########################################################################################
insert into user values (1,"Tony Stark","9025646484","tstark@stark.com","Male");
##########################################################################################
insert into city values (1,'Halifax','Nova Scotia');
insert into city values (2,'Ottawa','Ontario');
insert into city values (3,'Vancouver','British Columbia');
insert into city values (4,'Montreal','Quebec');
##########################################################################################
insert into tourist_location values (0,1,'Point Pleasant Park','Park','null','Lake');
insert into tourist_location values (0,2,'Canadian War Museum','Museum','null','Canadian military history');
insert into tourist_location values (0,3,'Stanley Park','Park','null','Seawall');
insert into tourist_location values (0,4,'Biosphere','Park','null','Plants');
##########################################################################################
insert into route values (0,1,2);
insert into route values (0,1,3);
insert into route values (0,1,4);
##########################################################################################
insert into company values (0,"Halifax Travels","9025564121");
##########################################################################################
insert into bus values (0,"Sleeper",50);
insert into bus values (0,"Sitting",75);
insert into bus values (0,"Sleeper",90);
##########################################################################################
insert into journey values (0,1,1,1,STR_TO_DATE('05-10-2020', '%m-%d-%Y'),4,250);
insert into booking_audit values(4, 50, 10);
insert into journey values (0,1,1,2,STR_TO_DATE('05-10-2020', '%m-%d-%Y'),4,200);
insert into booking_audit values(5, 75, 0);

insert into journey values (0,2,1,1,STR_TO_DATE('06-10-2020', '%m-%d-%Y'),7,600);
insert into booking_audit values (6, 50, 2);

insert into journey values (0,3,1,3,STR_TO_DATE('07-10-2020', '%m-%d-%Y'),2,150);
insert into booking_audit values (7, 90, 40);
##########################################################################################
insert into booking values (0,1,4,"Credit Card",250,'2020-03-10 09:39:01',2);
##########################################################################################
select * from user;
select * from tourist_location;
select * from city;
select * from route;
select * from company;
select * from bus;
select * from journey;
select * from booking;
select * from booking_audit;