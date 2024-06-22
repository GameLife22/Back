CREATE SCHEMA IF NOT EXISTS gamelife;

SET search_path TO gamelife;

DROP TABLE IF EXISTS gamelife.gluser CASCADE;
DROP TABLE IF EXISTS gamelife.glorder CASCADE;
DROP TABLE IF EXISTS gamelife.glgame CASCADE;
DROP TABLE IF EXISTS gamelife.glgenre CASCADE;
DROP TABLE IF EXISTS gamelife.glplatform;
DROP TABLE IF EXISTS gamelife.glimage CASCADE;
DROP TABLE IF EXISTS gamelife.glorder_item CASCADE;
DROP TABLE IF EXISTS gamelife.glproduct_seller CASCADE;

CREATE TABLE gamelife.gluser
(
    id                    UUID   PRIMARY KEY,
    last_name             VARCHAR(50)          NOT NULL,
    first_name            VARCHAR(50)          NOT NULL,
    password              VARCHAR(80)          NOT NULL,
    email                 VARCHAR(80)          NOT NULL UNIQUE,
    street_number         INT                  NOT NULL,
    street                VARCHAR(255)         NOT NULL,
    city                  VARCHAR(80)          NOT NULL,
    zip_code              INT                  NOT NULL,
    role                  VARCHAR(50)          NOT NULL,
    siren_number          CHAR(9)              NULL DEFAULT NULL UNIQUE,
    account_status        BOOLEAN              DEFAULT TRUE NOT NULL,
    reset_password_token  VARCHAR(30)          NULL
);

CREATE TABLE gamelife.glorder
(
    id                     UUID   PRIMARY KEY,
    status                 VARCHAR(80)  NOT NULL,
    delivery_street_number INT          NOT NULL,
    delivery_street        VARCHAR(255) NOT NULL,
    delivery_city          VARCHAR(80)  NOT NULL,
    delivery_zip_code      INT          NOT NULL,
    date                   DATE         NOT NULL,
    user_id                UUID         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES gamelife.gluser (id)
);

CREATE TABLE gamelife.glgame
(
    id          UUID        PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    description TEXT        NOT NULL
);

CREATE TABLE gamelife.glgenre
(
    game_id UUID        NOT NULL,
    genre   VARCHAR(25) NOT NULL,
    CONSTRAINT fk_glgenre_glgame FOREIGN KEY (game_id) REFERENCES gamelife.glgame (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glplatform
(
    game_id  UUID         NOT NULL,
    platform VARCHAR(255) NOT NULL,
    CONSTRAINT fk_glplatform_glgame FOREIGN KEY (game_id) REFERENCES gamelife.glgame (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glimage
(
    image_url VARCHAR(2083) NOT NULL,
    game_id   UUID          NOT NULL,
    CONSTRAINT fk_glimage_glgame FOREIGN KEY (game_id) REFERENCES gamelife.glgame (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE gamelife.glproduct_seller
(
    id      UUID         PRIMARY KEY,
    stock   INT          NOT NULL,
    price   DECIMAL(10, 0) NOT NULL,
    status  VARCHAR(25)  NOT NULL,
    game_id UUID         NOT NULL,
    user_id UUID         NOT NULL,
    FOREIGN KEY (game_id) REFERENCES gamelife.glgame (id),
    FOREIGN KEY (user_id) REFERENCES gamelife.gluser (id)
);

CREATE TABLE gamelife.glorder_item
(
    id                UUID PRIMARY KEY,
    quantity          INT  NOT NULL,
    order_id          UUID NOT NULL,
    product_seller_id UUID NOT NULL,
    FOREIGN KEY (order_id) REFERENCES gamelife.glorder (id),
    FOREIGN KEY (product_seller_id) REFERENCES gamelife.glproduct_seller (id)
);
