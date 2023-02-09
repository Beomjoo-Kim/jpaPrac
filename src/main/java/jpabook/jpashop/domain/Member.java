package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private long id;

    private String name;

    // 내장타입임을 의미하는 annotation
    @Embedded
    private Address address;

    // 연관관계의 주인 명시
    // 연관관계의 주인은 FK가 가까운 것으로 설정한다. 지금같은 경우엔 order에 FK가 존재하기 때문에 order가 주인이다.
    // 연관관계의 주인은 mappedBy를 사용하지 않는 것으로 확인이 가능하다.
    // 주인이 아니라면 mappedBy를 사용하여 주인을 지정해주어야 한다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
