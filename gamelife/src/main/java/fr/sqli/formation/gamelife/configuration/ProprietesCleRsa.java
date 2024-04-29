package fr.sqli.formation.gamelife.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record ProprietesCleRsa(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}