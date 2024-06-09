package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.TestContainerConfiguration;
import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.entity.GameEntity;
import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;
import fr.sqli.formation.gamelife.utility.converter.IGameConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Integration test class for IGameRepository.
 */
@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("test")
class IGameRepositoryIntegrationTest {
    @Autowired
    private IGameRepository iGameRepository;

    private GameEntity gameEntity;

    /**
     * Set up the test environment by creating a GameRequest object with predefined values
     * and converting it to a GameEntity object using the IGameConverter utility class.
     */
    @BeforeEach
    void setUp() {
        GameRequest gameRequest = new GameRequest(
                null, "name",
                "description",
                Set.of(Genre.ARCADE, Genre.ADVENTURE),
                Set.of(Platform.PC, Platform.PLAYSTATION),
                List.of("https://image1.png", "file://image2.jpg")
        );

        this.gameEntity = IGameConverter.convertGameRequestToGameEntity(gameRequest);
    }

    /**
     * Test saving a GameEntity in GameRepository should return the saved GameEntity.
     *
     * This test method saves a GameEntity object using the IGameRepository save method and then asserts that the saved GameEntity
     * matches the original GameEntity in terms of ID, name, description, genres, platforms, and images.
     *
     * @see IGameRepository
     * @see GameEntity
     */
    @Test
    @DisplayName("Test saving a GameEntity in GameRepository should return GameEntity saved")
    void GameRepository_Save_ReturnGameEntity() {
        GameEntity gameEntitySaved = this.iGameRepository.save(this.gameEntity);

        Assertions.assertNotNull(this.gameEntity);
        Assertions.assertNotNull(gameEntitySaved);
        Assertions.assertEquals(this.gameEntity.getId(), gameEntitySaved.getId());
        Assertions.assertEquals(this.gameEntity.getName(), gameEntitySaved.getName());
        Assertions.assertEquals(this.gameEntity.getDescription(), gameEntitySaved.getDescription());
        Assertions.assertEquals(this.gameEntity.getGenres(), gameEntitySaved.getGenres());
        Assertions.assertEquals(this.gameEntity.getPlatforms(), gameEntitySaved.getPlatforms());
        Assertions.assertEquals(this.gameEntity.getImages(), gameEntitySaved.getImages());
    }

    /**
     * Test to find a game by its ID in the game repository and return an Optional containing the GameEntity.
     *
     * This test method saves a GameEntity object in the repository and then attempts to find it by its ID.
     * It asserts that the returned Optional is not null, is present, and contains the expected GameEntity with matching ID, name, description, genres, platforms, and images.
     */
    @Test
    @DisplayName("Test to find by ID and return Optional<GameEntity>")
    void GameRepository_FindById_ReturnOptionalGameEntity() {
        GameEntity gameEntitySaved = this.iGameRepository.save(gameEntity);
        Optional<GameEntity> optionalGameEntity = this.iGameRepository.findById(gameEntitySaved.getId());

        Assertions.assertNotNull(optionalGameEntity);
        Assertions.assertTrue(optionalGameEntity.isPresent());
        Assertions.assertNotNull(optionalGameEntity.get().getId());
        Assertions.assertEquals(gameEntitySaved.getId(), optionalGameEntity.get().getId());
        Assertions.assertEquals(gameEntitySaved.getName(), optionalGameEntity.get().getName());
        Assertions.assertEquals(gameEntitySaved.getDescription(), optionalGameEntity.get().getDescription());
        Assertions.assertEquals(gameEntitySaved.getGenres(), optionalGameEntity.get().getGenres());
        Assertions.assertEquals(gameEntitySaved.getPlatforms(), optionalGameEntity.get().getPlatforms());
        Assertions.assertEquals(gameEntitySaved.getImages(), optionalGameEntity.get().getImages());
    }

    /**
     * Test to verify that the findAll method in the GameRepository returns a list of GameEntity objects.
     *
     * This test method saves a GameEntity object using the IGameRepository save method and then calls the findAll method to retrieve a list of GameEntity objects.
     * It asserts that the returned list is not null and is not empty.
     */
    @Test
    @DisplayName("Test to verify findAll method returns Game entities")
    void GameRepository_FindAll_ReturnGamesEntities() {
        this.iGameRepository.save(gameEntity);

        List<GameEntity> gamesEntities = this.iGameRepository.findAll();

        Assertions.assertNotNull(gamesEntities);
        Assertions.assertFalse(gamesEntities.isEmpty());
    }

    /**
     * Test updating a GameEntity in GameRepository should return the updated GameEntity.
     *
     * This test method verifies that when updating a GameEntity in the GameRepository,
     * the method should return the GameEntity that has been updated with the new information.
     * It performs the following steps:
     * 1. Saves the original GameEntity in the repository.
     * 2. Creates a new GameRequest with updated information.
     * 3. Converts the GameRequest to a new GameEntity.
     * 4. Compares the original GameEntity with the updated GameEntity to ensure that:
     *    - The IDs are the same.
     *    - The names are different.
     *    - The descriptions are different.
     *    - The genres are different.
     *    - The platforms are different.
     *    - The images are different.
     *
     * @see IGameRepositoryIntegrationTest
     */
    @Test
    @DisplayName("Test updating GameEntity in GameRepository should return GameEntity updated")
    void GameRepository_Update_ReturnGameEntityUpdated() {
        GameEntity gameEntitySaved = this.iGameRepository.save(gameEntity);

        GameRequest gameRequest = new GameRequest(
                gameEntitySaved.getId(),
                "name updated",
                "description updated",
                Set.of(Genre.BOARD_GAMES, Genre.INDIE),
                Set.of(Platform.GAME_BOY, Platform.XBOX),
                List.of("https://image3.png", "file://image4.jpg")
        );

        GameEntity gameEntityUpdated = IGameConverter.convertGameRequestToGameEntity(gameRequest);

        Assertions.assertNotNull(gameEntitySaved);
        Assertions.assertNotNull(gameEntityUpdated);
        Assertions.assertEquals(gameEntitySaved.getId(), gameEntityUpdated.getId());
        Assertions.assertNotEquals(gameEntitySaved.getName(), gameEntityUpdated.getName());
        Assertions.assertNotEquals(gameEntitySaved.getDescription(), gameEntityUpdated.getDescription());
        Assertions.assertNotEquals(gameEntitySaved.getGenres(), gameEntityUpdated.getGenres());
        Assertions.assertNotEquals(gameEntitySaved.getPlatforms(), gameEntityUpdated.getPlatforms());
        Assertions.assertNotEquals(gameEntitySaved.getImages(), gameEntityUpdated.getImages());
    }

    /**
     * Test deleting a game by its ID in the game repository and verifying that nothing is returned.
     *
     * This test method saves a GameEntity object in the repository, deletes it by its ID using the deleteById method,
     * and then attempts to find it by the same ID. It asserts that the returned Optional is empty, indicating that
     * the game has been successfully deleted from the repository.
     */
    @Test
    @DisplayName("Test deleting by ID should return nothing")
    void GameRepository_DeleteById_ReturnNothing() {
        GameEntity gameEntitySaved = this.iGameRepository.save(gameEntity);

        this.iGameRepository.deleteById(gameEntitySaved.getId());
        Optional<GameEntity> optionalGameEntity = this.iGameRepository.findById(gameEntitySaved.getId());

        Assertions.assertTrue(optionalGameEntity.isEmpty());
    }

    /**
     * Test deleting by Ids in GameRepository should return nothing.
     *
     * This method tests the functionality of deleting multiple GameEntity objects by their IDs in the GameRepository.
     * It saves two GameEntity objects, retrieves their IDs, and then deletes them using the deleteAllByIdIn method.
     * Finally, it verifies that both GameEntity objects have been successfully deleted by checking if they are not present in the repository.
     *
     * Assertions:
     * - Verifies that the first GameEntity object was saved successfully.
     * - Verifies that the second GameEntity object was saved successfully.
     * - Verifies that the first GameEntity object has been deleted and is not present in the repository.
     * - Verifies that the second GameEntity object has been deleted and is not present in the repository.
     */
    @Test
    @DisplayName("Test deleting by Ids in GameRepository should return nothing")
    void GameRepository_DeleteAllByIdIn_ReturnNothing() {
        GameEntity gameEntitySaved1 = this.iGameRepository.save(gameEntity);

        GameRequest newGameRequest = new GameRequest(
                null, "name 2",
                "description 2",
                Set.of(Genre.CARD, Genre.CASUAL),
                Set.of(Platform.NES, Platform.SEGA_32X),
                List.of("https://image3.png", "file://image4.jpg")
        );

        GameEntity gameEntity = IGameConverter.convertGameRequestToGameEntity(newGameRequest);
        GameEntity gameEntitySaved2 = this.iGameRepository.save(gameEntity);
        List<UUID> gamesIds = List.of(gameEntitySaved1.getId(), gameEntitySaved2.getId());

        this.iGameRepository.deleteAllByIdIn(gamesIds);
        Optional<GameEntity> gameEntityDeleted1 = this.iGameRepository.findById(gameEntitySaved1.getId());
        Optional<GameEntity> gameEntityDeleted2 = this.iGameRepository.findById(gameEntitySaved2.getId());

        Assertions.assertNotNull(gameEntitySaved1);
        Assertions.assertNotNull(gameEntitySaved2);
        Assertions.assertTrue(gameEntityDeleted1.isEmpty());
        Assertions.assertTrue(gameEntityDeleted2.isEmpty());
    }
}