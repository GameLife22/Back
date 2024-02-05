package fr.sqli.formation.gamelife.dto.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.time.LocalDate;
import java.util.List;

public class CommandeDtoIn extends AbstractDtoIn {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer idUtilisateur;
    private String etat;
    private Integer numRueLivraison;
    private String rueLivraison;
    private String villeLivraison;
    private Integer codePostalLivraison;
    private List<Integer> idItemsCommande;
    private LocalDate date;

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer pIdUtilisateur) {
        idUtilisateur = pIdUtilisateur;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String pEtat) {
        etat = pEtat;
    }

    public Integer getNumRueLivraison() {
        return numRueLivraison;
    }

    public void setNumRueLivraison(Integer pNumRueLivraison) {
        numRueLivraison = pNumRueLivraison;
    }

    public String getRueLivraison() {
        return rueLivraison;
    }

    public void setRueLivraison(String pRueLivraison) {
        rueLivraison = pRueLivraison;
    }

    public String getVilleLivraison() {
        return villeLivraison;
    }

    public void setVilleLivraison(String pVilleLivraison) {
        villeLivraison = pVilleLivraison;
    }

    public Integer getCodePostalLivraison() {
        return codePostalLivraison;
    }

    public void setCodePostalLivraison(Integer pCodePostalLivraison) {
        codePostalLivraison = pCodePostalLivraison;
    }

    public List<Integer> getIdItemsCommande() {
        return idItemsCommande;
    }

    public void setIdItemsCommande(List<Integer> pIdItemsCommande) {
        idItemsCommande = pIdItemsCommande;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate pDate) {
        date = pDate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CommandeDtoIn{");
        sb.append("idUtilisateur=").append(idUtilisateur);
        sb.append(", etat='").append(etat).append('\'');
        sb.append(", numRueLivraison=").append(numRueLivraison);
        sb.append(", rueLivraison='").append(rueLivraison).append('\'');
        sb.append(", villeLivraison='").append(villeLivraison).append('\'');
        sb.append(", codePostalLivraison=").append(codePostalLivraison);
        sb.append(", idItemsCommande=").append(idItemsCommande);
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
