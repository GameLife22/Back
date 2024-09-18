package fr.sqli.formation.gamelife.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Registration {
    private String email;

    private Integer etat;

    private String mdp;

    private String nom;

    private int num_rue;

    private String num_siret;

    private String prenom;

    private String rue;

    private String ville;

    private String role;

    private  int code_postal;

}
