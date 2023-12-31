package com.gyull.jwt.jwt.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gyull.jwt.jwt.domain.member.Member;
import com.gyull.jwt.jwt.mapper.user.UserMapper;
import com.gyull.jwt.jwt.security.domain.user.CustomUser;

import lombok.RequiredArgsConstructor;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserMapper userMapper;

  //username파라미터로 User정보와 권한을 가져와 User 객체 생성 메서드를 호출
  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    return userMapper.getUserWithAuthorities(username)
              .map(user -> createUser(username, user))
              .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
  }

  //username파라미터로 조회하여 회원 정보를 확인하고 User 객체를 생성
  private User createUser(String username, Member user){

    if(!user.isActivated()){
      throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
    }

    List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
            .collect(Collectors.toList());
    
    return new User(user.getMemId(), user.getMemPw(), grantedAuthorities);
  }

}
