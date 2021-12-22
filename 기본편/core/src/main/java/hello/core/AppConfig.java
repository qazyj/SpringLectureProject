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
        return new MemberServiceImpl(getMemberRepository());
    }

    @Bean
    public MemberRepository getMemberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(
                getMemberRepository(),
                getDigcountPolicy());
    }

    @Bean
    public DiscountPolicy getDigcountPolicy() {
        //return new FIxDiscountPolicy();
        return new RateDiscountPolicy();
    }
}