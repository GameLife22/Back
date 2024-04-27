package fr.sqli.formation.gamelife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableConfigurationProperties
@SpringBootApplication
public class GamelifeApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GamelifeApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GamelifeApplication.class, args);
    }
}