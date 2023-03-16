package fr.sqli.formation.gamelife.spring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sqli.formation.gamelife.dto.springSecurity.ExceptionDtoOut;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.System.out;

class AccesDeniedHandler implements AccessDeniedHandler {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        AccesDeniedHandler.LOG.error("{} --> 403 <--- From AccesDeniedHandler for {}", request.getRemoteAddr(),
                request.getRequestURL(), exception);

        // We want our Json Exception model instead of the one in Spring
        var out = new ExceptionDtoOut(exception);
        var objectMapper = new ObjectMapper();
        var expToJson = objectMapper.writeValueAsString(out);
        var pw = response.getWriter();
        pw.write(expToJson);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    }
}