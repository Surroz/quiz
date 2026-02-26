package org.surro.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.surro.userservice.model.TokensPair;

import java.security.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    Map<String, Object> claims = new HashMap<>();
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private static final int REQUEST_TOKEN_LIFETIME = 1000 * 60 * 5;
    private static final int ACCESS_TOKEN_LIFETIME = 1000 * 60 * 60 * 24 * 7;

    public JwtService() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    public TokensPair createTokenPair(String userName) {
        String requestToken = createToken(userName, REQUEST_TOKEN_LIFETIME);
        String accessToken = createToken(userName, ACCESS_TOKEN_LIFETIME);
        return new TokensPair(requestToken, accessToken, ACCESS_TOKEN_LIFETIME);
    }

    public String createRequestToken(String accessToken) {
        return createToken(getUsername(accessToken), REQUEST_TOKEN_LIFETIME);
    }

    private String createToken(String userName, int tokenTime) {
        return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenTime))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
