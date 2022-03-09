package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            //회원 저장
            Member member = new Member();
            member.setName("member1");//단방향 연관관계 설정, 참조 저장
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();
            /*
            //조회
            Team findTeam = em.find(Team.class, team.getId());
            int memberSize = findTeam.getMembers().size(); //역방향 조회
            System.out.println(memberSize);*/
            Team findTeam = em.find(Team.class, team.getId());
            System.out.println(findTeam.toString());
            List<Member> members = findTeam.getMembers();
            for(Member member1 : members){
                System.out.println("member = "  + member1.getName());
            }

            tx.commit();
            } catch(Exception e){
                tx.rollback();
            } finally{
                em.close();
                emf.close();
            }
        }

}