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

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    //BindingResult -> validation 에서 error 발생 시 해당 error 발생시 바로 error page 로 넘어가지 않고 해당 항목을 가진채로 로직이 진행됨.
    public String create(@Valid MemberForm form, BindingResult result) {

        //validation 에서 error 발생 시 해당 로직 진행.
        if (result.hasErrors()) {
            //thymeleaf 사용 시, 해당 error 발생 사항을 해당 html 에서 처리 가능.
            //spring boot 와 thymeleaf 가 integration 되어있다.
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }
}
