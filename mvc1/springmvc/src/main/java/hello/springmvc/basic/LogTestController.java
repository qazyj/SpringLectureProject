package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j   //private final Logger log = LoggerFactory.getLogger(getClass()); 의 기능을 해줌
@RestController
public class LogTestController {

    @GetMapping("/log-test")
    public String logTest() {
        String name = "spring";

        // "trace log="+ name 로 사용하면 안되는 이유
        // debug이하로 할 때, trace 안에 위와같이 적으면
        // java는 "trace log=spring"을 저장한다.
        // 즉, 로그는 찍히지 않지만 연산이 일어나며 cpu, memory를 사용하게 된다.
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        System.out.println("info log =" + name);
        log.info("info log= {}", name);
        log.warn("warn log={}", name);
        log.error("error log= {}", name);
        return "ok";
    }
}
