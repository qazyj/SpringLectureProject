package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Child child1 = new Child();
            Child child2 = new Child();


            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            tx.commit();
            } catch(Exception e){
                tx.rollback();
                e.printStackTrace();
            } finally{
                em.close();
                emf.close();
            }
    }

    public static void printMember(Member member){
        String username = member.getName();
        System.out.println(username);

        Team team = member.getTeam();
        System.out.println(team.getName());
    }


}