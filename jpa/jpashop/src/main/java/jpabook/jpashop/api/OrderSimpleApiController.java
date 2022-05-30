package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * XToOne (ManyToOne, OneToOne) 에서의 성능 최적화
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderService orderService;
    private final MemberService memberService;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderService.findOrders(new OrderSearch());
        for(Order order : orders){
            // getMember까지는 프록시 객체지만, getName()을 가져오면 강제 초기화되서 데이터를 끌고 온다.
            // 이렇게 강제적으로 Entity를 가져오는 방법도 있다.
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDTO> ordersV2() {
        List<Order> orders = orderService.findOrders(new OrderSearch());
        List<SimpleOrderDTO> result = orders.stream()
                .map(o -> new SimpleOrderDTO(o))
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDTO> ordersV3() {
        List<Order> orders = orderService.findAllWithMemberDelivery();

        List<SimpleOrderDTO> result = orders.stream()
                .map(o -> new SimpleOrderDTO(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderService.findOrderDtos();
    }

    @Data
    static class SimpleOrderDTO {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDTO(Order order){
            orderId = order.getId();
            name = order.getMember().getName();     // LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();         // LAZY 초기화
        }
    }
}
