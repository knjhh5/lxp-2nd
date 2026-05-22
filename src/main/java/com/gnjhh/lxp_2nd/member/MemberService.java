package com.gnjhh.lxp_2nd.member;

import com.gnjhh.lxp_2nd.member.domain.entity.Member;
import com.gnjhh.lxp_2nd.member.domain.vo.UserType;
import com.gnjhh.lxp_2nd.member.dto.MemberCreateRequestDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signup(MemberCreateRequestDto memberCreateRequestDto) {
        if (memberRepository.existsByLoginId(memberCreateRequestDto.getLoginId())) {
            throw new IllegalStateException("이미 사용중인 아이디입니다.");
        } else if (memberRepository.existsByNickname(memberCreateRequestDto.getNickname())) {
            throw new IllegalStateException("이미 사용중인 닉네입니다.");

        }

        String encodePassword = passwordEncoder.encode(memberCreateRequestDto.getPassword());
        Member member = new Member(
                memberCreateRequestDto.getLoginId(),
                encodePassword,
                memberCreateRequestDto.getNickname(),
                UserType.STUDENT
        );

        memberRepository.save(member);
    }
}
