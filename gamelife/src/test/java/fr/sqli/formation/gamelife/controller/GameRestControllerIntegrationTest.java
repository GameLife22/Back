package fr.sqli.formation.gamelife.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sqli.formation.gamelife.TestContainerConfiguration;
import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
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

import java.util.*;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Import(TestContainerConfiguration.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Rollback
@ActiveProfiles("test")
class GameRestControllerIntegrationTest {

    private final MockMvc mockMvc;

    private final TokenService tokenService;

    private GameRequest gameRequest;

    private final ObjectMapper objectMapper;

    private static final String PREFIX_API_URL = "/api/v1";

    private String adminToken;

    @Autowired
    GameRestControllerIntegrationTest(MockMvc pMockMvc, TokenService pTokenService, ObjectMapper pObjectMapper) {
        this.mockMvc = pMockMvc;
        tokenService = pTokenService;
        objectMapper = pObjectMapper;
    }

    @BeforeEach
    public void setUp() throws Exception {;
        this.gameRequest = new GameRequest();
        this.gameRequest.setName("name");
        this.gameRequest.setDescription("description");
        this.gameRequest.setGenres(Set.of(Genre.ARCADE, Genre.ADVENTURE));
        this.gameRequest.setPlatforms(Set.of(Platform.PC, Platform.PLAYSTATION));
        this.gameRequest.setImages(List.of("https://image1.png", "file://image2.jpg"));

        adminToken = generateToken("admin@gamelife.fr", "ROLE_ADMIN");
    }

    private String generateToken(String username, String role) throws Exception {
        var authentication = new TestingAuthenticationToken(username, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
        return tokenService.generateToken(authentication);
    }

    @Test
    void givenGameRequest_whenCreateGame_thenReturnHttpStatusCreated() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(gameRequest.getName()))
                .andExpect(jsonPath("$.description").value(gameRequest.getDescription()))
                .andExpect(jsonPath("$.genres").isArray())
                .andExpect(jsonPath("$.genres").value(hasItem(Genre.ARCADE.getName())))
                .andExpect(jsonPath("$.genres").value(hasItem(Genre.ADVENTURE.getName())))
                .andExpect(jsonPath("$.platforms").isArray())
                .andExpect(jsonPath("$.platforms").value(hasItem(Platform.PC.getName())))
                .andExpect(jsonPath("$.platforms").value(hasItem(Platform.PLAYSTATION.getName())));
    }

    @Test
    void givenGameName_whenCreateGame_thenReturnHttpStatusBadRequest() throws Exception {
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
    void givenGameId_whenGetGameById_thenReturnHttpStatusOk() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        GameResponse gameResponse = this.objectMapper.readValue(json, GameResponse.class);

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/{id}", gameResponse.getId())
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenInvalidGameId_whenGetGameById_thenReturnHttpStatusNotFound() throws Exception {
        UUID gameId = UUID.randomUUID();
        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/{id}", gameId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenGameName_whenGetGameByName_thenReturnHttpStatusOk() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        GameResponse gameResponse = this.objectMapper.readValue(json, GameResponse.class);

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/search?name={name}", gameResponse.getName())
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenInvalidGameName_whenGetGameByName_thenReturnHttpStatusNotFound() throws Exception {
        String gameName = RandomStringUtils.randomAlphabetic(20); // warning: max = 50

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/search?name={name}", gameName)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenPageAndSizeOfGame_whenGetGamesByPage_thenReturnHttpStatusOk() throws Exception {
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
    void givenGameRequestUpdated_whenUpdateGame_thenReturnHttpStatusOk() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        GameResponse gameResponse = this.objectMapper.readValue(jsonResponse, GameResponse.class);

        GameRequest gameRequest = new GameRequest();
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
    void givenInvalidGameRequestUpdated_whenUpdateGame_thenReturnHttpStatusNotFound() throws Exception {
        GameRequest gameRequest = new GameRequest();
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
    void givenGameId_whenDeleteGameById_thenReturnHttpStatusNoContent() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        GameResponse gameResponse = this.objectMapper.readValue(json, GameResponse.class);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(PREFIX_API_URL + "/games/{id}", gameResponse.getId())
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void givenInvalidGameId_whenDeleteGameById_thenReturnHttpStatusBadRequest() throws Exception {
        UUID gameId = UUID.randomUUID();
        this.mockMvc.perform(MockMvcRequestBuilders.delete(PREFIX_API_URL + "/games/{id}", gameId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenNothing_whenGetGameGenres_thenReturnHttpStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/genres")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenNothing_whenGetGamePlatforms_thenReturnHttpStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/platforms")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}