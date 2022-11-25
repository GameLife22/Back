package fr.sqli.formation.gamelife.spring.security.filter;

import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter implements SecurityConstants{

    private static final Logger LOG = LogManager.getLogger();

    private final byte[] signingKey;
    private Object token;

    public JwtAuthorizationFilter(AuthenticationManager AuthenticationManager, Environment Env) {
        super(AuthenticationManager);
        this.signingKey = Env.getProperty("configuration.jwt.key",
                "-KaPdSgVkXp2s5v8y/B?E(H+MbQeThWmZq3t6w9z$C&F)J@NcRfUjXn2r5u7x!A%").getBytes();
    }

    /**
     * Checks for token validity.
     *
     * @param request     the request
     * @param response    the response
     * @param filterChain filters
     * @throws IOException      if an error occurred
     * @throws ServletException if an error occurred
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        final var remoteIP = request.getRemoteAddr();
        final var url = request.getRequestURL().toString();

        //On récupére le token du header
        var jwtToken = request.getHeader(SecurityConstants.TOKEN_HEADER);

        JwtAuthorizationFilter.LOG.info("[{}] <-- JwtAuthorizationFilter.doFilterInternal - {} - JWT token is {}", remoteIP, url, jwtToken);

        if (jwtToken==null|| !StringUtils.hasLength(jwtToken) || !jwtToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            JwtAuthorizationFilter.LOG.warn("[{}] <-- JwtAuthorizationFilter.doFilterInternal - {} - JWT token is Empty", remoteIP, url);
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = this.getAuthentication(jwtToken, remoteIP);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtAuthorizationFilter.LOG.info("[{}] <-- JwtAuthorizationFilter.doFilterInternal - {} - OK - Set authentication back", remoteIP, url);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token, String remoteIp) {

        JwtAuthorizationFilter.LOG.info("[{}] --> JwtAuthorizationFilter.getAuthentication - Token - {}", remoteIp, token);
        try {
            //On transforme le Jwt en token spring
            var parsedToken = Jwts.parserBuilder().setSigningKey(this.signingKey).build().
                    parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""));

            //on recupere l'email qu'on a utilisé à l'authetification
            var email = parsedToken.getBody().getSubject();


            if (StringUtils.hasLength(email)) {
                Collection<? extends GrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
                        .get(SecurityConstants.TOKEN_ROLES)).stream()
                        .map(authority -> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());

                var resu = new UsernamePasswordAuthenticationToken(email, null, authorities);
                @SuppressWarnings("unchecked")
                Integer idUser = (Integer) parsedToken.getBody().get(SecurityConstants.TOKEN_USER);
                JwtAuthorizationFilter.LOG.info("val {}", idUser);
                resu.setDetails(idUser);
                JwtAuthorizationFilter.LOG.warn(
                        "[{}] <-- JwtAuthorizationFilter.getAuthentication - Token was pushed into Spring Security, {}",
                        remoteIp, resu);
                return resu;
            }

        } catch (Exception exception) {
            JwtAuthorizationFilter.LOG.warn("[{}] - JwtAuthorizationFilter.getAuthentication : {} failed", remoteIp, token, exception);
        }
        return null;
    }
}
