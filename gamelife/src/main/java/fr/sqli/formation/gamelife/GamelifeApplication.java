package fr.sqli.formation.gamelife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class GamelifeApplication {

  @Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);
	}
	public static void main(String[] args) {
		SpringApplication.run(GamelifeApplication.class, args);
	}

}
