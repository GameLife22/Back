    package fr.sqli.formation.gamelife.dto.commande;

    import fr.sqli.formation.gamelife.enumeration.EtatCommande;

    import java.time.LocalDate;
    import java.util.List;
    import java.util.UUID;

    public class CommandeReponse {
        private UUID id;
        private EtatCommande etat;
        private Integer numRueLivraison;
        private String rueLivraison;
        private String villeLivraison;
        private Integer codePostalLivraison;
        private List<UUID> idItemsCommande;
        private LocalDate date;
        private List<ItemCommandeReponse> itemsCommande;

        public UUID getId() {
            return id;
        }

        public void setId(UUID pId) {
            id = pId;
        }

        public EtatCommande getEtat() {
            return etat;
        }

        public void setEtat(EtatCommande pEtat) {
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

        public List<UUID> getIdItemsCommande() {
            return idItemsCommande;
        }

        public void setIdItemsCommande(List<UUID> pIdItemsCommande) {
            idItemsCommande = pIdItemsCommande;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate pDate) {
            date = pDate;
        }

        public List<ItemCommandeReponse> getItemsCommande() {
            return itemsCommande;
        }

        public void setItemsCommande(List<ItemCommandeReponse> pItemsCommande) {
            itemsCommande = pItemsCommande;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("CommandeDtoOut{");
            sb.append("id=").append(id);
            sb.append(", etat=").append(etat);
            sb.append(", numRueLivraison=").append(numRueLivraison);
            sb.append(", rueLivraison='").append(rueLivraison).append('\'');
            sb.append(", villeLivraison='").append(villeLivraison).append('\'');
            sb.append(", codePostalLivraison=").append(codePostalLivraison);
            sb.append(", idItemsCommande=").append(idItemsCommande);
            sb.append(", date=").append(date);
            sb.append(", itemsCommande=").append(itemsCommande);
            sb.append('}');
            return sb.toString();
        }
    }
