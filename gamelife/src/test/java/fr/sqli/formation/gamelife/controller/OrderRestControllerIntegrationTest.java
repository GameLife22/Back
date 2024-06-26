package fr.sqli.formation.gamelife.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sqli.formation.gamelife.TestContainerConfiguration;
import fr.sqli.formation.gamelife.dto.request.ItemOrderRequest;
import fr.sqli.formation.gamelife.dto.request.OrderRequest;
import fr.sqli.formation.gamelife.dto.response.ItemOrderResponse;
import fr.sqli.formation.gamelife.dto.response.OrderResponse;
import fr.sqli.formation.gamelife.enumeration.OrderStatus;
import fr.sqli.formation.gamelife.service.OrderService;
import fr.sqli.formation.gamelife.service.TokenService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;
@Import(TestContainerConfiguration.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Rollback
@ActiveProfiles("test")
class OrderRestControllerIntegrationTest {

    private final MockMvc mockMvc;

    private final TokenService tokenService;

    private OrderRequest orderRequest;

    private final ObjectMapper objectMapper;

    private static final String PREFIX_API_URL = "/commande";

    private String buyerToken;

    @Autowired
    OrderRestControllerIntegrationTest(MockMvc pMockMvc, TokenService pTokenService, ObjectMapper pObjectMapper) {
        this.mockMvc = pMockMvc;
        tokenService = pTokenService;
        objectMapper = pObjectMapper;
    }


    @BeforeEach
    public void setUp() throws Exception {
        this.orderRequest = new OrderRequest();
        this.orderRequest.setIdUtilisateur(UUID.fromString("c81f4beb-d17d-4b68-8a10-195745ddb894")); // ID of the buyer
        this.orderRequest.setEtat(OrderStatus.NOUVELLE);
        this.orderRequest.setNumRueLivraison(2);
        this.orderRequest.setRueLivraison("Rue du Marechal");
        this.orderRequest.setVilleLivraison("Nantes");
        this.orderRequest.setCodePostalLivraison(44000);
        this.orderRequest.setDate(LocalDate.of(2024, 5, 7)); // Specified date

        // Initialisation des articles de commande
        //List<ItemOrderRequest> items = new ArrayList<>();
        //items.add(new ItemOrderRequest(UUID.fromString("63ef0498-3148-4e57-a4f6-4c17a9ed9352"), 2)); // Utilisation correcte du constructeur
       // this.orderRequest.setItemsCommande(items);
        // Génération du token admin
        buyerToken = generateToken("acheteur@gamelife.fr", "ROLE_ACHETEUR");
    }

    private String generateToken(String username, String role) throws Exception {
        var authentication = new TestingAuthenticationToken(username, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
        return tokenService.generateToken(authentication);
    }
    @Test
    void testCreateOrder() throws Exception {
        // Simuler une requête HTTP POST pour créer une commande
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/creer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + buyerToken)
                        .content(this.objectMapper.writeValueAsString(this.orderRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        // Extraire le contenu de la réponse
        String responseContent = result.getResponse().getContentAsString();

        // Désérialiser le contenu de la réponse en un objet OrderRequest
        OrderRequest createdOrder = objectMapper.readValue(responseContent, OrderRequest.class);

        // Valider la réponse
        assertEquals(orderRequest.getIdUtilisateur(), createdOrder.getIdUtilisateur());
        assertEquals(orderRequest.getEtat(), createdOrder.getEtat());
        assertEquals(orderRequest.getNumRueLivraison(), createdOrder.getNumRueLivraison());
        assertEquals(orderRequest.getRueLivraison(), createdOrder.getRueLivraison());
        assertEquals(orderRequest.getVilleLivraison(), createdOrder.getVilleLivraison());
        assertEquals(orderRequest.getCodePostalLivraison(), createdOrder.getCodePostalLivraison());
        assertEquals(orderRequest.getDate(), createdOrder.getDate());
    }

    //Création de Commande avec des Dates Invalide
    @Test
    void testCreateOrderWithInvalidDate() throws Exception {
        // Utiliser une date de livraison passée
        orderRequest.setDate(LocalDate.of(2020, 1, 1));

        // Simuler une requête HTTP POST pour créer une commande
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/creer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + buyerToken)
                        .content(this.objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isBadRequest()) // Vérifier que le statut est 400 Bad Request
                .andReturn();

        // Vérifier le contenu de la réponse pour l'erreur de date
        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Date de livraison invalide"));
    }

    @Test
    void testAjoutProduit() throws Exception {
        UUID userId = UUID.fromString("ede28d8b-9170-4e8e-83b3-3c2c16c39ae8");
        UUID productSellerId = UUID.fromString("63ef0498-3148-4e57-a4f6-4c17a9ed9352");
        UUID orderId = UUID.fromString("01234567-89ab-cdef-0123-456789abcdef");

        // Prepare an item order request
        ItemOrderRequest itemOrderRequest = new ItemOrderRequest( productSellerId, 1);

        mockMvc.perform(MockMvcRequestBuilders.put("/commande/" + userId + "/ajout-produit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + buyerToken)
                        .content(objectMapper.writeValueAsString(itemOrderRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCommande").value(orderId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idProduitRevendeur").value(productSellerId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantite").value(1));
    }














    // Test de validation de commande
    @Test
    void testValiderCommande() throws Exception {
        // Créer une nouvelle commande pour pouvoir la valider
        MvcResult createResult = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/creer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + buyerToken)
                        .content(this.objectMapper.writeValueAsString(this.orderRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        // Extraire l'ID de la commande créée à partir de la réponse
        String createResponseContent = createResult.getResponse().getContentAsString();
        OrderRequest createdOrder = objectMapper.readValue(createResponseContent, OrderRequest.class);

        // Simuler une requête HTTP PUT pour valider la commande
        MvcResult validateResult = mockMvc.perform(MockMvcRequestBuilders.put(PREFIX_API_URL + "/valider-commande")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + buyerToken))
                .andExpect(status().isOk())
                .andReturn();

        // Extraire le contenu de la réponse de validation
        String validateResponseContent = validateResult.getResponse().getContentAsString();
        OrderResponse validatedOrder = objectMapper.readValue(validateResponseContent, OrderResponse.class);

        // Valider la réponse
        assertEquals(OrderStatus.EN_COURS_DE_TRAITEMENT, validatedOrder.getEtat());
    }



}