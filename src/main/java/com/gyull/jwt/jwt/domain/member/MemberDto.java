package com.gyull.jwt.jwt.domain.member;

import java.util.Set;
import java.util.stream.Collectors;

import com.gyull.jwt.jwt.security.domain.authority.Authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
  private String memId;
  private String memPw;
  private String memName;
  private Set<Authority> authorities;

  public static MemberDto from(Member member) {
    if(member == null) return null;

      return MemberDto.builder()
              .memId(member.getMemId())
              .memName(member.getMemName())
              .authorities(member.getAuthorities().stream()
                      .map(authority -> Authority.builder().authorityName(authority.getAuthorityName()).build())
                      .collect(Collectors.toSet()))
              .build();
  }
}
