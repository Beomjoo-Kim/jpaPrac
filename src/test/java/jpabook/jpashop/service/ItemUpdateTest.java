package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemUpdateTest {

    @Autowired
    EntityManager em;


    @Test
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1L);

        //Transaction
        book.setName("asdf");

        //변경감지 == dirty checking
        //Transaction commit
        //update 시에 따로 entityManager 를 통해 update 나 merge 를 진행하지 않더라도,
        //jpa 쪽에서 변경이 된 항목을 감지하고 자동으로 변경한다.(영속성 엔티티)

        //준영속 엔티티 -> DB 를 한번 거쳐 식별자가 존재하여 JPA 가 식별할 수 있는 entity.
        //임의로 만들어낸 엔티팉도 기존 식별자를 가지고 있으면 준영속 엔티티이다.
        //준영속 엔티티는 영속성 컨텍스트가 더는 관리하지 않는 엔티티를 의미한다.

        //영속성 엔티티는 jpa 가 변경감지를 통해 변경이 일어났을 시 자동으로 db 의 값을 변경시켜 주는데,
        //준영속 엔티티는 영속성 컨텍스트가 관리하지 않기 때문에 변경이 필요 할 경우 다른 방식으로 진행해주어야 한다.
        //변경 감지 기능(dirty checking)을 사용하는 것과 병합(merge)를 사용하는 두 가지 방법이 존재한다.

        //변경 감지 기능의 경우엔 변경된 parameter 만 변경되지만, 병합의 경우엔 조회 후 set 이 진행되고 update 가 진행되기 때문에
        //미리 set 해주지 않은 값의 경우 null 로 update 가 진행 될 수 있다.

        //그렇기 때문에 실무에서는 merge 를 사용하는 것 보다는 변경감지를 사용하는 것이 권장된다.
        //find 이후 set.

        //주의점
        //controller 에서 어설프게 entity 생성 금지
        //transaction 이 있는 service 에 식별자와 변경할 데이터를 명확히 전달할 것.(parameter / dto)
        //transaction 이 있는 service 에서 영속 상태의 엔티티를 조회하고, 해당 엔티티의 데이터를 직접 변경할 것.
        //이렇게 진행하면 transaction commit 시점에 변경 감지가 실행되어 자동으로 변경된 데이터만 변경된다.
    }
}
