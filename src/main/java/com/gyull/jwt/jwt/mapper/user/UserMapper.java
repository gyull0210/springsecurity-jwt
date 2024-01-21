package com.gyull.jwt.jwt.mapper.user;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.gyull.jwt.jwt.domain.authority.Authority;
import com.gyull.jwt.jwt.domain.member.Member;
import com.gyull.jwt.jwt.domain.member.MemberDto;

@Mapper
public interface UserMapper {
    
    //DB에서 회원 조회
    public int selectOneMember(String memId);

    //내 아이디로 권한 가져오기
    public Optional<Member> findOneWithAuthoritiesByUsername(String memId);

    //회원 가입
    public int insertMember(Member user);

    //권한 등록
    public int insertAuthority(Authority authority);
}
