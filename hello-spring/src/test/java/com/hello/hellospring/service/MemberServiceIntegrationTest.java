package com.hello.hellospring.service;

import com.hello.hellospring.domain.Member;
import com.hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        // when
        Long saveId = memberService.join(member1);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member1.getName()).isEqualTo(findMember.getName());
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
    void 사용자리스트() {
        // given
        int initSize = memberService.findMembers().size();
        Member member1 = new Member();
        member1.setName("hello1");

        Member member2 = new Member();
        member2.setName("hello2");

        // when
        memberService.join(member1);
        memberService.join(member2);
        List<Member> members = memberService.findMembers();

        // then
        assertThat(members.size()).isEqualTo(initSize + 2);
    }

    @Test
    void findOne() {
        // given
        Member member1 = new Member();
        member1.setName("hello1");

        // when
        Long id = memberService.join(member1);
        Optional<Member> one = memberService.findOne(id);

        // then
        assertThat(one.get().getName()).isEqualTo("hello1");
    }
}