package com.gyull.jwt.jwt.security.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gyull.jwt.jwt.domain.member.Member;
import com.gyull.jwt.jwt.domain.member.MemberDto;
import com.gyull.jwt.jwt.mapper.user.UserMapper;
import com.gyull.jwt.jwt.security.domain.authority.Authority;
import com.gyull.jwt.jwt.security.domain.user.CustomUser;
import com.gyull.jwt.jwt.security.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  //회원가입
  @Transactional
  public Member signup(MemberDto memberDto){
    if (userMapper.findOneWithAuthoritiesByUsername(memberDto.getMemId()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        // 가입되어 있지 않은 회원이면,
        // 권한 정보 만들고
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // 유저 정보를 만들어서 save
        Member user = Member.builder()
                .memId(memberDto.getMemId())
                .memPw(passwordEncoder.encode(memberDto.getMemPw()))
                .memName(memberDto.getMemName())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userMapper.save(user);
    }

    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public MemberDto getUserWithAuthorities(String memId) {
        return MemberDto.from(userMapper.findOneWithAuthoritiesByUsername(memId).orElse(null));
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public MemberDto getMyUserWithAuthorities() {
        return MemberDto.from(
            SecurityUtil.getCurrentUsername()
                .flatMap(userMapper::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new RuntimeException("Member not found"))
        );
    }
}
