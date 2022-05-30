package hellojpa;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("팀A");
            em.persist(team);

            Team teamb = new Team();
            teamb.setName("팀B");
            em.persist(teamb);

            Member member = new Member();
            member.setName("member1");
            member.setAge(10);
            member.changeTeam(team);

            Member member2 = new Member();
            member2.setName("member2");
            member2.setAge(20);
            member2.changeTeam(team);

            Member member3 = new Member();
            member3.setName("member3");
            member3.setAge(30);
            member3.changeTeam(teamb);

            em.persist(member);
            em.persist(member2);
            em.persist(member3);


            em.flush();
            em.clear();

            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            em.clear();
            member3 = em.find(Member.class, member3.getId());
            System.out.println(member3.getAge());

            tx.commit();
            } catch(Exception e){
                tx.rollback();
                e.printStackTrace();
            } finally{
                em.close();
                emf.close();
            }
    }
}