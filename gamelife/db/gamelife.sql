CREATE SCHEMA IF NOT EXISTS gamelife;

SET search_path TO gamelife;

DROP TABLE IF EXISTS gamelife.glutilisateur CASCADE;
DROP TABLE IF EXISTS gamelife.glcommande CASCADE;
DROP TABLE IF EXISTS gamelife.glproduit CASCADE;
DROP TABLE IF EXISTS gamelife.glcategorie CASCADE;
DROP TABLE IF EXISTS gamelife.glplateforme CASCADE;
DROP TABLE IF EXISTS gamelife.glproduit_glcategorie CASCADE;
DROP TABLE IF EXISTS gamelife.glproduit_glplateforme CASCADE;
DROP TABLE IF EXISTS gamelife.glimage CASCADE;
DROP TABLE IF EXISTS gamelife.glitem_commande CASCADE;
DROP TABLE IF EXISTS gamelife.glproduit_revendeur CASCADE;

CREATE TABLE gamelife.glutilisateur
(
    id                    UUID   PRIMARY KEY,
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
    id                    UUID   PRIMARY KEY,
    etat                  VARCHAR(80)  NOT NULL,
    num_rue_livraison     INT          NOT NULL,
    rue_livraison         VARCHAR(255) NOT NULL,
    ville_livraison       VARCHAR(80)  NOT NULL,
    code_postal_livraison INT          NOT NULL,
    date                  DATE         NOT NULL,
    utilisateur_id        UUID   NOT NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES gamelife.glutilisateur (id)
);

CREATE TABLE gamelife.glcategorie
(
    id             UUID   PRIMARY KEY,
    libelle        VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE gamelife.glplateforme
(
    id            UUID   PRIMARY KEY,
    libelle       VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE gamelife.glproduit
(
    id            UUID   PRIMARY KEY,
    nom           VARCHAR(255) NOT NULL,
    description   TEXT         NOT NULL
);

CREATE TABLE gamelife.glproduit_glcategorie (
    produit_id      UUID NOT NULL,
    categorie_id    UUID NOT NULL,
    FOREIGN KEY (produit_id) REFERENCES gamelife.glproduit(id),
    FOREIGN KEY (categorie_id) REFERENCES gamelife.glcategorie(id)
);

CREATE TABLE gamelife.glproduit_glplateforme (
     produit_id       UUID NOT NULL,
     plateforme_id    UUID NOT NULL,
     FOREIGN KEY (produit_id) REFERENCES gamelife.glproduit(id),
     FOREIGN KEY (plateforme_id) REFERENCES gamelife.glplateforme(id)
);

CREATE TABLE gamelife.glimage
(
    id          UUID   PRIMARY KEY,
    image       TEXT NOT NULL,
    titre       TEXT NOT NULL,
    produit_id  UUID   NOT NULL,
    FOREIGN KEY (produit_id) REFERENCES gamelife.glproduit (id)
);

CREATE TABLE gamelife.glproduit_revendeur
(
    id             UUID   PRIMARY KEY,
    stock          INT            NOT NULL,
    prix           DECIMAL(10, 0) NOT NULL,
    etat           VARCHAR(25)    NOT NULL,
    produit_id     UUID             NOT NULL,
    utilisateur_id UUID             NOT NULL,
    FOREIGN KEY (produit_id) REFERENCES gamelife.glproduit (id),
    FOREIGN KEY (utilisateur_id) REFERENCES gamelife.glutilisateur (id)
);

CREATE TABLE gamelife.glitem_commande
(
    id                    UUID   PRIMARY KEY,
    quantite              INT NOT NULL,
    commande_id           UUID   NOT NULL,
    produit_revendeur_id  UUID   NOT NULL,
    FOREIGN KEY (commande_id) REFERENCES gamelife.glcommande (id),
    FOREIGN KEY (produit_revendeur_id) REFERENCES gamelife.glproduit_revendeur (id)
);

-- utilisateurs (à supprimer)
INSERT INTO gamelife.glutilisateur (id, nom, prenom, mdp, email, num_rue, rue, ville, code_postal, role, num_siren, etat_compte, reset_password_token)
VALUES ('ede28d8b-9170-4e8e-83b3-3c2c16c39ae8', 'admin', 'admin', '$2a$12$CPjNhkXJGvh05Q2RxbatceYvVem4LVBuKfm6vgh7KVHPxp0ZvXuCi', 'admin@gamelife.fr', 2, 'rue de capucine', 'paris', 75000, 'ROLE_ADMIN', null, true, null);