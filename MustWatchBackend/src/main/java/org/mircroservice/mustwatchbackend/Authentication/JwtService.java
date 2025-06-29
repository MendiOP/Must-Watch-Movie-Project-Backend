package org.mircroservice.mustwatchbackend.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 \* Service that handles the generation of JWT tokens using a secret key.
 */
@Service
public class JwtService {

    /**
     \* Holds the base64-encoded key for signing the tokens.
     */
    private String secret = "";

    /**
     \* Constructs a new JwtService, generating a fresh HmacSHA256 secret key.
     \* @throws NoSuchAlgorithmException if the required algorithm is not available
     */
    public JwtService() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = keyGen.generateKey();
        secret = Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     \* Generates a signed JWT token for the provided username.
     \* @param username the username associated with the token
     \* @return a completed JWT token string
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() * 60 * 60 * 30))
                .and()
                .signWith(getKey())
                .compact();
    }

    /**
     \* Retrieves and decodes the secret key for token signing.
     \* @return a cryptographic key derived from the base64 secret
     */
    private Key getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}