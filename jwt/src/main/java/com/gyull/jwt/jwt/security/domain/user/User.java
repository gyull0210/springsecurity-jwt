package com.gyull.jwt.jwt.security.domain.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
  
  private String userId;

  private String userPw;

  private String userName;

  private boolean activated;

  private List<String> roles = new ArrayList<>();


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
  }

  @Override
  public String getPassword() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
  }

  @Override
  public String getUsername() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isAccountNonLocked'");
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isCredentialsNonExpired'");
  }

  @Override
  public boolean isEnabled() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
  }
  
}
