package fr.sqli.formation.gamelife.utility.converter;

import com.google.gson.*;
import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.entity.GameEntity;
import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Interface for converting GameRequest and GameEntity objects to each other,
 * as well as converting lists of these objects.
 * Contains static methods for conversion operations.
 */
public interface IGameConverter {

    static final Logger LOGGER = LoggerFactory.getLogger(IGameConverter.class);

    static final String RAWG_API_KEY = "fc6fc9da02854e66b8d2d0115d28c680";

    static final String URL_RAWG = "https://api.rawg.io/api/games/";
    /**
     * Converts a GameRequest object to a GameEntity object.
     *
     * @param pGameRequest The GameRequest object to be converted
     * @return The converted GameEntity object
     */
    public static GameEntity convertGameRequestToGameEntity(GameRequest pGameRequest) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(pGameRequest.getId());
        gameEntity.setName(pGameRequest.getName());
        gameEntity.setDescription(pGameRequest.getDescription());
        gameEntity.setGenres(pGameRequest.getGenres());
        gameEntity.setPlatforms(pGameRequest.getPlatforms());
        gameEntity.setImages(pGameRequest.getImages());
        return gameEntity;
    }

    /**
     * Converts a GameEntity object to a GameResponse object.
     *
     * @param pGameEntity The GameEntity object to be converted
     * @return The converted GameResponse object
     */
    public static GameResponse convertGameEntityToGameResponse(GameEntity pGameEntity) {
        GameResponse gameResponse = new GameResponse();
        gameResponse.setId(pGameEntity.getId());
        gameResponse.setName(pGameEntity.getName());
        gameResponse.setDescription(pGameEntity.getDescription());
        gameResponse.setGenres(pGameEntity.getGenres());
        gameResponse.setPlatforms(pGameEntity.getPlatforms());
        gameResponse.setImages(pGameEntity.getImages());
        return gameResponse;
    }

    /**
     * Converts a Page of GameEntity objects to a Page of GameResponse objects.
     *
     * @param pGamesEntities The Page of GameEntity objects to be converted
     * @return The converted Page of GameResponse objects
     */
    public static Page<GameResponse> convertGamesEntitiesToPageGamesResponses(Page<GameEntity> pGamesEntities) {
        List<GameResponse> gamesResponses = pGamesEntities.stream()
                .map(IGameConverter::convertGameEntityToGameResponse)
                .toList();

        Pageable pageable = pGamesEntities.getPageable();
        return new PageImpl<>(gamesResponses, pageable, pGamesEntities.getTotalElements());
    }

    /**
     * Converts a list of GameEntity objects to a list of GameResponse objects.
     *
     * @param pGamesEntities The list of GameEntity objects to be converted
     * @return The converted list of GameResponse objects
     */
    public static List<GameResponse> convertGamesEntitiesToGamesResponse(List<GameEntity> pGamesEntities) {
        return pGamesEntities.stream()
                .map(IGameConverter::convertGameEntityToGameResponse)
                .toList();
    }

    /**
     * Retrieves game details from the RAWG API for a specific game ID.
     *
     * @param pGameId The ID of the game to fetch details for
     * @return A Mono emitting a GameRequest object representing the game details
     */
    public static Mono<GameRequest> convertGameDetailsFromApiRawg(int pGameId) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        String url = URL_RAWG + pGameId + "?key=" + RAWG_API_KEY;

        WebClient client = WebClient.builder()
                .baseUrl(URL_RAWG)
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", url))
                .build();

        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(url);
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("data");

        WebClient.ResponseSpec responseSpec = headersSpec.header(
                        HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve();

        LOGGER.info("Initiating API call to fetch game details for game ID: {}", pGameId);

        Mono<String> responseMono = responseSpec
                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> {
                            if (clientResponse.statusCode().value() == 404) {
                                LOGGER.warn("Resource not found for game ID {}: {}", pGameId, url);
                            } else if (clientResponse.statusCode().value() == 502) {
                                LOGGER.error("Bad gateway encountered for game ID {}: {}", pGameId, url);
                            } else {
                                LOGGER.error("Error fetching game details for game ID {}: {}", pGameId, clientResponse.statusCode());
                            }
                            return Mono.empty();
                        })
                .bodyToMono(String.class);

        return responseMono.flatMap(response -> {
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
            GameRequest game = convertJsonObjectToGameEntity(jsonObject);
            LOGGER.info("Successfully fetched game details for game ID: {}", pGameId);
            return Mono.just(game);
        }).onErrorResume(error -> {
            LOGGER.error("Error fetching game details for game ID {}: {}", pGameId, error.getMessage());
            return Mono.empty();
        });
    }

    /**
     * Retrieves images for a specific game ID from the RAWG API.
     *
     * @param pGameId The ID of the game to fetch images for
     * @return A Mono emitting a list of image URLs
     */
    public static Mono<List<String>> convertImagesFromApiRawg(int pGameId) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        String url = URL_RAWG + pGameId + "/screenshots?key=" + RAWG_API_KEY;

        WebClient client = WebClient.builder()
                .baseUrl(URL_RAWG)
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", url))
                .build();

        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(url);
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("data");

        WebClient.ResponseSpec responseSpec = headersSpec.header(
                        HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve();

        LOGGER.info("Initiating API call to fetch images for game ID: {}", pGameId);

        Mono<String> responseMono = responseSpec
                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> {
                            if (clientResponse.statusCode().value() == 404) {
                                LOGGER.warn("Images not found for game ID {}: {}", pGameId, url);
                            } else if (clientResponse.statusCode().value() == 502) {
                                LOGGER.error("Bad gateway encountered for game ID {}: {}", pGameId, url);
                            } else {
                                LOGGER.error("Error fetching images for game ID {}: {}", pGameId, clientResponse.statusCode());
                            }
                            return Mono.empty();
                        })
                .bodyToMono(String.class);

        return responseMono.flatMap(response -> {
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
            List<String> images = convertJsonObjectToImagesInGameEntity(jsonObject);
            LOGGER.info("Successfully fetched images for game ID: {}", pGameId);
            return Mono.just(images);
        }).onErrorResume(error -> {
            LOGGER.error("Error fetching images for game ID {}: {}", pGameId, error.getMessage());
            return Mono.empty();
        });
    }

    /**
     * Converts a JSON object representing game details to a GameRequest object.
     *
     * @param pJsonObject The JSON object containing game details
     * @return A GameRequest object with the extracted game details
     */
    private static GameRequest convertJsonObjectToGameEntity(JsonObject pJsonObject) {
        LOGGER.debug("Converting JSON object to GameEntity");

        GameRequest gameRequest = new GameRequest();
        gameRequest.setName(pJsonObject.get("name").getAsString());
        gameRequest.setDescription(pJsonObject.get("description").getAsString().replaceAll("<[^>]*>", ""));

        Set<Genre> genres = new HashSet<>();
        JsonArray genresArray = pJsonObject.getAsJsonArray("genres");
        for (JsonElement genreElement : genresArray) {
            String genre = genreElement.getAsJsonObject()
                    .get("name")
                    .getAsString()
                    .replace(" ", "_")
                    .toUpperCase();
            genres.add(Genre.valueOf(genre));
        }
        gameRequest.setGenres(genres);

        Set<Platform> platforms = new HashSet<>();
        JsonArray platformsArray = pJsonObject.getAsJsonArray("platforms");
        for (JsonElement platformElement : platformsArray) {
            JsonObject platformJsonObject = platformElement.getAsJsonObject();
            String platform = platformJsonObject.get("platform")
                    .getAsJsonObject()
                    .get("name")
                    .getAsString()
                    .replace(" ", "_")
                    .replace("/", "")
                    .replace(" / ", "")
                    .toUpperCase();
            platforms.add(Platform.valueOf(platform));
        }
        gameRequest.setPlatforms(platforms);

        LOGGER.debug("Conversion completed successfully");
        return gameRequest;
    }

    /**
     * Converts a JSON object representing images in a GameEntity to a list of image URLs.
     *
     * @param pJsonObject The JSON object containing images data
     * @return A list of image URLs extracted from the JSON object
     */
    private static List<String> convertJsonObjectToImagesInGameEntity(JsonObject pJsonObject) {
        LOGGER.debug("Converting JSON object to images in GameEntity");

        List<String> images = new ArrayList<>();
        JsonArray imagesArray = pJsonObject.getAsJsonArray("results");
        for (JsonElement imageElement : imagesArray) {
            String image = imageElement.getAsJsonObject()
                    .get("image")
                    .getAsString();
            images.add(image);
        }

        LOGGER.debug("Images conversion completed successfully");
        return images;
    }
}