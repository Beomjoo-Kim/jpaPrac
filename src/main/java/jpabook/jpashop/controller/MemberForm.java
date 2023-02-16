package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {
    //entity 는 최대한 순수하게 유지해야 유지보수가 용이하다.(핵심 비즈니스 로직만 존재하도록)
    //그렇기 때문에, 이 처럼 form 객체나 DTO 를 새로 작성하여 사용한다.
    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
