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
 * Unit test class for GameRequest.
 */
@ActiveProfiles("test")
class GameRequestUnitTest {

    /**
     * Validates a GameRequest object to ensure no constraint violations are present.
     */
    @Test
    @DisplayName("Test valid GameRequest validation with no errors")
    void GameRequest_ValidationNoErrors_ReturnEmptyConstraintViolation() {
        GameRequest gameRequest = getValidGameRequest();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<GameRequest>> constraintViolations =
                validator.validate(gameRequest);

        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    /**
     * Provides a stream of arguments representing fields and their corresponding null values.
     *
     * @return a stream of arguments containing field names and null values
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
     * Provides a stream of arguments representing fields and their corresponding invalid values.
     *
     * @return a stream of arguments containing field names and corresponding invalid values
     */
    private static Stream<Arguments> provideFieldAndInvalidValue() {
        return Stream.of(
                Arguments.of("name", List.of("", " " , RandomStringUtils.randomAlphabetic(51))),
                Arguments.of("description", List.of("", " ", RandomStringUtils.randomAlphabetic(5001))),
                Arguments.of("genres", List.of(Set.of())),
                Arguments.of("platforms", List.of(Set.of())),
                Arguments.of("images",
                        List.of(new ArrayList<>(), List.of("http://example.com/image.jpg"), List.of("https://example.com/path/to/image.gif"),
                                List.of("https://example.com/path/to/image "), List.of("ftp://example.com/image.jpg"),
                                List.of("https://example.com/image.jpg?param=value&param2=value2#section"),
                                List.of("https://" + RandomStringUtils.randomAlphabetic(2084) + ".jpg") // more than 5000 characters
                        ))
        );
    }

    /**
     * Method to test an invalid game request by validating a specific field with an invalid value.
     */
    @ParameterizedTest
    @MethodSource("provideFieldAndNullValue")
    @DisplayName("Test invalid game request with null values")
    void GameRequest_ValidationErrors_ReturnConstraintViolation(String pFieldName, Object pInvalidValue) {
        validateInvalidField(pFieldName, pInvalidValue);
    }


    @ParameterizedTest
    @MethodSource("provideFieldAndInvalidValue")
    @DisplayName("Test invalid game request with invalid values")
    void GameRequest_ValidationErrors_ReturnConstraintViolation(String pFieldName, List<Object> pInvalidValues) {
        for (Object pInvalidValue : pInvalidValues) {
            validateInvalidField(pFieldName, pInvalidValue);
        }
    }

    /**
     * Validates a specific field of a GameRequest object with an invalid value and asserts that constraint violations are present.
     *
     * @param pFieldName the name of the field to be validated
     * @param pInvalidValue the invalid value to be set for the field
     */
    private void validateInvalidField(String pFieldName, Object pInvalidValue) {
        GameRequest gameRequest = createGameRequestWithInvalidField(pFieldName, pInvalidValue);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<GameRequest>> constraintViolations = validator.validate(gameRequest);
        Assertions.assertFalse(constraintViolations.isEmpty());
    }

    /**
     * Retrieves a valid GameRequest object with predefined values for testing purposes.
     *
     * @return a valid GameRequest object with a null ID, name "validName", description "validDescription",
     * genres {ARCADE, ADVENTURE}, platforms {PC, PLAYSTATION}, and images ["https://image1.png", "file://image2.jpg"]
     */
    private GameRequest getValidGameRequest() {
        return new GameRequest(null, "validName", "validDescription", Set.of(Genre.ARCADE, Genre.ADVENTURE), Set.of(Platform.PC, Platform.PLAYSTATION), List.of("https://image1.png", "file://image2.jpg"));
    }

    /**
     * Creates a new GameRequest object with a specific field set to an invalid value.
     *
     * @param pFieldName the name of the field to be set with the invalid value
     * @param pInvalidValue the invalid value to be set for the specified field
     * @return a new GameRequest object with the specified field set to the invalid value
     * @throws IllegalArgumentException if the provided field name is invalid
     */
    private GameRequest createGameRequestWithInvalidField(String pFieldName, Object pInvalidValue) {
        return switch (pFieldName) {
            case "name" -> new GameRequest(null, (String) pInvalidValue, "validDescription", Set.of(Genre.ARCADE, Genre.ADVENTURE), Set.of(Platform.PC, Platform.PLAYSTATION), List.of("https://image1.png", "file://image2.jpg"));
            case "description" -> new GameRequest(null, "validName", (String) pInvalidValue, Set.of(Genre.ARCADE, Genre.ADVENTURE), Set.of(Platform.PC, Platform.PLAYSTATION), List.of("https://image1.png", "file://image2.jpg"));
            case "genres" -> new GameRequest(null, "validName", "validDescription", (Set<Genre>) pInvalidValue, Set.of(Platform.PC, Platform.PLAYSTATION), List.of("https://image1.png", "file://image2.jpg"));
            case "platforms" -> new GameRequest(null, "validName", "validDescription", Set.of(Genre.ARCADE, Genre.ADVENTURE), (Set<Platform>) pInvalidValue, List.of("https://image1.png", "file://image2.jpg"));
            case "images" -> new GameRequest(null, "validName", "validDescription", Set.of(Genre.ARCADE, Genre.ADVENTURE), Set.of(Platform.PC, Platform.PLAYSTATION), (List<String>) pInvalidValue);
            default -> throw new IllegalArgumentException("Invalid field name: " + pFieldName);
        };
    }
}