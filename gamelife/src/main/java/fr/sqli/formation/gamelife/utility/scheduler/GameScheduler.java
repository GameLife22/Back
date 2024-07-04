package fr.sqli.formation.gamelife.utility.scheduler;

import fr.sqli.formation.gamelife.exception.GameExistsException;
import fr.sqli.formation.gamelife.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class representing a game scheduler utility for inserting games from API RAWG to a database.
 */
@Component
public class GameScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameScheduler.class);

    private GameService gameService;

    @Autowired
    public GameScheduler(GameService pGameService) {
        gameService = pGameService;
    }

    @Scheduled(initialDelay = 1000)
    public void insertGamesFromApiRawgToDatabaseTask() throws GameExistsException {
        this.gameService.createGamesFromApiRawg();
    }
}