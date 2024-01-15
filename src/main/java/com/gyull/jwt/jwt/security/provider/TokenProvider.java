package com.gyull.jwt.jwt.security.provider;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider implements InitializingBean {

  private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
  private static final String AUTHORITIES_KEY = "auth";
  private final String secret;
  private final long tokenValidityInMilliseconds;
  private Key key;

  //application.yml과 같은 설정파일에서 값을 읽어오려면 lombok.value가 아니라
  //org.springframework.beans.factory.annotation.Value를 사용해야 한다
  public TokenProvider(@Value("${jwt.secret}") String secret, 
  @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds){
    this.secret = secret;
    this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;

  }

  @Override
  public void afterPropertiesSet() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public String createToken(Authentication authentication){
    String authorities = authentication.getAuthorities().stream()
                                      .map(GrantedAuthority::getAuthority)
                                      .collect(Collectors.joining(","));

    long now = (new Date()).getTime();
    Date validity = new Date(now + this.tokenValidityInMilliseconds);
    return Jwts.builder()
            .subject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith((SecretKey) key, Jwts.SIG.HS512)
            .expiration(validity)
            .compact();
  }

  public Authentication getAuthentication(String token){
    Claims claims = Jwts
                    .parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

    Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                  .map(SimpleGrantedAuthority::new)
                  .collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public boolean validateToken(String token){
    try {
      Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token);
      return true;
    } catch(SecurityException | MalformedJwtException e){

      logger.info("잘못된 JWT 서명입니다.");
    } catch(ExpiredJwtException e){

      logger.info("만료된 JWT 토큰입니다.");
    } catch(UnsupportedJwtException e){

      logger.info("지원되지 않는 JWT 토큰입니다.");
    } catch(IllegalArgumentException e){

      logger.info("JWT 토큰이 잘못되었습니다.");
    }

    return false;
  }
}
