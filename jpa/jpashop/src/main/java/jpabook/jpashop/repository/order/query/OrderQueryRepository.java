package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    /**
     * join해서 갖고온 데이터에 ToMany 데이터를 Dto 로 갖고온다.
     */
    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrderQueryDto();

        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderitems(orderItems);
        });

        return result;
    }

    /**
     * OrderItemQueryDto.orderItems 필드 값 채우는 메소드
     */
    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, oi.item.name, oi.orderPirce, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    /**
     * dto로 직접 조회 (ToMany는 따로 담아줘야하기 때문에 ToMany빼고 join해서 한번에 데이터를 갖고온다.)
     */
    public List<OrderQueryDto> findOrderQueryDto() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, o.member.name, o.orderDate, o.status, o.delivery.address)" +
                        " from Order o" +
                " join o.member m" +
                " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    /**
     * Order 수에 따른 N번에 추가 쿼리를 1번의 쿼리문으로
     */
    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrderQueryDto();

        Map<Long, List<OrderItemQueryDto>> orderItempMap = findOrderItemMap(toOrderIds(result));

        result.forEach(o -> o.setOrderitems(orderItempMap.get(o.getOrderId())));

        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> ordersIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery(
                        "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, oi.item.name, oi.orderPirce, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", ordersIds)
                .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItempMap = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto -> OrderItemQueryDto.getOrderId()));
        return orderItempMap;
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        List<Long> ordersIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
        return ordersIds;
    }

    /**
     * 단 한번의 쿼리문으로 컬렉션까지 조회
     */
    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery(
                "select new" +
                        " jpabook.jpashop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPirce, oi.count)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
}
