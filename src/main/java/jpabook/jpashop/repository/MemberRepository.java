package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //spring boot 의 jpa 라이브러리를 사용할 경우 EntityManager 의 annotation 인 PersistenceContext 를 Autowired 로 변경이 가능하다.
    //이를 통해 lombok 의 RequiredArgsConstructor 를 사용하여 constructor injection 을 적용할 수 있다.
    private final EntityManager em;

    public void save(Member member) {
        // em.persist 를 작성하게 되면 해당 entity 의 key 는 지정한 pk로 자동 등록된다.
        // 그렇기때문에, db 에 들어간 시점이 아니어도 바로 getId를 진행할 수 있다.
        em.persist(member);
    }

    public Member findOne(Long id) {
        //find(type, PK)
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // createQuery("query", return Type)
        // query string의 형태가 다름(from의 대상이 table이 아닌, entity이다.)
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .getResultList();
    }
}
