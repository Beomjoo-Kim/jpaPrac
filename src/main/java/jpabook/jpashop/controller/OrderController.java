package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        //entity 를 controller 쪽에서도 select 할 수 있지만, service 쪽에서 진행하는 것이 더욱 할 수 있는 기능이 많아진다.
        //service 쪽에서 transaction 안에서 select 하기 때문에 영속 상태의 엔티티를 얻을 수 있다.
        // -> 변경 감지를 사용할 수 있다.
        //transaction 없이 controller 쪽에서 select 를 진행 한 후 service 로 넘기게 되면
        //해당 객체는 jpa 와 관계가 없기 떄문에 영속성을 가지지 못한다.
        orderService.order(memberId, itemId, count);

        return "redirect:/orders";
    }
}
