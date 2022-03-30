package com.hello.hellospring.respository;

import com.hello.hellospring.domain.Member;
import com.hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        // given
        Member member = new Member();
        member.setName("spring");

        //  when
        repository.save(member);

        // then
        Member result = repository.findById(member.getId()).get();
        Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when
        Member result = repository.findByName("spring1").get();

        //then
        Assertions.assertThat(member1).isEqualTo(result);
    }

    @Test
    public void findAll() {
        // given
        ArrayList<Member> arrayList = new ArrayList<>();
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        arrayList.add(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        arrayList.add(member2);

        Member member3 = new Member();
        member3.setName("spring3");
        repository.save(member3);
        arrayList.add(member3);

        //when
        List<Member> result = repository.findAll();

        //then
        Assertions.assertThat(result).isEqualTo(arrayList);
    }

    @Test
    public void clearStore() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        // when
        repository.clearStore();
        List<Member> result = repository.findAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(0);
    }
}
