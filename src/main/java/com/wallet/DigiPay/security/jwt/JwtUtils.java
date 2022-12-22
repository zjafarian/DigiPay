package com.wallet.DigiPay.security.jwt;


import com.wallet.DigiPay.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils implements Serializable {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${access.secret.key}")
  private String secretKey;

  @Value("${access.secret.key.validityInMinutes}")
  private int secretKeyExpirationMs;

  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();


    return Jwts.builder().claim("nationalCode" , userPrincipal.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + secretKeyExpirationMs))
            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)))
            .compact();
  }

  public String getUserNameFromJwtToken(String token) {

    String username="";
    try {
      username = getClaimFromToken(token, Claims::getSubject);
      //username = claims.getSubject();
    } catch (Exception e) {
      username = null;
    }
    return username;



    //return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    //SecretKey key = Keys.hmacShaKeyFor(encodedKeyBytes);
    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
   Claims claims =  Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

  public boolean validateJwtToken(String authToken) {





    try {

        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }






}
