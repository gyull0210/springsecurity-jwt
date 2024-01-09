package com.gyull.jwt.jwt.mapper.user;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import com.gyull.jwt.jwt.domain.member.Member;
import com.gyull.jwt.jwt.domain.member.MemberDto;

@Mapper
public interface UserMapper {
  public MemberDto signup();

  public Optional<Member> getUserWithAuthorities(String memId);

  public Optional<Member> getMyUserWithAuthorities();

  public Optional<Member> findOneWithAuthoritiesByUsername(String memId);

  public Member save(Member user);
}
