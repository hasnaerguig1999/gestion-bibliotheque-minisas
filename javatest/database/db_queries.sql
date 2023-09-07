DROP DATABASE IF EXISTS mini_sas_java;

CREATE DATABASE mini_sas_java;

USE mini_sas_java;


CREATE TABLE Livre (
  isbn int(15) PRIMARY KEY AUTO_INCREMENT,
  auteur varchar(30),
  titre varchar(30),
  available int(4)
);

CREATE TABLE Membre (
  num_national int(15) PRIMARY KEY AUTO_INCREMENT,
  nom varchar(30),
  prenom varchar(30)
);

CREATE TABLE Emprunte (
 date_emprunte date,
 date_retour date,
 isbn int,
 num_national int,
 FOREIGN KEY (isbn) REFERENCES Livre(isbn),
 FOREIGN KEY (num_national) REFERENCES Membre(num_national)
);