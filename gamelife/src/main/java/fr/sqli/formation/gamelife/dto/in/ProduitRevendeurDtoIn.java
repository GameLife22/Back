package fr.sqli.formation.gamelife.dto.in;

import fr.sqli.formation.gamelife.entity.EtatProduitRevendeur;

import java.io.Serial;
import java.math.BigDecimal;

public class ProduitRevendeurDtoIn extends AbstractDtoIn {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer stock;
    private BigDecimal prix;
    private EtatProduitRevendeur etatProduitRevendeur;
    private Integer idProduit;
    private Integer idUtilisateur;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer pStock) {
        stock = pStock;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal pPrix) {
        prix = pPrix;
    }

    public EtatProduitRevendeur getEtatProduitRevendeur() {
        return etatProduitRevendeur;
    }

    public void setEtatProduitRevendeur(EtatProduitRevendeur pEtatProduitRevendeur) {
        etatProduitRevendeur = pEtatProduitRevendeur;
    }

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer pIdProduit) {
        idProduit = pIdProduit;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer pIdUtilisateur) {
        idUtilisateur = pIdUtilisateur;
    }

    @Override
    public void validate() {

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitRevendeurDtoIn{");
        sb.append("stock=").append(stock);
        sb.append(", prix=").append(prix);
        sb.append(", etatProduitRevendeur=").append(etatProduitRevendeur);
        sb.append(", idProduit=").append(idProduit);
        sb.append(", idUtilisateur=").append(idUtilisateur);
        sb.append('}');
        return sb.toString();
    }
}
