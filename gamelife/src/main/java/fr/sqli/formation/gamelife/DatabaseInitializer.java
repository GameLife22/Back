package fr.sqli.formation.gamelife;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * DatabaseInitializer class that implements CommandLineRunner interface.
 * This class is responsible for initializing the database by running a Python script to insert rawg games.
 */
@Component
class DatabaseInitializer implements CommandLineRunner {

    private final Environment environment;

    public DatabaseInitializer(Environment environment) {
        this.environment = environment;
    }

    /**
     * Method that runs a Python script to insert rawg games into the database if the active profile is "dev".
     * Uses ProcessBuilder to start the Python script and waits for it to finish.
     *
     * @param args The arguments passed to the method
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void run(String... args) throws IOException {
        if (environment.getActiveProfiles().length > 0 && environment.getActiveProfiles()[0].equals("dev")) {
            ProcessBuilder processBuilder = new ProcessBuilder("python", "./src/main/resources/insert_rawg_games.py");
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}