package fr.sqli.formation.gamelife.convertisseur;

import fr.sqli.formation.gamelife.dto.produit.ProduitRevendeurReponse;
import fr.sqli.formation.gamelife.entite.ProduitRevendeurEntite;

import java.util.List;
import java.util.stream.Collectors;

public interface IProduitRevendeurConvertisseur {
    public static ProduitRevendeurReponse convertirEnProduitRevendeurReponse(ProduitRevendeurEntite pProduitRevendeurEntite) {
        ProduitRevendeurReponse produitRevendeurReponse = new ProduitRevendeurReponse();
        produitRevendeurReponse.setId(pProduitRevendeurEntite.getId());
        produitRevendeurReponse.setStock(pProduitRevendeurEntite.getStock());
        produitRevendeurReponse.setPrix(pProduitRevendeurEntite.getPrix());
        produitRevendeurReponse.setEtat(pProduitRevendeurEntite.getEtat());
        return produitRevendeurReponse;
    }

    public static List<ProduitRevendeurReponse> convertirEnProduitRevendeursReponse(List<ProduitRevendeurEntite> pProduitRevendeurEntites) {
        return pProduitRevendeurEntites.stream()
                .map(IProduitRevendeurConvertisseur::convertirEnProduitRevendeurReponse)
                .collect(Collectors.toList());
    }
}
