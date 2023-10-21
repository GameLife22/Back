package fr.sqli.formation.gamelife;

import fr.sqli.formation.gamelife.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class GamelifeApplication {
	public static void main(String[] args) {
		SpringApplication.run(GamelifeApplication.class, args);

	}

}
