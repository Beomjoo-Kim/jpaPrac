package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        //test 시 insert 가 보이지 않는다.
        //generate value 전략에선 persist 를 진행한다고 해서 DB 에 insert 되지 않는다.
        //transaction 이 commit 될 때 flush 가 진행되며 insert 가 진행된다.
        //insert query 를 확인하고 싶을 경우 annotation 으로 @Rollback(false) 를 부여하면 확인이 가능하다.
        //혹은, EntityManager 를 Autowired 진행한 후 em.flush() 를 진행해주면 된다.
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(saveId));
    }
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        //when
        memberService.join(member1);
        memberService.join(member2); //Exception 발생 point

        //then
        fail("예외가 발생해야 한다.");
    }
}