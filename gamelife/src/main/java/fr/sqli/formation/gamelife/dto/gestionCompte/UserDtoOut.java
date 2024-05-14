package fr.sqli.formation.gamelife.dto.gestionCompte;

import fr.sqli.formation.gamelife.entite.UtilisateurEntite;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDtoOut extends UtilisateurEntite {
    private UUID id;
    private String nom;
    private String prenom;
    private String email;
    private Integer num_rue;
    private String rue;
    private String ville;
    private Integer code_postal;
    private String num_siret;


}
