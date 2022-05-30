package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.*;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        } else {
            em.merge(item);     //merge에 대한 설명은 후에 한다고 합니다.
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
