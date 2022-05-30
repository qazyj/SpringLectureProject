package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.*;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소 (비즈니스 로직을 만들어 놓았기 때문에, cancel 메소드만 접근하면 된다.)
        order.cancel();

        // jpa가 아니라면 수정된 데이터들을 update 쿼리문을 날려야하지만,
        // jpa는 더티 체킹을 통해 변경된 내역을 알아서 update 쿼리문을 날린다.
        // 정말 편리
    }


    /**
     * 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByCriteria(orderSearch);
    }

    public List<Order> findAllWithMemberDelivery(){
        return orderRepository.findAllWithMemberDelivery();
    }

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    public List<Order> findAllWithMemberDeliveryItem() {
        return orderRepository.findAllWithMemberDeliveryItem();
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return orderRepository.findAllWithMemberDelivery(offset, limit);
    }
}
