package fr.sqli.formation.gamelife.dto.panier;

import fr.sqli.formation.gamelife.dto.utilisateur.UtilisateurDto;
import java.util.Date;
import java.util.List;

public class PanierDto {

    //les informations sur le panier lui-même.
    private int id;
    private Date date;
    private byte etat;
    private UtilisateurDto utilisateur;
    private List<ItemPanierDto> itemPaniers;

    public PanierDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte getEtat() {
        return etat;
    }

    public void setEtat(byte etat) {
        this.etat = etat;
    }

    public UtilisateurDto getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDto utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<ItemPanierDto> getItemPaniers() {
        return itemPaniers;
    }

    public void setItemPaniers(List<ItemPanierDto> itemPaniers) {
        this.itemPaniers = itemPaniers;
    }


}
