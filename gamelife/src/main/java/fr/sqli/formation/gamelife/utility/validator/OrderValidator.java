package fr.sqli.formation.gamelife.utility.validator;

import fr.sqli.formation.gamelife.entity.OrderEntity;
import fr.sqli.formation.gamelife.entity.ItemOrderEntity;
import fr.sqli.formation.gamelife.enumeration.OrderStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//todo: to remove
@Component
public class OrderValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderEntity.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        OrderEntity orderEntity = (OrderEntity) target;

        // Validation de l'état de la commande
        if (!orderEntity.getEtat().equals(OrderStatus.EN_ATTENTE_PAIEMENT)) {
            errors.rejectValue("etat", "invalid", "La commande n'est pas dans un état valide pour être validée");
        }


        // Validation des articles dans la commande
        List<ItemOrderEntity> itemsCommande = orderEntity.getItemsCommande();
        if (itemsCommande == null || itemsCommande.isEmpty()) {
            errors.reject("emptyItems", "La commande ne contient aucun article");
        }
        // Validation de la date de commande
        if (orderEntity.getDate().isAfter(LocalDate.now())) {
            errors.reject("invalidDate", "La date de commande est dans le futur");
        }

        // Validation des quantités minimales/maximales des articles
        for (ItemOrderEntity item : orderEntity.getItemsCommande()) {
            if (item.getQuantite() < 1) {
                errors.reject("quantite", "La quantité de l'article ne peut pas être inférieure à 1");
            }
            // Ajoutez ici d'autres validations si nécessaire
        }

        // Validation des prix des articles
        for (ItemOrderEntity item : orderEntity.getItemsCommande()) {
            if (item.recupererProduitRevendeur().getPrix().compareTo(BigDecimal.ZERO) <= 0) {
                errors.reject("prix", "Le prix de l'article doit être supérieur à 0");
            }
            // Ajoutez ici d'autres validations si nécessaire
        }
    }


}



