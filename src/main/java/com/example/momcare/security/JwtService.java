package com.example.momcare.security;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

//    private String secretKey = "qfkuo1TaJ03fhdb8NlxJDdjhR9oXgmQP5yZiqjQuThXCTeiMFhd3UJWs5tCRaZ6sL6zlNqPh4Q3o8/unHOFsMgJEagjLvO89g+Vw+Y9xzLKWS8QmvJZ5u6I3rAVRHxHlR/C2horSffDQXRZTsoK+sqXTnJvTzPbFBEVmUGjNesWS3oQxwW24Hi6sLFsvnLrzuE95zRpABtjgguJ1XKe7ZE+FFiHbJtKfHEO0FqW6KJl6N/91BZcIz0HR5C2YTbzvR/WytqYhu3cT9xFZMPfi611F1RCoBYqEhRUZ1ec7yhH+ALvQeeFbfSyBN4gVhPOy6HtgyK+07dNFA4MgIKmPG5CaiTv5bYGezIDmziLF3uE=";
//    public String extractUsername(String token){
//        return extractClaim(token, Claims::getSubject);
//    }
//    public String generateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//    public String generateToken(
//            Map<String, Object> extraClaims,
//            UserDetails userDetails
//    ) {
//        return String.valueOf(Jwts
//                .builder()
//                .setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*24))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact());
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//    private Claims extractAllClaims(String token){
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignInKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Key getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
}
