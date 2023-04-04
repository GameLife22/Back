package fr.sqli.formation.gamelife.spring.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sqli.formation.gamelife.dto.login.LoginDtoIn;
import fr.sqli.formation.gamelife.ex.AuthentificationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter  extends UsernamePasswordAuthenticationFilter implements SecurityConstants {

    private static final Logger LOG = LogManager.getLogger();

    private final Environment env;

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(Environment env, AuthenticationManager authenticationManager) {
        LOG.info("JWT AuthenticationFilter");
        this.authenticationManager = authenticationManager;
        this.setFilterProcessesUrl(SecurityConstants.contextRoot+SecurityConstants.AUTH_LOGIN_URL);
        this.env = env;
    }


    /**
     * Can handle email,password as parameter or as JSOn in body
     * ({"email":"xxx","password":"xxx"}).
     *
     * @param request  the request
     * @param response the response
     * @throws AuthenticationException if an error occurred
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("JWT AuthenticationFilter - attemptAuthentication");

        //Adresse IP
        final var remoteIP = request.getRemoteAddr();

        //email et mdp qu'on a mis dans le body
        var email = request.getParameter("email");
        var password = request.getParameter("password");

        //condition pour faire le choix de verifier  les donnees soit en body ou en parametre
        if (!StringUtils.hasLength(email) && !StringUtils.hasLength(password)) {
            JwtAuthenticationFilter.LOG.info("[{}] --> JwtAuthenticationFilter.attemptAuthentication(email, password) as Json in Body", remoteIP);
            //C'est bon les donnees sont dans le body
            // Look as JSon in the body
            String body = null;
            try {
                body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                // Level is in trace but we should hide password if present in body or if in
                // production
                JwtAuthenticationFilter.LOG.trace("[{}] --> JwtAuthenticationFilter.attemptAuthentication(email, password) Body={}", remoteIP, body);
                var mapper = new ObjectMapper();
                var loginDto = mapper.readValue(body, LoginDtoIn.class);
                email = loginDto.getLogin();
                password = loginDto.getPwd();
            } catch (Exception lExp) {
                JwtAuthenticationFilter.LOG.error("[{}] --> JwtAuthenticationFilter.attemptAuthentication - Error, your JSon is not right!, found {}, should be something like {\"email\":\"toto@gmail.com\",\"password\":\"bonjour\"}. DO NOT use simple quote!",
                        remoteIP, body, lExp);
            }
        } else {
            JwtAuthenticationFilter.LOG.info(
                    "[{}] --> JwtAuthenticationFilter.attemptAuthentication(email, password) as parameter", remoteIP);
        }

        JwtAuthenticationFilter.LOG.info("[{}] --> JwtAuthenticationFilter.attemptAuthentication({}, [PROTECTED])",
                remoteIP, email);

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        try {
            var result = this.authenticationManager.authenticate(authenticationToken);
            JwtAuthenticationFilter.LOG.info(
                    "[{}] --> JwtAuthenticationFilter.attemptAuthentication is ok - User id is {}", remoteIP, result.getDetails().toString());
            return result;
        } catch (AuthenticationException e) {
            throw new AuthentificationException("Authentication failed for user " + email + " - " + e.getMessage(), e);
        }


    }

    /**
     * Will build the JWT Token if authentication is ok.
     *
     * @param request        the request
     * @param response       the response
     * @param filterChain    filters
     * @param authentication email/pwd information taken from AuthenticationManager
     * @throws AuthenticationException if an error occurred
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {
        LOG.info("JWT AuthenticationFilter - successfulAuthentication");
        final var remoteIP = request.getRemoteAddr();
        var userName = (String) authentication.getPrincipal();
        LOG.info("JWT AuthenticationFilter - successfulAuthentication  -- username : {}",userName);

        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        for (String r: roles) {
            LOG.info("JWT AuthenticationFilter - successfulAuthentication  -- roles : {}",r);

        }
        var ue =  authentication.getDetails();

        //payload du token*************
        Claims claims = new DefaultClaims();
        claims.put(SecurityConstants.TOKEN_USER, ue);
        claims.put(SecurityConstants.TOKEN_ROLES, roles);
        claims.setIssuer(SecurityConstants.TOKEN_ISSUER);
        claims.setAudience(SecurityConstants.TOKEN_AUDIENCE);
        claims.setSubject(userName);
        var val = Integer.parseInt(this.env.getProperty("configuration.jwt.expire.in.ms", "86400000"));
        claims.setExpiration(new Date(System.currentTimeMillis() + val));
        //*******************************************

        var signatureAlgorithm = SignatureAlgorithm
                .forName(this.env.getProperty("configuration.jwt.signature.algorithm", "none"));

        JwtBuilder builder = null;
        if (signatureAlgorithm == null || signatureAlgorithm == SignatureAlgorithm.NONE) {
            JwtAuthenticationFilter.LOG.warn("[{}] - No encryption for JWT token, this is good for testing ...", remoteIP);
            builder = Jwts.builder().setHeaderParam("typ", SecurityConstants.TOKEN_TYPE).setClaims(claims);
        } else {
            JwtAuthenticationFilter.LOG.info(
                    "Encryption for JWT token is {}, do not forget to set your key in the configuration file",
                    signatureAlgorithm);
            // Sample key is for HS512
            var signingKey = this.env.getProperty("configuration.jwt.key",
                    "-KaPdSgVkXp2s5v8y/B?E(H+MbQeThWmZq3t6w9z$C&F)J@NcRfUjXn2r5u7x!A%").getBytes();
            builder = Jwts.builder().signWith(Keys.hmacShaKeyFor(signingKey), signatureAlgorithm)
                    .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE).setClaims(claims);
        }

        //Le token est cree
        var token = builder.compact();

        //le token est insere dans le header
        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
    }

}

