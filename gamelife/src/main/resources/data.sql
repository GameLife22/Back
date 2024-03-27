-- Insérer des catégories

INSERT INTO gamelife.glcategorie(id, libelle)
VALUES ('8ddee749-65db-4fa4-a5b2-74a4463cba74', 'test');

INSERT INTO gamelife.glcategorie(id, libelle)
VALUES ('8192d7fe-2789-43c3-9789-9f554ff9b9fb', 'test 2');

-- Insérer des plateformes

INSERT INTO gamelife.glplateforme(id, libelle)
VALUES ('4a523eed-9372-4979-89a8-2eec8d41f696', 'test');

INSERT INTO gamelife.glplateforme(id, libelle)
VALUES ('171642f4-d2b5-49a3-91f9-dd31bf07d237', 'test 2');

-- Insérer un produit avec une image, une catégorie et une plateforme

INSERT INTO gamelife.glproduit (id, nom, description, etat_produit, categorie_id, plateforme_id)
VALUES ('6a4a4185-cdb4-418e-9249-a160e384d877', 'test', 'test', true, '8ddee749-65db-4fa4-a5b2-74a4463cba74', '4a523eed-9372-4979-89a8-2eec8d41f696');

-- Insérer une image associé à un produit

INSERT INTO gamelife.glimage (id, image, titre, produit_id)
VALUES ('d9798c40-173f-4061-bc2e-a39edece42ff', 'test', 'test', '6a4a4185-cdb4-418e-9249-a160e384d877');

INSERT INTO gamelife.glimage (id, image, titre, produit_id)
VALUES ('1b014282-3fa7-4997-be24-4538dbb74f81', 'test 2', 'test 2', '6a4a4185-cdb4-418e-9249-a160e384d877');

-- Insérer des nouveaux utilisateurs

INSERT INTO gamelife.glutilisateur (nom, prenom, mdp, email, num_rue, rue, ville, code_postal, role, num_siren, etat_compte, reset_password_token)
VALUES ('admin', 'admin', '$2a$12$CPjNhkXJGvh05Q2RxbatceYvVem4LVBuKfm6vgh7KVHPxp0ZvXuCi', 'admin@gamelife.fr', '2', 'rue de capucine', 'paris', '75000', 'ROLE_ADMIN', null, true, null);

INSERT INTO gamelife.glutilisateur (nom, prenom, mdp, email, num_rue, rue, ville, code_postal, role, num_siren, etat_compte, reset_password_token)
VALUES ('moderateur', 'moderateur', '$2a$12$CPjNhkXJGvh05Q2RxbatceYvVem4LVBuKfm6vgh7KVHPxp0ZvXuCi', 'moderateur@gamelife.fr', '2', 'rue du general de Gaulle', 'paris', '75000', 'ROLE_MODERATEUR', null, true, null);

INSERT INTO gamelife.glutilisateur (nom, prenom, mdp, email, num_rue, rue, ville, code_postal, role, num_siren, etat_compte, reset_password_token)
VALUES ('acheteur', 'acheteur', '$2a$12$CPjNhkXJGvh05Q2RxbatceYvVem4LVBuKfm6vgh7KVHPxp0ZvXuCi', 'acheteur@test.fr', '2', 'rue du marechal', 'nantes', '44000', 'ROLE_REVENDEUR', '325987418', true, null);

INSERT INTO gamelife.glutilisateur (nom, prenom, mdp, email, num_rue, rue, ville, code_postal, role, num_siren, etat_compte, reset_password_token)
VALUES ('vendeur', 'vendeur', '$2a$12$CPjNhkXJGvh05Q2RxbatceYvVem4LVBuKfm6vgh7KVHPxp0ZvXuCi', 'vendeur@test.fr', '3', 'rue dupont', 'lille', '59000', 'ROLE_ACHETEUR', null, true, null);