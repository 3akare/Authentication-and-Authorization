package com.bakare.authentication_services.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String SECRET_KEY = "lBnHnh7/1bRw7+TdRhUKYkhKoOrtoBpgaSTEe2/a1aHOmQ5c+rVX4ZQJiDt7Y354lUq8DqOZrxv3Gx7mw3ZfdprPWiwJH5bT1r58ek0+l92uMzIZ2rj4ya1oJyd2D/WAlU9PBDx0w0JfJ14rMWKdMRUnooPMAeqgwIecTkZkHT5z0tpYyF+eZ+VUAKe/bTAJ4Rq88tcJAuvfVzLxOveVtm/JQgNSIzgZyU6BwBKFb4s+uqAHx3qsIyvzc7DhF9/Pm8cumz+O5Vndhuq142YmV3DXQLwOlDbt1Cme4Y/fnsoSYQxoBaYXMRYrL/sp4NrJb0kjCt3OB1Z3vE3mEalM"; // BASE64 encoded secret

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails);
    }

    public long getExpirationTime() {
        //    @Value("${security.jwt.expiration-time}")
        return 3600000;
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + getExpirationTime()))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
