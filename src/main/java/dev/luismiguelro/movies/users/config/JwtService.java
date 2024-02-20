package dev.luismiguelro.movies.users.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {
    private static final String secretKey = "iTV7espFYRZwM44FcNmJvgj55SkvIr8obvpyx8Qgkn48bfg74Sg7XT+/ebnP1i9s";

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    //extract a single claim
    public <T> T extractClaim (String token, Function<Claims,T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    //generate token
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + 1000 * 60 * 24);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(extraClaims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    // validate token
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
