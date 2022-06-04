package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 컨트롤러의 기능
// 1. @Component를 통해 스프링 빈으로 등록
// 2. 스프링 mvc에서 어노테이션 기반 컨트롤러로 인식  RequestMappingHandlerMapping
@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process(){
        System.out.println("OldController.handlerRequest");
        return new ModelAndView("new-form");
    }
}
