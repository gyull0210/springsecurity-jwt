package com.gyull.jwt.jwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gyull.jwt.jwt.domain.login.LoginDTO;
import com.gyull.jwt.jwt.domain.token.TokenDTO;
import com.gyull.jwt.jwt.security.filter.JwtFilter;
import com.gyull.jwt.jwt.security.provider.TokenProvider;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
  private final Logger logger = LoggerFactory.getLogger("AuthController.class");
  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @PostMapping("/authenticate")
  public ResponseEntity<TokenDTO> authorize(@Valid @RequestBody LoginDTO loginDTO){
    logger.info("인증 api 호출됨");
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getMemId(), loginDTO.getMemPw());
    logger.info("UsernamePasswordAuthenticationToken 생성됨");
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    logger.info("authentication 생성");
    SecurityContextHolder.getContext().setAuthentication(authentication);
    logger.info("security context 생성");
    String jwt = tokenProvider.createToken(authentication);
    logger.info("토큰 생성");
    HttpHeaders httpHeaders = new HttpHeaders();

    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
    logger.info("토큰 담기");
    return new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK);
  }
}
