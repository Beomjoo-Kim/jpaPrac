package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//해당 service 에는 select 와 같이 readOnly 를 사용할 경우가 많기 때문에 전체에 readOnly 를 걸어두고 readOnly 인 경우는 따로 지정해둔다.
@Transactional(readOnly = true)
// final 이 지정된 field 의 생성자를 자동으로 생성.
// 생성자 injection 을 사용할 경우 해당 annotation 을 지정하고 final 로 지정해주면 자동으로 생성자가 만들어지기 때문에 injection 이 자동으로 이루어진다.
@RequiredArgsConstructor
public class MemberService {

    //생성자 injection 의 경우 final 로 지정하는 것이 권장된다.
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    // insert 이기 때문에 readOnly 를 걸면 안되기 때문에 따로 지정해둔다.
    // @Transactional 의 경우 default 가 readOnly = false 이다.
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        //중복 회원 검증 method 를 작성하여도 동시에 들어오는 경우엔 둘 다 save 를 탈 수 있다.
        //이를 방지하기 위해 방어적으로 db 의 name 이라는 column 에 unique 제약조건을 거는 것이 바람직하다.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원이름입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
