package fr.sqli.formation.gamelife.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "glproduct_seller", schema = "gamelife")
public class SellerGameEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;


    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Getter
    @Column(name = "price", nullable = false, precision = 10)
    private BigDecimal prix;

    @Column(name = "status", nullable = false, length = 25)
    private String etat;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity produit;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity utilisateur;


}