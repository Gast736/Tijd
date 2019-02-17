/* als het schema al bestaat dan verwijderen, daarna weer aanmaken*/
DROP SCHEMA IF EXISTS `tijd` ;
CREATE SCHEMA `tijd`;

/* als de tabel project al bestaat dan verwijderen, daarna weer aanmaken*/
Drop table if exists `tijd`.`tblproject`;

CREATE TABLE `tijd`.`tblproject` (
  `idproject` INT(11) NOT NULL AUTO_INCREMENT,
  `naam` VARCHAR(45) NULL DEFAULT NULL,
  `categorie` VARCHAR(45) NULL DEFAULT NULL,
  `opdrachtgever` VARCHAR(45) NULL DEFAULT NULL,
  `directie` VARCHAR(45) NULL DEFAULT NULL,
  `startdatum` DATE NULL DEFAULT NULL,
  `einddatum` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`idproject`))
DEFAULT CHARACTER SET = utf8;

/* als de tabel medewerker al bestaat dan verwijderen, daarna weer aanmaken*/
Drop table if exists `tijd`.`tblmedewerker`;

CREATE TABLE `tijd`.`tblmedewerker` (
  `idmedewerker` INT(11) NOT NULL AUTO_INCREMENT,
  `naam` VARCHAR(45) NULL DEFAULT NULL,
  `wachtwoord` VARCHAR(10) NULL DEFAULT NULL,
  `team` VARCHAR(45) NULL DEFAULT NULL,
  `rol` VARCHAR(45) NULL DEFAULT NULL,
  `contracturen` decimal(4,2) NULL DEFAULT NULL,
  `startdatum` DATE NULL DEFAULT NULL,
  `einddatum` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`idmedewerker`))
DEFAULT CHARACTER SET = utf8;

/* als de tabel registratie al bestaat dan verwijderen, dan weer aanmaken
   hier wordt ook de relatie gelegd:
   1) een medewerker kan uren schrijven op meerdere projecten
   2) op een project kunnen meerdere medewerkers uren schrijven
   dus een veel-op-veel relatie waar de koppeling plaatsvindt
   via de registratie tabel*/
   
Drop table if exists `tijd`.`tblregistratie`;

CREATE TABLE `tijd`.`tblregistratie` (
  `idmedewerker` INT(11) NOT NULL,
  `idproject` INT(11) NOT NULL,
  `startdatum` DATE NOT NULL,
  `uren` decimal(4,2) NULL DEFAULT NULL,
  FOREIGN KEY (`idmedewerker`) REFERENCES `tblmedewerker` (`idmedewerker`) ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (`idproject`) REFERENCES `tblproject` (`idproject`) ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY (`idmedewerker`, `idproject`, `startdatum`))
DEFAULT CHARACTER SET = utf8;

/* vulling van tabellen*/
INSERT INTO `tijd`.tblproject(idproject, naam, categorie, opdrachtgever, directie, startdatum, einddatum) VALUES (1, "migratie cognos", "intern project", "Jeanine Vosselman", "I&S", "2018-01-01", "2019-12-31");
INSERT INTO `tijd`.tblproject(idproject, naam, categorie, opdrachtgever, directie, startdatum, einddatum) VALUES (2, "dmo indicatiewaarden", "project", "Arne Tiel Groenestegen", "DMO", "2018-01-01", "2019-12-31");
INSERT INTO `tijd`.tblproject(idproject, naam, categorie, opdrachtgever, directie, startdatum, einddatum) VALUES (3, "financiele rapportage", "project", "Willem van der Heide", "F&I", "2018-04-01", "2019-03-31");
INSERT INTO `tijd`.tblproject(idproject, naam, categorie, opdrachtgever, directie, startdatum, einddatum) VALUES (4, "verlof", "afwezigheid", "Jeanine Vosselman", "I&S", "2001-01-01", "2050-12-31");
INSERT INTO `tijd`.tblmedewerker(emailadres, naam, wachtwoord, team, rol, contracturen, startdatum, einddatum) VALUES ("erwin.reinders@groningen.nl", "Erwin", "welkom", "reporting", "afdelingshoofd", 36.00, "2009-04-01", null);
INSERT INTO `tijd`.tblmedewerker(emailadres, naam, wachtwoord, team, rol, contracturen, startdatum, einddatum) VALUES ("arjan.hegge@groningen.nl", "Arjan", "welkom", "reporting", "beheerder", 36.00, "2008-01-01", null);
INSERT INTO `tijd`.tblmedewerker(emailadres, naam, wachtwoord, team, rol, contracturen, startdatum, einddatum) VALUES ("maarten.millaard@groningen.nl", "Maarten", "welkom", "datawarehouse", "medewerker", 36.00, "1998-03-15", null);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (1 , 1, "2019-01-21", 8);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (1 , 3, "2019-01-22", 8);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (1 , 1, "2019-01-23", 4);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (1 , 4, "2019-01-23", 4);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (1 , 1, "2019-01-24", 2);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (1 , 2, "2019-01-24", 6);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (1 , 1, "2019-01-25", 8);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (2 , 3, "2019-01-21", 8);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (2 , 3, "2019-01-22", 8);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (2 , 3, "2019-01-23", 4);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (2 , 4, "2019-01-23", 4);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (2 , 3, "2019-01-24", 2);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (2 , 1, "2019-01-24", 6);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (2 , 3, "2019-01-25", 8);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (3 , 2, "2019-01-21", 8);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (3 , 2, "2019-01-22", 8);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (3 , 2, "2019-01-23", 4);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (3 , 4, "2019-01-23", 4);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (3 , 2, "2019-01-24", 2);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (3 , 1, "2019-01-24", 6);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (3 , 4, "2019-01-25", 8);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (1 , 4, "2019-02-05", 8);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (1 , 4, "2019-01-05", 8);
INSERT INTO `tijd`.tblregistratie(idmedewerker, idproject, startdatum, uren) VALUES (1 , 4, "2019-02-25", 8);

/*check op vulling registratie tabel inclusief werking primary en foreign keys*/
select r.*, p.*, m.* from `tijd`.tblregistratie r INNER JOIN 
`tijd`.tblproject p ON r.idproject = p.idproject INNER JOIN
`tijd`.tblmedewerker m ON m.idmedewerker=r.idmedewerker
WHERE m.idmedewerker<4;

