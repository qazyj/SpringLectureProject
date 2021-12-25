package hello.core.order;

import hello.core.discount.FIxDiscountPolicy;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

public class OrderServiceImplTest {

    @Test
    void createOrder(){
        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FIxDiscountPolicy());
        orderService.createOrder(1L, "itemA", 10000);
    }
}
