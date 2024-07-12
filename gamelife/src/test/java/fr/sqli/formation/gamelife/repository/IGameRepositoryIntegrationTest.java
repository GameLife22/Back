package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.TestContainerConfiguration;
import fr.sqli.formation.gamelife.entity.GameEntity;
import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("test")
class IGameRepositoryIntegrationTest {

    private final IGameRepository iGameRepository;

    private GameEntity gameEntity;

    @Autowired
    IGameRepositoryIntegrationTest(IGameRepository pIGameRepository) {
        iGameRepository = pIGameRepository;
    }
    
    @BeforeEach
    void setUp() {
        this.gameEntity = new GameEntity();
        this.gameEntity.setId(null);
        this.gameEntity.setTitle("name");
        this.gameEntity.setDescription("description");
        this.gameEntity.setGenres(Set.of(Genre.ARCADE, Genre.ADVENTURE));
        this.gameEntity.setPlatforms(Set.of(Platform.PC, Platform.PLAYSTATION));
        this.gameEntity.setImages(List.of("https://image1.png", "file://image2.jpg"));
    }
    
    @Test
    void givenGameEntity_Save_thenReturnGameEntitySaved() {
        GameEntity gameEntitySaved = this.iGameRepository.save(this.gameEntity);

        Assertions.assertNotNull(this.gameEntity);
        Assertions.assertNotNull(gameEntitySaved);
        Assertions.assertEquals(this.gameEntity.getId(), gameEntitySaved.getId());
        Assertions.assertEquals(this.gameEntity.getTitle(), gameEntitySaved.getTitle());
        Assertions.assertEquals(this.gameEntity.getDescription(), gameEntitySaved.getDescription());
        Assertions.assertEquals(this.gameEntity.getGenres(), gameEntitySaved.getGenres());
        Assertions.assertEquals(this.gameEntity.getPlatforms(), gameEntitySaved.getPlatforms());
        Assertions.assertEquals(this.gameEntity.getImages(), gameEntitySaved.getImages());
    }
    
    @Test
    void givenGameId_whenFindById_thenReturnExistingOptionalGameEntity() {
        GameEntity gameEntitySaved = this.iGameRepository.save(gameEntity);
        Optional<GameEntity> optionalGameEntity = this.iGameRepository.findById(gameEntitySaved.getId());

        Assertions.assertNotNull(optionalGameEntity);
        Assertions.assertTrue(optionalGameEntity.isPresent());
        Assertions.assertNotNull(optionalGameEntity.get().getId());
        Assertions.assertEquals(gameEntitySaved.getId(), optionalGameEntity.get().getId());
        Assertions.assertEquals(gameEntitySaved.getTitle(), optionalGameEntity.get().getTitle());
        Assertions.assertEquals(gameEntitySaved.getDescription(), optionalGameEntity.get().getDescription());
        Assertions.assertEquals(gameEntitySaved.getGenres(), optionalGameEntity.get().getGenres());
        Assertions.assertEquals(gameEntitySaved.getPlatforms(), optionalGameEntity.get().getPlatforms());
        Assertions.assertEquals(gameEntitySaved.getImages(), optionalGameEntity.get().getImages());
    }

    @Test
    void givenGameName_whenFindByName_thenReturnExistingOptionalGameEntity() {
        GameEntity gameEntitySaved = this.iGameRepository.save(gameEntity);
        Optional<GameEntity> optionalGameEntity = this.iGameRepository.findByTitle(gameEntitySaved.getTitle());

        Assertions.assertNotNull(optionalGameEntity);
        Assertions.assertTrue(optionalGameEntity.isPresent());
        Assertions.assertNotNull(optionalGameEntity.get().getId());
        Assertions.assertEquals(gameEntitySaved.getId(), optionalGameEntity.get().getId());
        Assertions.assertEquals(gameEntitySaved.getTitle(), optionalGameEntity.get().getTitle());
        Assertions.assertEquals(gameEntitySaved.getDescription(), optionalGameEntity.get().getDescription());
        Assertions.assertEquals(gameEntitySaved.getGenres(), optionalGameEntity.get().getGenres());
        Assertions.assertEquals(gameEntitySaved.getPlatforms(), optionalGameEntity.get().getPlatforms());
        Assertions.assertEquals(gameEntitySaved.getImages(), optionalGameEntity.get().getImages());
    }

    @Test
    void givenGameEntity_whenFindAll_thenReturnGamesEntities() {
        this.iGameRepository.save(gameEntity);

        List<GameEntity> gamesEntities = this.iGameRepository.findAll();

        Assertions.assertNotNull(gamesEntities);
        Assertions.assertFalse(gamesEntities.isEmpty());
    }

    @Test
    void givenGameEntityUpdated_whenSave_thenReturnGameEntityUpdated() {
        GameEntity gameEntitySaved = this.iGameRepository.save(gameEntity);

        GameEntity gameEntityUpdated = new GameEntity();
        gameEntityUpdated.setId(gameEntitySaved.getId());
        gameEntityUpdated.setTitle("updated name");
        gameEntityUpdated.setDescription("updated description");
        gameEntityUpdated.setGenres(Set.of(Genre.BOARD_GAMES, Genre.INDIE));
        gameEntityUpdated.setPlatforms(Set.of(Platform.GAME_BOY, Platform.XBOX));
        gameEntityUpdated.setImages(List.of("https://image3.png", "file://image4.jpg"));

        
        Assertions.assertNotNull(gameEntitySaved);
        Assertions.assertNotNull(gameEntityUpdated);
        Assertions.assertEquals(gameEntitySaved.getId(), gameEntityUpdated.getId());
        Assertions.assertNotEquals(gameEntitySaved.getTitle(), gameEntityUpdated.getTitle());
        Assertions.assertNotEquals(gameEntitySaved.getDescription(), gameEntityUpdated.getDescription());
        Assertions.assertNotEquals(gameEntitySaved.getGenres(), gameEntityUpdated.getGenres());
        Assertions.assertNotEquals(gameEntitySaved.getPlatforms(), gameEntityUpdated.getPlatforms());
        Assertions.assertNotEquals(gameEntitySaved.getImages(), gameEntityUpdated.getImages());
    }

    @Test
    void givenGameId_whenDelete_thenReturnNothing() {
        GameEntity gameEntitySaved = this.iGameRepository.save(gameEntity);

        this.iGameRepository.deleteById(gameEntitySaved.getId());
        Optional<GameEntity> optionalGameEntity = this.iGameRepository.findById(gameEntitySaved.getId());

        Assertions.assertTrue(optionalGameEntity.isEmpty());
    }

    @Test
    void givenGamesIds_whenDeleteAllByIdIn_thenReturnNothing() {
        GameEntity gameEntitySaved1 = this.iGameRepository.save(this.gameEntity);

        GameEntity gameEntitySaved2 = new GameEntity();
        gameEntitySaved2.setTitle("name 2");
        gameEntitySaved2.setDescription("description 2");
        gameEntitySaved2.setGenres(Set.of(Genre.CARD, Genre.CASUAL));
        gameEntitySaved2.setPlatforms(Set.of(Platform.NES, Platform.SEGA_32X));
        gameEntitySaved2.setImages(List.of("https://image3.png", "file://image4.jpg"));

        this.iGameRepository.save(gameEntitySaved2);
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