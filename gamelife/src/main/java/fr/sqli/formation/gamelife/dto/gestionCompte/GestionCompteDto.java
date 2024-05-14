package fr.sqli.formation.gamelife.dto.gestionCompte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GestionCompteDto {

    private UUID id;

    private String email;

    private String nom;

    private Integer num_rue;

    private Integer codePostal;

    private String numSiren;

    private String prenom;

    private String rue;

    private String ville;


}