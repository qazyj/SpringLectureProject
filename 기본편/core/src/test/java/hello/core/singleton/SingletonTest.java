package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        // 1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService = appConfig.memberService();

        // 2. 조회 : 조회할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        // 참조값이 다른 것을 확인
        System.out.println("memberService1 : " + memberService);
        System.out.println("memberService2 : " + memberService1);

        // memberService != memberService1
        assertThat(memberService).isNotSameAs(memberService1);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        //private으로 생성자를 막아두었다. 컴파일 오류가 발생한다.
        // new SingletonService();

        // 1. 조회 : 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService = SingletonService.getInstance();

        // 2. 조회 : 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService1 = SingletonService.getInstance();

        // 참조값이 같은 것을 확인
        System.out.println("singletonService1 : " + singletonService);
        System.out.println("singletonService2 : " + singletonService1);

        // singletonService1 == singletonService2
        assertThat(singletonService).isSameAs(singletonService1);

        singletonService1.logic();
    }
}
