-- Users
INSERT INTO gamelife.gluser (id, last_name, first_name, password, email, street_number, street, city, zip_code, role, siren_number, account_status, reset_password_token)
VALUES
    ('ede28d8b-9170-4e8e-83b3-3c2c16c39ae8', 'admin', 'admin', '$2a$12$CPjNhkXJGvh05Q2RxbatceYvVem4LVBuKfm6vgh7KVHPxp0ZvXuCi', 'admin@gamelife.fr', 2, 'rue de capucine', 'paris', 75000, 'ROLE_ADMIN', NULL, true, NULL),
    ('b114050e-1341-4ce4-9380-8c01b3eb52fb', 'moderateur', 'moderateur', '$2a$12$CPjNhkXJGvh05Q2RxbatceYvVem4LVBuKfm6vgh7KVHPxp0ZvXuCi', 'moderateur@gamelife.fr', 2, 'rue du general de Gaulle', 'paris', 75000, 'ROLE_MODERATEUR', NULL, true, NULL),
    ('c81f4beb-d17d-4b68-8a10-195745ddb894', 'acheteur', 'acheteur', '$2a$12$CPjNhkXJGvh05Q2RxbatceYvVem4LVBuKfm6vgh7KVHPxp0ZvXuCi', 'acheteur@gamelife.fr', 2, 'rue du marechal', 'nantes', 44000, 'ROLE_ACHETEUR', NULL, true, NULL),
    ('906d837f-c451-4aa1-9bc1-e92038e93f1d', 'revendeur', 'revendeur', '$2a$12$CPjNhkXJGvh05Q2RxbatceYvVem4LVBuKfm6vgh7KVHPxp0ZvXuCi', 'revendeur@gamelife.fr', 3, 'rue dupont', 'lille', 59000, 'ROLE_REVENDEUR', '325987418', true, NULL);

-- Games
INSERT INTO gamelife.glgame (id, name, description)
VALUES
    ('6a4a4185-cdb4-418e-9249-a160e384d877', 'FIFA', 'FOOTBALL'),
    ('994a4185-cdb4-418e-9249-a160e3840000', 'GTA', 'Grand Theft Auto'),
    ('884a4185-cdb4-418e-9249-a160e3840044', 'CALL OFF', 'Guerre');

-- Genres
INSERT INTO gamelife.glgenre (game_id, genre)
VALUES
    ('6a4a4185-cdb4-418e-9249-a160e384d877', 'Sports');

-- Platforms
INSERT INTO gamelife.glplatform (game_id, platform)
VALUES
    ('6a4a4185-cdb4-418e-9249-a160e384d877', 'PlayStation');

-- Images
INSERT INTO gamelife.glimage (image_url, game_id)
VALUES
    ('test', '6a4a4185-cdb4-418e-9249-a160e384d877'),
    ('test 2', '994a4185-cdb4-418e-9249-a160e3840000'),
    ('test 3', '884a4185-cdb4-418e-9249-a160e3840044');

-- Orders
INSERT INTO gamelife.glorder (id, status, delivery_street_number, delivery_street, delivery_city, delivery_zip_code, date, user_id)
VALUES
    ('01234567-89ab-cdef-0123-456789abcdef', 'NOUVELLE', 123, 'Rue de la Peace', 'Paris', 75000, '2024-05-07', 'ede28d8b-9170-4e8e-83b3-3c2c16c39ae8');

-- Product Sellers
INSERT INTO gamelife.glproduct_seller (id, stock, price, status, game_id, user_id)
VALUES
    ('63ef0498-3148-4e57-a4f6-4c17a9ed9352', 10, 49, 'Active', '6a4a4185-cdb4-418e-9249-a160e384d877', 'ede28d8b-9170-4e8e-83b3-3c2c16c39ae8'),
    ('56ef0498-3148-4e57-a4f6-4c17a9ed9366', 10, 59, 'Active', '6a4a4185-cdb4-418e-9249-a160e384d877', 'ede28d8b-9170-4e8e-83b3-3c2c16c39ae8'),
    ('77ef0498-3148-4e57-a4f6-4c17a9ed9399', 10, 29, 'Inactive', '994a4185-cdb4-418e-9249-a160e3840000', 'ede28d8b-9170-4e8e-83b3-3c2c16c39ae8');

-- Order Items
INSERT INTO gamelife.glorder_item (id, quantity, order_id, product_seller_id)
VALUES
    ('1', 2, '01234567-89ab-cdef-0123-456789abcdef', '63ef0498-3148-4e57-a4f6-4c17a9ed9352');
