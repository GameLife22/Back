package fr.sqli.formation.gamelife;

import fr.sqli.formation.gamelife.config.RsaKeyProperties;
import fr.sqli.formation.gamelife.validator.CommandeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class GamelifeApplication extends SpringBootServletInitializer {


    @Autowired
    private CommandeValidator commandeValidator;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GamelifeApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GamelifeApplication.class, args);
    }

}
