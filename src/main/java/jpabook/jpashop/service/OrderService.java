package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //entity 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        //생각대로라면 delivery, orderItem 이 두가지를 먼저 insert 한 후 order 를 insert 해야 할 것으로 보이는데,
        //order 객체 내에서 해당하는 두 가지가 cascade 되어 있기 때문에 order 가 persist 되는 시점에 둘 다 자동으로 persist 가 진행된다.
        //cascade 는 주의해서 지정해야 한다. -> lifeCycle 파악 후 refactoring 을 통해 지정하는 것이 바람직하다.

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 cancel
        order.cancel();

        //jpa 를 사용하게 되면 해당 객체의 속성 중 변경 된 것이 있을 때 자동으로 update 가 적용된다.
        //mybatis 와 같은 것들을 사용하는 경우엔 각각 query 를 작성해야 하는 것에 비해 편의성이 높다.
    }

    /**
     * 주문 검색
     */
    /*public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }*/
}
