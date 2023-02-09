package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    // oneToOne의 경우엔 FK는 어느 곳엥 두어도 상관없다.
    // 이 때, 자주 사용하는 곳에 두는것으로 지향.
    // 현재 서비스에선 delivery 보다는 order를 자주 사용하기 떄문에 order에 FK를 지정한다. -> 연관관계의 주인을 order로 지정
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    // ORDINAL = 숫자로 들어감(중간에 다른 status가 추가되면 기존 1,2,3 사이에 중간에 들어가기 때문에 장애가 발생한다.)
    // STRING 사용을 지향할 것.
    private DeliveryStatus status; //READY, COMP (배송중, 배송)
}
