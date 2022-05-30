package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jpabook.jpashop.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor            // final로 이루어진 필드로만 생성자를 만들어 줌
@Transactional(readOnly = true) //조회 성능 최적화
public class MemberService {

    private final MemberRepository memberRepository;        // 한번 초기화하면 바꿀 일이 없으니 final 선언

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 같은 이름이 있는 회원은 등록 안되도록
     */
    private void validateDuplicateMember(Member member) {
        //exception
        // 이러한 경우 멀티스레드 환경에서 문제가 될 수 있다.
        // 방지하기 위해서 member의 name을 unique 제약조건을 거는 것을 권장한다.
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 멤버 1명 조회
     */
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
