CREATE SCHEMA IF NOT EXISTS gamelife;

DROP TABLE IF EXISTS gamelife.glutilisateur CASCADE;
DROP TABLE IF EXISTS gamelife.glcommande CASCADE;
DROP TABLE IF EXISTS gamelife.glproduit CASCADE;
DROP TABLE IF EXISTS gamelife.glcategorie CASCADE;
DROP TABLE IF EXISTS gamelife.glplateforme CASCADE;
DROP TABLE IF EXISTS gamelife.glimage CASCADE;
DROP TABLE IF EXISTS gamelife.glitem_commande CASCADE;
DROP TABLE IF EXISTS gamelife.glproduit_revendeur CASCADE;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE gamelife.glutilisateur
(
    id                    UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    nom                   VARCHAR(50)          NOT NULL,
    prenom                VARCHAR(50)          NOT NULL,
    mdp                   VARCHAR(80)          NOT NULL,
    email                 VARCHAR(80)          NOT NULL UNIQUE,
    num_rue               INT                  NOT NULL,
    rue                   VARCHAR(255)         NOT NULL,
    ville                 VARCHAR(80)          NOT NULL,
    code_postal           INT                  NOT NULL,
    role                  VARCHAR(50)          NOT NULL,
    num_siren             CHAR(9) NULL DEFAULT NULL UNIQUE,
    etat_compte           BOOLEAN DEFAULT TRUE NOT NULL,
    reset_password_token  VARCHAR(30) NULL
);

CREATE TABLE gamelife.glcommande
(
    id                    UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id_utilisateur        UUID DEFAULT uuid_generate_v4()         NOT NULL,
    etat                  VARCHAR(80)  NOT NULL,
    num_rue_livraison     INT          NOT NULL,
    rue_livraison         VARCHAR(255) NOT NULL,
    ville_livraison       VARCHAR(80)  NOT NULL,
    code_postal_livraison INT          NOT NULL,
    date                  DATE         NOT NULL,
    FOREIGN KEY (id_utilisateur) REFERENCES gamelife.glutilisateur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glcategorie
(
    id              UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    libelle         VARCHAR(25) NOT NULL UNIQUE,
    etat            BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE gamelife.glplateforme
(
    id              UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    libelle         VARCHAR(25) NOT NULL UNIQUE,
    etat            BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE gamelife.glproduit
(
    id            UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    nom           VARCHAR(255) NOT NULL UNIQUE,
    description   TEXT         NOT NULL,
    id_categorie  UUID DEFAULT uuid_generate_v4()         NOT NULL,
    id_plateforme UUID DEFAULT uuid_generate_v4()         NOT NULL,
    etat          BOOLEAN      DEFAULT TRUE,
    FOREIGN KEY (id_categorie) REFERENCES gamelife.glcategorie (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_plateforme) REFERENCES gamelife.glplateforme (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glimage
(
    id          UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id_produit  UUID DEFAULT uuid_generate_v4()         NOT NULL,
    image       TEXT NOT NULL,
    titre       TEXT NOT NULL,
    FOREIGN KEY (id_produit) REFERENCES gamelife.glproduit (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glproduit_revendeur
(
    id             UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    stock          INT            NOT NULL,
    prix           DECIMAL(10, 0) NOT NULL,
    etat           VARCHAR(25)    NOT NULL,
    id_produit     UUID DEFAULT uuid_generate_v4()           NOT NULL,
    id_utilisateur UUID DEFAULT uuid_generate_v4()           NOT NULL,
    FOREIGN KEY (id_produit) REFERENCES gamelife.glproduit (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_utilisateur) REFERENCES gamelife.glutilisateur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glitem_commande
(
    id                    UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id_commande           UUID DEFAULT uuid_generate_v4() NOT NULL,
    id_produit_revendeur  UUID DEFAULT uuid_generate_v4() NOT NULL,
    quantite              INT NOT NULL,
    FOREIGN KEY (id_commande) REFERENCES gamelife.glcommande (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_produit_revendeur) REFERENCES gamelife.glproduit_revendeur (id) ON DELETE CASCADE ON UPDATE CASCADE
);