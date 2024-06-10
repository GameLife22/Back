package fr.sqli.formation.gamelife.dto.request;

import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Unit test class for GameRequest with active profiles set to "test".
 */
@ActiveProfiles("test")
class GameRequestUnitTest {

    /**
     * Validates a GameRequest object to ensure that it is valid and does not contain any constraint violations.
     * Returns an empty set of constraint violations if the GameRequest is valid without any errors.
     */
    @Test
    @DisplayName("Validate a valid GameRequest without errors")
    void GameRequest_ValidationNoErrors_ReturnEmptyConstraintViolation() {
        GameRequest gameRequest = getValidGameRequest();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<GameRequest>> constraintViolations = validator.validate(gameRequest);

        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    /**
     * Provides a stream of arguments representing fields and their corresponding null values for a GameRequest object.
     * Each argument consists of the field name and a null value to be set for that field.
     *
     * @return A stream of arguments, each containing the field name and a null value to be set for that field.
     */
    private static Stream<Arguments> provideFieldAndNullValue() {
        return Stream.of(
                Arguments.of("name", null),
                Arguments.of("description", null),
                Arguments.of("genres", null),
                Arguments.of("platforms", null),
                Arguments.of("images", null)
        );
    }

    /**
     * Provides a stream of arguments representing invalid values for different fields in a GameRequest object.
     * Each argument consists of the field name and a list of invalid values for that field.
     *
     * @return A stream of arguments, each containing the field name and a list of invalid values for that field.
     */
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

    /**
     * Validates a GameRequest object with null values for the specified field based on the provided field name and null value.
     *
     * @param pFieldName The name of the field to set as null.
     * @param pInvalidValue The null value to set for the specified field.
     */
    @ParameterizedTest
    @MethodSource("provideFieldAndNullValue")
    @DisplayName("Validate a valid GameRequest with null values")
    void GameRequest_ValidationErrors_ReturnConstraintViolation(String pFieldName, Object pInvalidValue) {
        validateInvalidField(pFieldName, pInvalidValue);
    }

    /**
     * Validates a GameRequest object with invalid values for the specified field based on the provided field name and list of invalid values.
     *
     * @param pFieldName The name of the field to set as invalid.
     * @param pInvalidValues A list of invalid values to set for the specified field.
     */
    @ParameterizedTest
    @MethodSource("provideFieldAndInvalidValue")
    @DisplayName("Validate a valid GameRequest with invalid values")
    void GameRequest_ValidationErrors_ReturnConstraintViolation(String pFieldName, List<Object> pInvalidValues) {
        pInvalidValues.forEach(invalidValue -> validateInvalidField(pFieldName, invalidValue));
    }

    /**
     * Validates a GameRequest object with an invalid field value based on the provided field name and invalid value.
     *
     * @param pFieldName The name of the field to set as invalid.
     * @param pInvalidValue The invalid value to set for the specified field.
     * @throws IllegalArgumentException if the provided field name is not valid.
     */
    private void validateInvalidField(String pFieldName, Object pInvalidValue) {
        GameRequest gameRequest = createGameRequestWithInvalidField(pFieldName, pInvalidValue);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<GameRequest>> constraintViolations = validator.validate(gameRequest);
        Assertions.assertFalse(constraintViolations.isEmpty());
    }

    /**
     * Creates and returns a valid GameRequest object with predefined values for testing purposes.
     *
     * @return A GameRequest object with the name "validName", description "validDescription",
     * genres set to [ARCADE, ADVENTURE], platforms set to [PC, PLAYSTATION],
     * and images containing "https://image1.png" and "file://image2.jpg".
     */
    private GameRequest getValidGameRequest() {
        GameRequest gameRequest = new GameRequest();
        gameRequest.setName("validName");
        gameRequest.setDescription("validDescription");
        gameRequest.setGenres(Set.of(Genre.ARCADE, Genre.ADVENTURE));
        gameRequest.setPlatforms(Set.of(Platform.PC, Platform.PLAYSTATION));
        gameRequest.setImages(List.of("https://image1.png", "file://image2.jpg"));
        return gameRequest;
    }

    /**
     * Creates a new GameRequest object with an invalid field value based on the provided field name and invalid value.
     *
     * @param pFieldName The name of the field to set as invalid.
     * @param pInvalidValue The invalid value to set for the specified field.
     * @return A new GameRequest object with the specified field set to the invalid value.
     * @throws IllegalArgumentException if the provided field name is not valid.
     */
    private GameRequest createGameRequestWithInvalidField(String pFieldName, Object pInvalidValue) {
        GameRequest gameRequest = new GameRequest();
        switch (pFieldName) {
            case "name":
                gameRequest.setName((String) pInvalidValue);
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