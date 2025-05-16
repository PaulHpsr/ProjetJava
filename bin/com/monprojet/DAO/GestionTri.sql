DROP DATABASE IF EXISTS GestionTri;
CREATE DATABASE GestionTri ;
USE GestionTri ;

DROP TABLE IF EXISTS Dechet;
DROP TABLE IF EXISTS Depot;
DROP TABLE IF EXISTS PoubelleIntelligente;
DROP TABLE IF EXISTS BonAchat;
DROP TABLE IF EXISTS Menage;
DROP TABLE IF EXISTS ContratPartenariat;
DROP TABLE IF EXISTS CentreDeTri;
DROP TABLE IF EXISTS Commerce;


CREATE TABLE CentreDeTri(
	id INT PRIMARY KEY,
	nom VARCHAR(20),
	adresse VARCHAR(40)
);

CREATE TABLE Menage(
	id INT PRIMARY KEY,
	nom VARCHAR(20),
	codeAcces INT,
	pointFidel INT	
);

CREATE TABLE PoubelleIntelligente(
	id INT PRIMARY KEY,
	emplacement VARCHAR(40),
	typePoub INT,
	etat BOOLEAN,
	idCentreDeTri INT DEFAULT NULL,
	FOREIGN KEY fk_poub_centre(idCentreDeTri) REFERENCES CentreDeTri(id)
);

CREATE TABLE Depot(
	id INT PRIMARY KEY AUTO_INCREMENT,  -- Identifiant unique du dépôt
	idPoubelle INT,
	idMenage INT,
	heureDepot DATETIME NOT NULL,     -- Date et heure du dépôt
	pointsGagnes INT NOT NULL,         -- Points gagnés lors du dépôt
	FOREIGN KEY fk_dep_poub(idPoubelle) REFERENCES PoubelleIntelligente(id),
	FOREIGN KEY fk_dep_men(idMenage) REFERENCES Menage(id)
);

CREATE TABLE Dechet(
	id INT PRIMARY KEY AUTO_INCREMENT,  -- Identifiant unique du déchet
	idDepot INT DEFAULT NULL,
	typeDechet INT NOT NULL,                 -- Type de déchet (0: PLASTIQUE, 1: VERRE, etc.)
	categorie VARCHAR(10),
	poids INT NOT NULL,                 -- Poids du déchet
	FOREIGN KEY fk_dech_dep(idDepot) REFERENCES Depot(id)
);

CREATE TABLE Commerce(
	id INT PRIMARY KEY AUTO_INCREMENT,  
	nom VARCHAR(20),
	rapport FLOAT
);

CREATE TABLE ContratPartenariat(
	id INT PRIMARY KEY AUTO_INCREMENT, 
	idCommerce INT,
	idCentreDeTri INT,  
	dateDebut DATE,
	dateFin DATE,
	conditions VARCHAR(50),         
	categoriesProduits VARCHAR(50),
	FOREIGN KEY fk_contrat_com(idCommerce) REFERENCES Commerce(id),
	FOREIGN KEY fk_contrat_centre(idCentreDeTri) REFERENCES CentreDeTri(id)
);

CREATE TABLE BonAchat(
	id INT PRIMARY KEY AUTO_INCREMENT, 
	idMenage INT,
	idCommerce INT,  
	valeur FLOAT,
	dateCreation DATE,
	FOREIGN KEY fk_contrat_com(idMenage) REFERENCES Menage(id),
	FOREIGN KEY fk_contrat_centre(idCommerce) REFERENCES Commerce(id)
);

/*
INSERT INTO CentreDeTri (id, nom, adresse) VALUES
(1, 'EcoTri', '12 rue des Recyclables'),
(2, 'GreenCenter', '45 avenue Verte'),
(3, 'RecyclePlus', '78 boulevard Durable');

INSERT INTO Menage (id, nom, codeAcces, pointFidel) VALUES
(1, 'Dupont', 1234, 50),
(2, 'Legarnd', 5678, 30),
(3, 'Martin', 9012, 80);

INSERT INTO PoubelleIntelligente (id, emplacement, type, etat, idCentreDeTri) VALUES
(1, 'Rue des Lilas', 0, TRUE, 1),  -- Poubelle plastique
(2, 'Avenue des Chênes', 1, FALSE, 1), -- Poubelle verre HS
(3, 'Place du Marché', 2, TRUE, 2), -- Poubelle papier/carton
(4, 'Parc Central', 0, TRUE, 3); -- Poubelle plastique

INSERT INTO Depot (idPoubelle, idMenage, heureDepot, pointsGagnes) VALUES
(1, 1, '2024-03-22 08:30:00', 10),
(2, 2, '2024-03-22 09:15:00', 15),
(3, 3, '2024-03-22 10:45:00', 20),
(4, 1, '2024-03-22 12:00:00', 25);

INSERT INTO Dechet (idDepot, typeDechet, categorie, poids) VALUES
(1, null,'PLASTIQUE', 2),
(1, 1, 'VERRE', 4),    
(2, 2, 'CARTON', 3),     
(3, 3, 'METAL', 5),  
(4, 1, 'VERRE', 4);      
*/

SELECT * FROM CentreDeTri;
SELECT * FROM Menage;
SELECT * FROM PoubelleIntelligente;
SELECT * FROM Depot;
SELECT * FROM Dechet;
SELECT * FROM Commerce;
SELECT * FROM ContratPartenariat;
SELECT * FROM BonAchat;





