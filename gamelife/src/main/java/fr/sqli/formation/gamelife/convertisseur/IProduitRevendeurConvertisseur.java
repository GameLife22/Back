package fr.sqli.formation.gamelife.convertisseur;

import fr.sqli.formation.gamelife.dto.produit.ProduitRevendeurReponse;
import fr.sqli.formation.gamelife.entite.ProduitRevendeurEntite;

public interface IProduitRevendeurConvertisseur {
    public static ProduitRevendeurReponse convertirEnCategorieReponse(ProduitRevendeurEntite pProduitRevendeurEntite) {
        ProduitRevendeurReponse produitRevendeurReponse = new ProduitRevendeurReponse();
        produitRevendeurReponse.setId(pProduitRevendeurEntite.getId());
        produitRevendeurReponse.setStock(pProduitRevendeurEntite.getStock());
        produitRevendeurReponse.setPrix(pProduitRevendeurEntite.getPrix());
        produitRevendeurReponse.setEtat(pProduitRevendeurEntite.getEtat());
        return produitRevendeurReponse;
    }
}
