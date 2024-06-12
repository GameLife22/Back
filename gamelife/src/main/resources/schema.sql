CREATE SCHEMA IF NOT EXISTS gamelife;

SET search_path TO gamelife;

DROP TABLE IF EXISTS gamelife.glutilisateur CASCADE;
DROP TABLE IF EXISTS gamelife.glcommande CASCADE;
DROP TABLE IF EXISTS gamelife.glgame CASCADE;
DROP TABLE IF EXISTS gamelife.glgenre CASCADE;
DROP TABLE IF EXISTS gamelife.glplatform;
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
    num_siren             CHAR(9) NULL UNIQUE,
    etat_compte           BOOLEAN NOT NULL,
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

CREATE TABLE gamelife.glgame
(
    id            UUID   PRIMARY KEY,
    name          VARCHAR(50) NOT NULL,
    description   TEXT         NOT NULL
);

CREATE TABLE gamelife.glgenre (
    game_id UUID NOT NULL,
    genre VARCHAR(25) NOT NULL,
    CONSTRAINT fk_glgenre_glgame FOREIGN KEY (game_id) REFERENCES gamelife.glgame (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glplatform(
   game_id UUID NOT NULL,
   platform VARCHAR(255) NOT NULL,
   CONSTRAINT fk_glplatform_glgame FOREIGN KEY (game_id) REFERENCES gamelife.glgame (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glimage
(
    image_url VARCHAR(2083) NOT NULL,
    game_id UUID NOT NULL,
    CONSTRAINT fk_glimage_glgame FOREIGN KEY (game_id) REFERENCES gamelife.glgame (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glproduit_revendeur
(
    id             UUID   PRIMARY KEY,
    stock          INT            NOT NULL,
    prix           DECIMAL(10, 0) NOT NULL,
    etat           VARCHAR(25)    NOT NULL,
    game_id     UUID             NOT NULL,
    utilisateur_id UUID             NOT NULL,
    FOREIGN KEY (game_id) REFERENCES gamelife.glgame (id),
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