package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FIxDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration          // 설정 정보에 하는 어노테이션
public class AppConfig {

    @Bean           // 각 메서드에 Bean 어노테이션을 추가하면 스프링 컨테이너에 추가됨.
    public MemberService memberService() {
        //1번
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        //2번
        System.out.println("call AppConfig.orderService");
        return null;
        /*return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());*/
    }

    @Bean
    public MemberRepository memberRepository() {
        //3번
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        //return new FIxDiscountPolicy();
        return new RateDiscountPolicy();
    }
}