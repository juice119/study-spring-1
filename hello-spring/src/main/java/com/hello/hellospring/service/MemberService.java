package com.hello.hellospring.service;

import com.hello.hellospring.domain.Member;
import com.hello.hellospring.repository.MemberRepository;
import com.hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = new MemoryMemberRepository();
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        validateDuplicateName(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateName(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원 입니다.");
                });
    }

    /**
     * 전채 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
