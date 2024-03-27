CREATE SCHEMA IF NOT EXISTS gamelife;

DROP TABLE IF EXISTS gamelife.glutilisateur CASCADE;
DROP TABLE IF EXISTS gamelife.glcommande CASCADE;
DROP TABLE IF EXISTS gamelife.glproduit CASCADE;
DROP TABLE IF EXISTS gamelife.glcategorie CASCADE;
DROP TABLE IF EXISTS gamelife.glplateforme CASCADE;
DROP TABLE IF EXISTS gamelife.glimage CASCADE;
DROP TABLE IF EXISTS gamelife.glitem_commande CASCADE;
DROP TABLE IF EXISTS gamelife.glproduit_revendeur CASCADE;

CREATE TABLE gamelife.glutilisateur
(
    id                    UUID DEFAULT RANDOM_UUID()  PRIMARY KEY,
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
    id                    UUID DEFAULT RANDOM_UUID()  PRIMARY KEY,
    etat                  VARCHAR(80)  NOT NULL,
    num_rue_livraison     INT          NOT NULL,
    rue_livraison         VARCHAR(255) NOT NULL,
    ville_livraison       VARCHAR(80)  NOT NULL,
    code_postal_livraison INT          NOT NULL,
    date                  DATE         NOT NULL,
    utilisateur_id        UUID DEFAULT RANDOM_UUID()  NOT NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES gamelife.glutilisateur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glcategorie
(
    id              UUID DEFAULT RANDOM_UUID()  PRIMARY KEY,
    libelle         VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE gamelife.glplateforme
(
    id            UUID DEFAULT RANDOM_UUID()  PRIMARY KEY,
    libelle         VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE gamelife.glproduit
(
    id            UUID DEFAULT RANDOM_UUID()  PRIMARY KEY,
    nom           VARCHAR(255) NOT NULL UNIQUE,
    description   TEXT         NOT NULL,
    etat_produit  BOOLEAN      DEFAULT TRUE,
    categorie_id  UUID DEFAULT RANDOM_UUID() NOT NULL,
    plateforme_id UUID DEFAULT RANDOM_UUID() NOT NULL,
    FOREIGN KEY (categorie_id) REFERENCES gamelife.glcategorie (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (plateforme_id) REFERENCES gamelife.glplateforme (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glimage
(
    id          UUID DEFAULT RANDOM_UUID()  PRIMARY KEY,
    image       TEXT NOT NULL,
    titre       TEXT NOT NULL,
    produit_id  UUID DEFAULT RANDOM_UUID()  NOT NULL,
    FOREIGN KEY (produit_id) REFERENCES gamelife.glproduit (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glproduit_revendeur
(
    id             UUID DEFAULT RANDOM_UUID()  PRIMARY KEY,
    stock          INT            NOT NULL,
    prix           DECIMAL(10, 0) NOT NULL,
    etat           VARCHAR(25)    NOT NULL,
    produit_id     UUID DEFAULT RANDOM_UUID()            NOT NULL,
    utilisateur_id UUID DEFAULT RANDOM_UUID()            NOT NULL,
    FOREIGN KEY (produit_id) REFERENCES gamelife.glproduit (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (utilisateur_id) REFERENCES gamelife.glutilisateur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glitem_commande
(
    id                    UUID DEFAULT RANDOM_UUID()  PRIMARY KEY,
    quantite              INT NOT NULL,
    commande_id           UUID DEFAULT RANDOM_UUID()  NOT NULL,
    produit_revendeur_id  UUID DEFAULT RANDOM_UUID()  NOT NULL,
    FOREIGN KEY (commande_id) REFERENCES gamelife.glcommande (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (produit_revendeur_id) REFERENCES gamelife.glproduit_revendeur (id) ON DELETE CASCADE ON UPDATE CASCADE
);