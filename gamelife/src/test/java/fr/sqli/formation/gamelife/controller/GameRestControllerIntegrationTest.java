package fr.sqli.formation.gamelife.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sqli.formation.gamelife.TestContainerConfiguration;
import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Import(TestContainerConfiguration.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class GameRestControllerIntegrationTest {

    private MockMvc mockMvc;

    private GameRequest gameRequest;

    private final ObjectMapper objectMapper;

    private static final String PREFIX_API_URL = "/api/v1";

    @Autowired
    GameRestControllerIntegrationTest(MockMvc pMockMvc, ObjectMapper pObjectMapper) {
        this.mockMvc = pMockMvc;
        objectMapper = pObjectMapper;
    }

    @BeforeEach
    public void setUp() {;
        this.gameRequest = new GameRequest();
        this.gameRequest.setName("name");
        this.gameRequest.setDescription("description");
        this.gameRequest.setGenres(Set.of(Genre.ARCADE, Genre.ADVENTURE));
        this.gameRequest.setPlatforms(Set.of(Platform.PC, Platform.PLAYSTATION));
        this.gameRequest.setImages(List.of("https://image1.png", "file://image2.jpg"));
    }

    @Test
    void CreateGame_ReturnHttpStatusCreated() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(gameRequest.getName()))
                .andExpect(jsonPath("$.description").value(gameRequest.getDescription()));
        //todo: add the others fields
    }

    @Test
    void CreateGame_ReturnHttpStatusBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)));

        this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                    .contentType("application/json")
                    .accept("application/json")
                    .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void GetGameById_ReturnHttpStatusOk() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                    .contentType("application/json")
                    .accept("application/json")
                    .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        GameResponse gameResponse = this.objectMapper.readValue(json, GameResponse.class);

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/{id}", gameResponse.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void GetGameById_ReturnHttpStatusNotFound() throws Exception {
        UUID gameId = UUID.randomUUID();
        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/{id}", gameId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void GetGameByName_ReturnHttpStatusOk() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        GameResponse gameResponse = this.objectMapper.readValue(json, GameResponse.class);

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/search?name={name}", gameResponse.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void GetGameByName_ReturnHttpStatusNotFound() throws Exception {
        String gameName = RandomStringUtils.randomAlphabetic(20); // warning: max = 50

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/search?name={name}", gameName))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void GetGamesByPage_ReturnHttpStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                .contentType("application/json")
                .accept("application/json")
                .content(this.objectMapper.writeValueAsString(this.gameRequest)));

        int page = 0;
        int size = 5; // total element

        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games?page={page}&size={size}", page, size))
                .andExpect(MockMvcResultMatchers.status().isOk());;
    }

    @Test
    void UpdateGame_ReturnHttpStatusOk() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        GameRequest gameRequestUpdated = this.objectMapper.readValue(json, GameRequest.class);
        gameRequestUpdated.setName("name updated");
        gameRequestUpdated.setDescription("description updated");
        gameRequestUpdated.setGenres(Set.of(Genre.BOARD_GAMES, Genre.PUZZLE));
        gameRequestUpdated.setPlatforms(Set.of(Platform.GAMECUBE, Platform.XBOX));
        gameRequestUpdated.setImages(List.of("https://image3.png", "file://image4.jpg"));

        this.mockMvc.perform(MockMvcRequestBuilders.patch(PREFIX_API_URL + "/games/{id}", gameRequestUpdated.getId())
                        .contentType("application/json")
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(gameRequestUpdated)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void UpdateGame_ReturnHttpStatusNotFound() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        GameRequest gameRequestUpdated = this.objectMapper.readValue(json, GameRequest.class);
        gameRequestUpdated.setName("name updated");
        gameRequestUpdated.setDescription("description updated");
        gameRequestUpdated.setGenres(Set.of(Genre.BOARD_GAMES, Genre.PUZZLE));
        gameRequestUpdated.setPlatforms(Set.of(Platform.GAMECUBE, Platform.XBOX));
        gameRequestUpdated.setImages(List.of("https://image3.png", "file://image4.jpg"));

        UUID gameId = UUID.randomUUID();

        this.mockMvc.perform(MockMvcRequestBuilders.patch(PREFIX_API_URL + "/games/{id}", gameId)
                        .contentType("application/json")
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(gameRequestUpdated)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void DeleteGame_ReturnHttpStatusNoContent() throws Exception {
        var result = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        GameResponse gameResponse = this.objectMapper.readValue(json, GameResponse.class);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(PREFIX_API_URL + "/games/{id}", gameResponse.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void DeleteGame_ReturnHttpStatusBadRequest() throws Exception {
        UUID gameId = UUID.randomUUID();
        this.mockMvc.perform(MockMvcRequestBuilders.delete(PREFIX_API_URL + "/games/{id}", gameId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void DeleteGames_ReturnHttpStatusNoContent() throws Exception {
        var result1 = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(this.gameRequest)))
                .andReturn();

        String json1 = result1.getResponse().getContentAsString();
        GameResponse gameResponse1 = this.objectMapper.readValue(json1, GameResponse.class);

        GameRequest gameRequest2 = new GameRequest();
        gameRequest2.setName("name 2");
        gameRequest2.setDescription("description");
        gameRequest2.setGenres(Set.of(Genre.ARCADE, Genre.ADVENTURE));
        gameRequest2.setPlatforms(Set.of(Platform.PC, Platform.PLAYSTATION));
        gameRequest2.setImages(List.of("https://image1.png", "file://image2.jpg"));

        var result2 = this.mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_API_URL + "/games")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(this.objectMapper.writeValueAsString(gameRequest2)))
                .andReturn();

        String json2 = result2.getResponse().getContentAsString();
        GameResponse gameResponse2 = this.objectMapper.readValue(json2, GameResponse.class);

        List<UUID> gamesIds = List.of(gameResponse1.getId(), gameResponse2.getId());

        this.mockMvc.perform(MockMvcRequestBuilders.delete(PREFIX_API_URL + "/games")
                .contentType("application/json")
                .accept("application/json")
                .content(this.objectMapper.writeValueAsString(gamesIds)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void DeleteGames_ReturnHttpStatusBadRequest() throws Exception {
        UUID gameId1 = UUID.randomUUID();
        UUID gameId2 = UUID.randomUUID();
        List<UUID> gamesIds = List.of(gameId1, gameId2);
        this.mockMvc.perform(MockMvcRequestBuilders.delete(PREFIX_API_URL + "/games", gamesIds))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void GetGameGenres_ReturnHttpStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/genres"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void GetGamePlatforms_ReturnHttpStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_API_URL + "/games/platforms"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}