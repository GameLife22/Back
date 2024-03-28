CREATE SCHEMA IF NOT EXISTS gamelife;

DROP TABLE IF EXISTS gamelife.glutilisateur CASCADE;
DROP TABLE IF EXISTS gamelife.glimage CASCADE;
DROP TABLE IF EXISTS gamelife.glproduit_revendeur CASCADE;
DROP TABLE IF EXISTS gamelife.glitem_commande CASCADE;
DROP TABLE IF EXISTS gamelife.glproduit CASCADE;
DROP TABLE IF EXISTS gamelife.glcommande CASCADE;

CREATE TABLE gamelife.glutilisateur
(
    id                   SERIAL PRIMARY KEY,
    nom                  VARCHAR(50)          NOT NULL,
    prenom               VARCHAR(50)          NOT NULL,
    mdp                  VARCHAR(80)          NOT NULL,
    email                VARCHAR(80)          NOT NULL UNIQUE,
    num_rue              INT                  NOT NULL,
    rue                  VARCHAR(255)         NOT NULL,
    ville                VARCHAR(80)          NOT NULL,
    code_postal          INT,
    role                 VARCHAR(50)          NOT NULL,
    num_siren            CHAR(9) NULL DEFAULT NULL UNIQUE,
    etat_compte          BOOLEAN DEFAULT TRUE NOT NULL,
    reset_password_token VARCHAR(30) NULL
);

CREATE TABLE gamelife.glcommande
(
    id                    SERIAL PRIMARY KEY,
    id_utilisateur        INT          NOT NULL,
    etat                  VARCHAR(80)  NOT NULL,
    num_rue_livraison     INT          NOT NULL,
    rue_livraison         VARCHAR(255) NOT NULL,
    ville_livraison       VARCHAR(80)  NOT NULL,
    code_postal_livraison INT,
    date                  DATE         NOT NULL,
    FOREIGN KEY (id_utilisateur) REFERENCES gamelife.glutilisateur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glproduit
(
    id          SERIAL PRIMARY KEY,
    nom         VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    categorie   VARCHAR(50)  NOT NULL,
    plateforme  VARCHAR(50)  NOT NULL,
    etat        BOOLEAN      NOT NULL
);

CREATE TABLE gamelife.glimage
(
    id         SERIAL PRIMARY KEY,
    image      TEXT   NOT NULL,
    titre      TEXT   NOT NULL,
    id_produit INT    NOT NULL,
    FOREIGN KEY (id_produit) REFERENCES gamelife.glproduit (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glproduit_revendeur
(
    id             SERIAL PRIMARY KEY,
    stock          INT            NOT NULL,
    prix           DECIMAL(10, 0) NOT NULL,
    etat           VARCHAR(20)    NOT NULL,
    id_produit     INT            NOT NULL,
    id_utilisateur INT            NOT NULL,
    FOREIGN KEY (id_produit) REFERENCES gamelife.glproduit (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_utilisateur) REFERENCES gamelife.glutilisateur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glitem_commande
(
    id SERIAL PRIMARY KEY ,
    id_commande          INT NOT NULL,
    id_produit_revendeur INT NOT NULL,
    quantite             INT NOT NULL,
    FOREIGN KEY (id_commande) REFERENCES gamelife.glcommande (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_produit_revendeur) REFERENCES gamelife.glproduit_revendeur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Insertion dans la table glutilisateur
-- -----------------------------------------------------
INSERT INTO gamelife.glutilisateur (nom, prenom, mdp, email, num_rue, rue, ville, code_postal, role, num_siren, etat_compte, reset_password_token)
VALUES ('admin1', 'admin1', '$2y$10$cllVSeF0YfWJqP69iZ0QHObAqWgj0teCZRmCjXVFSvIDbKpxb4e4u', 'admin1@fr', 2, 'rue de capucine', 'paris', 75000, 'ROLE_ADMIN', null, true, null);

INSERT INTO gamelife.glutilisateur (nom, prenom, mdp, email, num_rue, rue, ville, code_postal, role, num_siren, etat_compte, reset_password_token)
VALUES ('smith', 'albert', '$2y$10$Be8wPp9hlL8MTMAzpGddt.LDhjww9HQq9WgxVqmrL8wXduVKIYuZO', 'revendeur1@fr', 2, 'rue du marechal', 'nantes', 44000, 'ROLE_REVENDEUR', 325987418, true, null);

INSERT INTO gamelife.glutilisateur (nom, prenom, mdp, email, num_rue, rue, ville, code_postal, role, num_siren, etat_compte, reset_password_token)
VALUES ('dupont', 'emmanuelle', '$2y$10$YOzoCt/pBb64uE/OOH3T.eeBrXqa/ftnkj8XpnIDc964.AYex6pEa', 'acheteur1@fr', 3, 'rue dupont', 'lille', 59000, 'ROLE_ACHETEUR', null, true, null);

INSERT INTO gamelife.glutilisateur (nom, prenom, mdp, email, num_rue, rue, ville, code_postal, role, num_siren, etat_compte, reset_password_token)
VALUES ('dubois', 'henry', '$2y$10$KZTksWKizskKPwZLaW6FWejCcEF1AdsYzVM8jxmHsot3Jypu0ZUti', 'acheteur2@fr', 3, 'rue dupont', 'lille', 59000, 'ROLE_ACHETEUR', null, false, null);

-- -----------------------------------------------------
-- Insertion dans la table glcommande
-- -----------------------------------------------------
INSERT INTO gamelife.glcommande (id_utilisateur, etat, num_rue_livraison, rue_livraison, ville_livraison, code_postal_livraison, date)
VALUES (2, 'NOUVELLE', 3, 'rue dupont', 'lille', 59000, '2022-11-07');

INSERT INTO gamelife.glcommande (id_utilisateur, etat, num_rue_livraison, rue_livraison, ville_livraison, code_postal_livraison, date)
VALUES (2, 'NOUVELLE', 3, 'rue dupont', 'lille', 59000,'2022-11-08');

-- -----------------------------------------------------
-- Insertion dans la table glproduit
-- -----------------------------------------------------
INSERT INTO gamelife.glproduit (nom, description, categorie, plateforme, etat)
VALUES ('fifa2023', 'description', 'sport', 'ps5', true);

INSERT INTO gamelife.glproduit (nom, description, categorie, plateforme, etat)
VALUES ('call of duty', 'description', 'guerre', 'ps5', true);

INSERT INTO gamelife.glproduit (nom, description, categorie, plateforme, etat)
VALUES ('gta', 'description', 'gta like', 'ps5', true);

INSERT INTO gamelife.glproduit (nom, description, categorie, plateforme, etat)
VALUES ('god of war', 'description', 'aventure', 'ps5', true);

INSERT INTO gamelife.glproduit (nom, description, categorie, plateforme, etat)
VALUES ('Cyberpunk 2077', 'description', 'aventure', 'pc', true);

-- -----------------------------------------------------
-- Insertion dans la table glproduit_revendeur
-- -----------------------------------------------------
INSERT INTO gamelife.glimage (image, titre, id_produit)
VALUES ('data:image/jpeg;base64,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', 'test', 1);

INSERT INTO gamelife.glimage (image, titre, id_produit)
VALUES ('test', 'test', 2);

INSERT INTO gamelife.glimage (image, titre, id_produit)
VALUES ('test', 'test', 3);

INSERT INTO gamelife.glimage (image, titre, id_produit)
VALUES ('test', 'test', 4);

INSERT INTO gamelife.glimage (image, titre, id_produit)
VALUES ('test', 'test', 5);

-- -----------------------------------------------------
-- Insertion dans la table glproduit_revendeur
-- -----------------------------------------------------
INSERT INTO gamelife.glproduit_revendeur (stock, prix, id_produit, id_utilisateur, etat)
VALUES (10, 100, 1, 1, 'Neuf avec étiquette');

-- -----------------------------------------------------
-- Insertion dans la table glitem_commande
-- -----------------------------------------------------
INSERT INTO gamelife.glitem_commande (id_commande, id_produit_revendeur, quantite)
VALUES (1, 1, 1);