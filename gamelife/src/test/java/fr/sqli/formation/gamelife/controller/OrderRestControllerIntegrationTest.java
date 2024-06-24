package fr.sqli.formation.gamelife.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sqli.formation.gamelife.TestContainerConfiguration;
import fr.sqli.formation.gamelife.dto.request.OrderRequest;
import fr.sqli.formation.gamelife.dto.request.OrderRequest;
import fr.sqli.formation.gamelife.dto.response.OrderResponse;
import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;
import fr.sqli.formation.gamelife.service.TokenService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
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

    private static final String PREFIX_API_URL = "/api/v1";

    private String adminToken;

    @Autowired
    OrderRestControllerIntegrationTest(MockMvc pMockMvc, TokenService pTokenService, ObjectMapper pObjectMapper) {
        this.mockMvc = pMockMvc;
        tokenService = pTokenService;
        objectMapper = pObjectMapper;
    }

    @BeforeEach
    public void setUp() throws Exception {;
        this.orderRequest = new OrderRequest();
        //todo: add values for attributes

        adminToken = generateToken("acheteur@gamelife.fr", "ROLE_ACHETEUR");
    }

    private String generateToken(String username, String role) throws Exception {
        var authentication = new TestingAuthenticationToken(username, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
        return tokenService.generateToken(authentication);
    }

    @Test
    void givenOrderRequest_whenCreateOrder_thenReturnHttpStatusCreated() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(this.objectMapper.writeValueAsString(this.orderRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").exists());
                //todo: check all attributes in OrderResponse
    }

    @Test
    void givenOrderName_whenCreateOrder_thenReturnHttpStatusBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                .contentType("application/json")
                .header("Authorization", "Bearer " + adminToken)
                .accept("application/json")
                .content(this.objectMapper.writeValueAsString(this.gameRequest)));

        this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenOrderId_whenGetOrderById_thenReturnHttpStatusOk() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        OrderResponse gameResponse = this.objectMapper.readValue(json, OrderResponse.class);

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/{id}", gameResponse.getId())
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenInvalidOrderId_whenGetOrderById_thenReturnHttpStatusNotFound() throws Exception {
        UUID gameId = UUID.randomUUID();
        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/{id}", gameId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenOrderName_whenfindByNameContainingIgnoreCase_thenReturnHttpStatusOk() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        OrderResponse gameResponse = this.objectMapper.readValue(json, OrderResponse.class);

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/search?name={name}", gameResponse.getName())
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenInvalidOrderName_whenFindByNameContainingIgnoreCase_thenReturnHttpStatusNotFound() throws Exception {
        String gameName = RandomStringUtils.randomAlphabetic(4); // warning: max = 50

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/search?name={name}", gameName)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenPageAndSizeOfOrder_whenGetOrdersByPage_thenReturnHttpStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                .contentType("application/json")
                .accept("application/json")
                .header("Authorization", "Bearer " + adminToken)
                .content(this.objectMapper.writeValueAsString(this.gameRequest)));

        int page = 0; // first page
        int size = 5; // total elements

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenOrderRequestUpdated_whenUpdateOrder_thenReturnHttpStatusOk() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        OrderResponse gameResponse = this.objectMapper.readValue(jsonResponse, OrderResponse.class);

        OrderRequest gameRequest = new OrderRequest();
        gameRequest.setName("updated name");
        gameRequest.setDescription("updated description");
        gameRequest.setGenres(Set.of(Genre.PUZZLE));
        gameRequest.setPlatforms(Set.of(Platform.ATARI_XEGS));
        gameRequest.setImages(List.of("https://image3.png", "file://image4.jpg"));

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/{id}", gameResponse.getId())
                        .contentType("application/json")
                        .accept("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(this.objectMapper.writeValueAsString(gameRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenInvalidOrderRequestUpdated_whenUpdateOrder_thenReturnHttpStatusNotFound() throws Exception {
        OrderRequest gameRequest = new OrderRequest();
        gameRequest.setName("updated name");
        gameRequest.setDescription("updated description");
        gameRequest.setGenres(Set.of(Genre.PUZZLE));
        gameRequest.setPlatforms(Set.of(Platform.ATARI_XEGS));
        gameRequest.setImages(List.of("https://image3.png", "file://image4.jpg"));

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/{id}", UUID.randomUUID())
                        .contentType("application/json")
                        .accept("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(this.objectMapper.writeValueAsString(gameRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenOrderId_whenDeleteOrderById_thenReturnHttpStatusNoContent() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        OrderResponse gameResponse = this.objectMapper.readValue(json, OrderResponse.class);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(PREFIX_API_URL + "/games/{id}", gameResponse.getId())
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void givenInvalidOrderId_whenDeleteOrderById_thenReturnHttpStatusBadRequest() throws Exception {
        UUID gameId = UUID.randomUUID();
        this.mockMvc.perform(MockMvcRequestBuilders.delete(PREFIX_API_URL + "/games/{id}", gameId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenNothing_whenGetOrderGenres_thenReturnHttpStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/genres")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenNothing_whenGetOrderPlatforms_thenReturnHttpStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/platforms")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}