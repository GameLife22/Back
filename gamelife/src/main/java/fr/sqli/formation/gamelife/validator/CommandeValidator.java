package fr.sqli.formation.gamelife.validator;

import fr.sqli.formation.gamelife.dto.in.ItemCommandeDtoIn;
import fr.sqli.formation.gamelife.dto.in.ProduitRevendeurDtoIn;
import fr.sqli.formation.gamelife.entity.CommandeEntity;
import fr.sqli.formation.gamelife.entity.ItemCommandeEntity;
import fr.sqli.formation.gamelife.entity.ProduitRevendeurEntity;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.enums.EtatCommande;
import fr.sqli.formation.gamelife.repository.ProduitRevendeurRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class CommandeValidator implements Validator {
    private final ProduitRevendeurRepository produitRevendeurRepository;

    public CommandeValidator(ProduitRevendeurRepository produitRevendeurRepository) {
        this.produitRevendeurRepository = produitRevendeurRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CommandeEntity.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        CommandeEntity commandeEntity = (CommandeEntity) target;

        // Validation de l'état de la commande
        if (!commandeEntity.getEtat().equals(EtatCommande.EN_ATTENTE_PAIEMENT)) {
            errors.rejectValue("etat", "invalid", "La commande n'est pas dans un état valide pour être validée");
        }


        // Validation des articles dans la commande
        List<ItemCommandeEntity> itemsCommande = commandeEntity.getItemsCommande();
        if (itemsCommande == null || itemsCommande.isEmpty()) {
            errors.reject("emptyItems", "La commande ne contient aucun article");
        }
        // Validation de la date de commande
        if (commandeEntity.getDate().isAfter(LocalDate.now())) {
            errors.reject("invalidDate", "La date de commande est dans le futur");
        }

        // Validation des quantités minimales/maximales des articles
        for (ItemCommandeEntity item : commandeEntity.getItemsCommande()) {
            if (item.getQuantite() < 1) {
                errors.reject("quantite", "La quantité de l'article ne peut pas être inférieure à 1");
            }
            // Ajoutez ici d'autres validations si nécessaire
        }

        // Validation des prix des articles
        for (ItemCommandeEntity item : commandeEntity.getItemsCommande()) {
            if (item.getIdProduitRevendeur().getPrix().compareTo(BigDecimal.ZERO) <= 0) {
                errors.reject("prix", "Le prix de l'article doit être supérieur à 0");
            }
            // Ajoutez ici d'autres validations si nécessaire
        }
    }


}



