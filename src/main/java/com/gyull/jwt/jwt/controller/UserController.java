package com.gyull.jwt.jwt.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gyull.jwt.jwt.domain.member.Member;
import com.gyull.jwt.jwt.domain.member.MemberDto;
import com.gyull.jwt.jwt.security.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
  private final UserServiceImpl userService;
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/hello")
    public ResponseEntity<String> hello() {
      return ResponseEntity.ok("hello");
    }

    @PostMapping("/test-redirect")
  public void testRedirect(HttpServletResponse response) throws IOException {
    response.sendRedirect("/api/user");
  }

  @PostMapping("/signup")
  public ResponseEntity<Member> singup(@Valid @RequestBody MemberDto memberDto){
    logger.info("signup api 호출됨");
    return ResponseEntity.ok(userService.signup(memberDto));
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER','ADMIN')")
  public ResponseEntity<MemberDto> getMyUserInfo(HttpServletRequest request){
    logger.info("user api 호출됨");
    return ResponseEntity.ok(userService.getMyUserWithAuthorities());
  }

  @GetMapping("/user/{username}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<MemberDto> getUserInfo(@PathVariable String username){
    logger.info("user api username 호출됨");
    return ResponseEntity.ok(userService.getUserWithAuthorities(username));
  }
}
