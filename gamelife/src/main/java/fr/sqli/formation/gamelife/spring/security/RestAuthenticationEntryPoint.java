package fr.sqli.formation.gamelife.spring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        RestAuthenticationEntryPoint.LOG.error("{} --> 401 <--- From AuthenticationEntryPoint for {}",
                request.getRemoteAddr(), request.getRequestURL(), authException);

        // We want our Json Exception model instead of the one in Spring
        //TODO
        //var out = new ExceptionDtoOut(authException);
        var objectMapper = new ObjectMapper();
        //TODO DtoException
        var expToJson = objectMapper.writeValueAsString("Erreur Auth");
        var pw = response.getWriter();
        pw.write(expToJson);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
