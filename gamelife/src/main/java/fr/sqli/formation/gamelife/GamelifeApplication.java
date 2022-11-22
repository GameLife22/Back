package fr.sqli.formation.gamelife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GamelifeApplication {





	public static void main(String[] args) {
		SpringApplication.run(GamelifeApplication.class, args);
	}

}
