package fr.sqli.formation.gamelife.utility.generator;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Interface for a secure token generator that uses a SecureRandom instance for generating secure tokens.
 */
public interface ISecureTokenGenerator {
    static final SecureRandom secureRandom = new SecureRandom();
    static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    /**
     * Generates a secure token of the specified byte length.
     *
     * @param byteLength the length of the token in bytes
     * @return a randomly generated secure token encoded in Base64
     */
    public static String generateToken(int byteLength) {
        byte[] randomBytes = new byte[byteLength];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}