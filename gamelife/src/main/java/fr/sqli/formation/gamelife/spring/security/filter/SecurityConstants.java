package fr.sqli.formation.gamelife.spring.security.filter;

public interface SecurityConstants {

    public static final String contextRoot = "/auth";
    public static final String AUTH_LOGIN_URL = "/signin";
    public static final String AUTH_LOGOUT_URL = "/logout";


    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_ROLES = "role";
    public static final String TOKEN_USER = "user";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";
}
