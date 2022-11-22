package fr.sqli.formation.gamelife;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SpringSecurity {
	private static final Logger LOG = LogManager.getLogger();

	private static final String[] ALL_METHODS = new String[] { CorsConfiguration.ALL, HttpMethod.GET.name(),
			HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(),
			HttpMethod.PATCH.name(), HttpMethod.OPTIONS.name(), HttpMethod.TRACE.name() };

	private static final String[] ALL_HEADERS = new String[] { CorsConfiguration.ALL, "Access-Control-Allow-Headers",
			"WWW-Authenticate", "Access-Control-Allow-Origin", "Origin,Accept", "X-Requested-With", "Content-Type",
			"Access-Control-Request-Method", "Access-Control-Request-Headers" };

	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		LOG.info("---- Loading CORS");
		var config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern(CorsConfiguration.ALL); // "*"

		config.setAllowedHeaders(Arrays.asList(ALL_HEADERS));
		config.setAllowedMethods(Arrays.asList(ALL_METHODS));
		config.setExposedHeaders(Arrays.asList(ALL_HEADERS));
		config.setMaxAge(Duration.of(2, ChronoUnit.HOURS));

		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}
	
	@Bean
	@DependsOn("corsConfigurationSource")
	public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager)
			throws Exception {
		LOG.info("---- SpringSecurity - Apply rules");

		// Keep cors enable here, otherwise configuration of it is not applied
		http.csrf().disable().cors();

		// For H2
		http.headers().frameOptions().disable();
		
		// For logout, simply send 200
		http.authorizeRequests().and().logout().clearAuthentication(true)
				.logoutSuccessHandler((pRequest, pResponse, pAuthentication) -> pResponse.setStatus(200));

		// No login, and no logout
		http.authorizeRequests().and().formLogin().disable().httpBasic().disable();

		http.authorizeRequests().anyRequest().permitAll();
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
