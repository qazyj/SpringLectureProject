package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import jpabook.jpashop.repository.MemberRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)    // "junit을 실행할 때, 스프링과 엮어서 실행하겠다"를 의미합니다.
@SpringBootTest         // SpringBoot를 띄운 상태에서 Test를 할 때 추가해야되는 어노테이션 (추가하지 않으면 @Autowired기능에서 에러가 발생합니다.)
@Transactional  // 기본적으로 rollback
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    // @Rollback(false)  insert query 문을 보고싶다면 해보자
    // 또는 EntityManager를 만들어서 flush() 해주면된다.
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(saveId));
    }
    
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        
        //when
        memberService.join(member1);
        memberService.join(member2);        // 예외 발생해야 함.

        //then
        fail("예외가 발생해야 한다.");
    }
}