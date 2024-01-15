package com.gyull.jwt.jwt.mapper.user;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.gyull.jwt.jwt.domain.member.Member;
import com.gyull.jwt.jwt.domain.member.MemberDto;

@Mapper
public interface UserMapper {
    //회원가입
    public MemberDto signup();
 
    //내 아이디로 권한 가져오기
    public Optional<Member> findOneWithAuthoritiesByUsername(String memId);
  
    public Member save(Member user);
}
