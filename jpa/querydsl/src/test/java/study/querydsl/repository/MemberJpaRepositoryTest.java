package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class MemberJpaRepositoryTest {
    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void before() {

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        Member member5 = new Member("member5", 50, teamB);
        Member member6 = new Member("member6", 60, teamB);
        Member member7 = new Member("member7", 70, teamB);
        Member member8 = new Member("member8", 80, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
        em.persist(member5);
        em.persist(member6);
        em.persist(member7);
        em.persist(member8);

        //초기화
        em.flush();
        em.clear();
    }

    @Test
    public void basicQuerydsl() {
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();
        assertThat(findMember.getId()).isEqualTo(member.getId());

        List<Member> result1 = memberJpaRepository.findAll_Querydsl();
        assertThat(result1.size()).isEqualTo(1);

        List<Member> result2 = memberJpaRepository.findByUsername_Querydsl("member1");
        assertThat(result2.get(0).getUsername()).isEqualTo(member.getUsername());

    }

    @Test
    public void searchTest() {
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(80);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);

        for (MemberTeamDto memberTeamDto : result) {
            System.out.println("memberTeamDto = " + memberTeamDto);
        }
    }

    @Test
    public void searchTest2() {
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(80);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.search(condition);

        for (MemberTeamDto memberTeamDto : result) {
            System.out.println("memberTeamDto = " + memberTeamDto);
        }
    }
}