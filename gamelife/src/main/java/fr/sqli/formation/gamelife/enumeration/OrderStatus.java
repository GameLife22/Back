package fr.sqli.formation.gamelife.enumeration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
public enum OrderStatus {
    EN_ATTENTE_PAIEMENT ("En attente de paiement"),
    EN_COURS_DE_TRAITEMENT ("En cours de traitement"),
    EN_PREPARATION ("En préparation"),
    EXPEDIEE ("Expédiée"),
    LIVREE ("Livrée"),
    ANNULEE ("Annulée"),
    NOUVELLE ("Nouvelle");

    private final String name;

    OrderStatus(String pName) {
        this.name = pName;
    }

    @JsonValue // serialization
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves a OrderStatus based on the provided name.
     *
     * @param pName The name of the OrderStatus to retrieve.
     * @return The OrderStatus corresponding to the provided name.
     * @throws IllegalArgumentException if the provided name does not match any OrderStatus.
     */
    @JsonCreator // deserialization
    public static OrderStatus findOrderStatusByName(String pName) {
        for (OrderStatus genre : OrderStatus.values()) {
            if (genre.getName().equalsIgnoreCase(pName)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus: " + pName);
    }



    }