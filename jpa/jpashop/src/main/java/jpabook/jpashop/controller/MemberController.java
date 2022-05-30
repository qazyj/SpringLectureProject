package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // get 요청
    @GetMapping("members/new")
    public String createFrom(Model model){
        // controller -> view 의 로직에서 data를 싫어서 보냄.
        // memberForm에 저장된 데이터를 갖고옴 MemberFrom메 매핑하여
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    // post 요청
    @PostMapping("members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result){
        // name을 필수로 입력해야하는데, 입력을 하지않아 에러가 존재한다면 다시 회원가입 페이지로 이동
        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
