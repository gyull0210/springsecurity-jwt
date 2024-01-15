package com.gyull.jwt.jwt.security.domain.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gyull.jwt.jwt.domain.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUser implements UserDetails {
  
  private Member member;

  /**
   * 해당 유저의 권한 목록
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    this.member.getAuthorities().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName())));
    
    return authorities;
 }

  @Override
  public String getPassword() {
    return member.getMemPw();
  }

  /**
   * 계정의 고유한 값
   * 
   */
  @Override
  public String getUsername() {
    return member.getMemId();
  }

  /**
   * 계정 만료 여부
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * 계정 잠김 여부
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * 비밀번호 만료 여부
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * 사용자 활성화 여부
   */
  @Override
  public boolean isEnabled() {
    return member.isActivated();
  }
  
}
