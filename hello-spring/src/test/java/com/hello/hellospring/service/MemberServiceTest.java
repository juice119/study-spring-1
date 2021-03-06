package com.hello.hellospring.service;

import com.hello.hellospring.domain.Member;
import com.hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        Member member1 = new Member();
        member1.setName("hello");

        // when
        Long saveId = memberService.join(member1);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member1).isEqualTo(findMember);
    }

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("hello");

        Member member2 = new Member();
        member2.setName("hello");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 입니다.");
    }

    @Test
    void findMembers() {
        // given
        Member member1 = new Member();
        member1.setName("hello1");

        Member member2 = new Member();
        member2.setName("hello2");

        // when
        memberService.join(member1);
        memberService.join(member2);
        List<Member> members = memberService.findMembers();

        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void findOne() {
        // given
        String memberName = "hello1";
        Member member1 = new Member();
        member1.setName(memberName);

        // when
        Long id = memberService.join(member1);
        Member member = memberService.findOne(id).get();

        // then
        assertThat(member.getName()).isEqualTo(memberName);
    }
}