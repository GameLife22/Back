DROP TABLE IF EXISTS glutilisateur CASCADE;
DROP TABLE IF EXISTS glcommande CASCADE;
DROP TABLE IF EXISTS glproduit CASCADE;
DROP TABLE IF EXISTS glimage CASCADE;
DROP TABLE IF EXISTS glitem_commande CASCADE;
DROP TABLE IF EXISTS glproduit_revendeur CASCADE;

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
    id_produit INT          NOT NULL,
    image      TEXT         NOT NULL,
    titre      VARCHAR(255) NOT NULL,
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
    id_commande          INT NOT NULL,
    id_produit_revendeur INT NOT NULL,
    quantite             INT NOT NULL,
    FOREIGN KEY (id_commande) REFERENCES gamelife.glcommande (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_produit_revendeur) REFERENCES gamelife.glproduit_revendeur (id) ON DELETE CASCADE ON UPDATE CASCADE
);