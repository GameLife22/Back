package fr.sqli.formation.gamelife.dto.request;

import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@ActiveProfiles("test")
class GameRequestUnitTest {

    @Test
    void givenValidGameRequest_whenValidate_thenReturnEmptyConstraintViolation() {
        GameRequest gameRequest = getValidGameRequest();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<GameRequest>> constraintViolations = validator.validate(gameRequest);

        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideFieldAndNullValue")
    void givenNullFieldsGameRequest_whenValidate_thenReturnConstraintViolation(String pFieldName, Object pInvalidValue) {
        validateInvalidField(pFieldName, pInvalidValue);
    }

    @ParameterizedTest
    @MethodSource("provideFieldAndInvalidValue")
    void givenInvalidFieldsGameRequest_whenValidate_thenReturnConstraintViolation(String pFieldName, List<Object> pInvalidValues) {
        pInvalidValues.forEach(invalidValue -> validateInvalidField(pFieldName, invalidValue));
    }

    private static Stream<Arguments> provideFieldAndNullValue() {
        return Stream.of(
                Arguments.of("name", null),
                Arguments.of("description", null),
                Arguments.of("genres", null),
                Arguments.of("platforms", null),
                Arguments.of("images", null)
        );
    }

    private static Stream<Arguments> provideFieldAndInvalidValue() {
        return Stream.of(
                Arguments.of("name", List.of("", " ", RandomStringUtils.randomAlphabetic(51))),
                Arguments.of("description", List.of("", " ", RandomStringUtils.randomAlphabetic(5001))),
                Arguments.of("genres", List.of(Set.of())),
                Arguments.of("platforms", List.of(Set.of())),
                Arguments.of("images",
                        List.of(new ArrayList<>(), List.of("http://example.com/image.jpg"), List.of("https://example.com/path/to/image.gif"),
                                List.of("https://example.com/path/to/image "), List.of("ftp://example.com/image.jpg"),
                                List.of("https://example.com/image.jpg?param=value&param2=value2#section"),
                                List.of("https://" + RandomStringUtils.randomAlphabetic(2084) + ".jpg")
                        ))
        );
    }

    private void validateInvalidField(String pFieldName, Object pInvalidValue) {
        GameRequest gameRequest = createGameRequestWithInvalidField(pFieldName, pInvalidValue);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<GameRequest>> constraintViolations = validator.validate(gameRequest);
        Assertions.assertFalse(constraintViolations.isEmpty());
    }

    private GameRequest getValidGameRequest() {
        GameRequest gameRequest = new GameRequest();
        gameRequest.setId(UUID.randomUUID()); // uuid or null
        gameRequest.setTitle("validName");
        gameRequest.setDescription("validDescription");
        gameRequest.setGenres(Set.of(Genre.ARCADE, Genre.ADVENTURE));
        gameRequest.setPlatforms(Set.of(Platform.PC, Platform.PLAYSTATION));
        gameRequest.setImages(List.of("https://image1.png", "file://image2.jpg"));
        return gameRequest;
    }

    private GameRequest createGameRequestWithInvalidField(String pFieldName, Object pInvalidValue) {
        GameRequest gameRequest = new GameRequest();
        switch (pFieldName) {
            case "name":
                gameRequest.setTitle((String) pInvalidValue);
                break;
            case "description":
                gameRequest.setDescription((String) pInvalidValue);
                break;
            case "genres":
                gameRequest.setGenres((Set<Genre>) pInvalidValue);
                break;
            case "platforms":
                gameRequest.setPlatforms((Set<Platform>) pInvalidValue);
                break;
            case "images":
                gameRequest.setImages((List<String>) pInvalidValue);
                break;
            default:
                throw new IllegalArgumentException("Nom de champ invalide : " + pFieldName);
        }
        return gameRequest;
    }
}