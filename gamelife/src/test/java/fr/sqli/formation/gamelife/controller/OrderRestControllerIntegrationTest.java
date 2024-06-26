package fr.sqli.formation.gamelife.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sqli.formation.gamelife.TestContainerConfiguration;
import fr.sqli.formation.gamelife.dto.request.ItemOrderRequest;
import fr.sqli.formation.gamelife.dto.request.OrderRequest;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

    private String adminToken;

    @Autowired
    OrderRestControllerIntegrationTest(MockMvc pMockMvc, TokenService pTokenService, ObjectMapper pObjectMapper) {
        this.mockMvc = pMockMvc;
        tokenService = pTokenService;
        objectMapper = pObjectMapper;
    }


    @BeforeEach
    public void setUp() throws Exception {
        this.orderRequest = new OrderRequest();
        this.orderRequest.setIdUtilisateur(UUID.randomUUID());
        this.orderRequest.setEtat(OrderStatus.NOUVELLE);
        this.orderRequest.setNumRueLivraison(123);
        this.orderRequest.setRueLivraison("Example Street");
        this.orderRequest.setVilleLivraison("Example City");
        this.orderRequest.setCodePostalLivraison(12345);
        this.orderRequest.setDate(LocalDate.now());
        List<ItemOrderRequest> items = new ArrayList<>();
        this.orderRequest.setItemsCommande(items);


        adminToken = generateToken("admin@gamelife.fr", "ROLE_ADMIN");
    }

    private String generateToken(String username, String role) throws Exception {
        var authentication = new TestingAuthenticationToken(username, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
        return tokenService.generateToken(authentication);
    }


    @Test
    void testValiderCommande_Success() throws Exception {
        UUID orderId = UUID.randomUUID();
        this.orderRequest.setId(orderId);

        this.mockMvc.perform(MockMvcRequestBuilders.put(PREFIX_API_URL + "/{idCommande}/valider-commande", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + adminToken)
                        .content(this.objectMapper.writeValueAsString(this.orderRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(orderId.toString()))
                .andExpect(jsonPath("$.idUtilisateur").value(orderRequest.getIdUtilisateur()))
                .andExpect(jsonPath("$.etat").value(OrderStatus.EN_COURS_DE_TRAITEMENT.getName()))
                .andExpect(jsonPath("$.numRueLivraison").value(orderRequest.getNumRueLivraison()))
                .andExpect(jsonPath("$.rueLivraison").value(orderRequest.getRueLivraison()))
                .andExpect(jsonPath("$.villeLivraison").value(orderRequest.getVilleLivraison()))
                .andExpect(jsonPath("$.codePostalLivraison").value(orderRequest.getCodePostalLivraison()))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.itemsCommande").isArray());
    }


    @Test
    void testValiderCommande_OrderNotFound() throws Exception {
        // Simuler une requête HTTP PUT avec un ID de commande inexistant
        UUID nonExistingId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.put(PREFIX_API_URL + "/{idCommande}/valider-commande", nonExistingId)
                        .contentType("application/json")
                        .accept("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(this.objectMapper.writeValueAsString(this.orderRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Commande non trouvée avec l'ID : " + nonExistingId.toString()))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.path").value("/commande/" + nonExistingId.toString() + "/valider-commande"));

    }



}