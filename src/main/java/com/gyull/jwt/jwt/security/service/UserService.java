package com.gyull.jwt.jwt.security.service;

import com.gyull.jwt.jwt.domain.member.Member;
import com.gyull.jwt.jwt.domain.member.MemberDto;

import java.util.Optional;

public interface UserService {
  public Optional<Member> signup(MemberDto memberDto);

  public MemberDto getUserWithAuthorities(String memId);

  public MemberDto getMyUserWithAuthorities();
}
