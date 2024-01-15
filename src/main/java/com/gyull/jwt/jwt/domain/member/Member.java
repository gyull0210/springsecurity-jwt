package com.gyull.jwt.jwt.domain.member;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import com.gyull.jwt.jwt.security.domain.authority.Authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
  private int memIdx;
  private String memId;
  private String memPw;
  private String memName;
  private boolean activated;
  private Set<Authority> authorities;
  private LocalDateTime createdAt;
  private LocalDateTime recentAt;

}
