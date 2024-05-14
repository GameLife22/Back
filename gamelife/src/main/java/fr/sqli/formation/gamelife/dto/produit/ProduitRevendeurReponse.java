package fr.sqli.formation.gamelife.dto.produit;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProduitRevendeurReponse {

    private UUID id;
    private Integer stock;
    private BigDecimal prix;
    private String etat;
    private ProduitReponse produit; // la représentation du produit lier


}
