-- -----------------------------------------------------
-- Insertion dans la table UTILISATEURs
-- -----------------------------------------------------
INSERT INTO `UTILISATEUR` (`nom`, `prenom`, `mdp`, `email`, `num_rue`, `rue`, `ville`, `code_postal`, `role`, `num_siren`, `etat_compte`) VALUES ('admin', 'admin', '$2y$10$cllVSeF0YfWJqP69iZ0QHObAqWgj0teCZRmCjXVFSvIDbKpxb4e4u', 'fabien.bidault@social.aston-ecole.com', '1', 'rue de capucine', 'paris', '75000', 'ROLE_ADMIN', null,1);
INSERT INTO `UTILISATEUR` (`nom`, `prenom`, `mdp`, `email`, `num_rue`, `rue`, `ville`, `code_postal`, `role`, `num_siren`, `etat_compte`) VALUES ('smith', 'albert', '$2y$10$Be8wPp9hlL8MTMAzpGddt.LDhjww9HQq9WgxVqmrL8wXduVKIYuZO', 'revendeur001@outlook.fr', '2', 'rue du marechal', 'nantes', '44000', 'ROLE_REVENDEUR', '325987418',1);
INSERT INTO `UTILISATEUR` (`nom`, `prenom`, `mdp`, `email`, `num_rue`, `rue`, `ville`, `code_postal`, `role`, `num_siren`, `etat_compte`) VALUES ('dupont', 'emmanuelle', '$2y$10$YOzoCt/pBb64uE/OOH3T.eeBrXqa/ftnkj8XpnIDc964.AYex6pEa', 'acheteur001@outlook.fr', '3', 'rue dupont', 'lille', '59000', 'ROLE_ACHETEUR', null,1);
INSERT INTO `UTILISATEUR` (`nom`, `prenom`, `mdp`, `email`, `num_rue`, `rue`, `ville`, `code_postal`, `role`, `num_siren`, `etat_compte`) VALUES ('dubois', 'henry', '$2y$10$KZTksWKizskKPwZLaW6FWejCcEF1AdsYzVM8jxmHsot3Jypu0ZUti', 'acheteur002@outlook.fr', '3', 'rue dupont', 'lille', '59000', 'ROLE_ACHETEUR', null,0);