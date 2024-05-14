package fr.sqli.formation.gamelife.dto.produit;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProduitRevendeurRequete {

    private UUID id;
    private Integer stock;
    private BigDecimal prix;
    private String etat;
    private UUID idProduit; // L'IDENTIFIANT DU PRODUIT LIER
    private UUID idUtilisateur; // L'IDENTIFIANT DE L'UTILISATEUR LIER
}
