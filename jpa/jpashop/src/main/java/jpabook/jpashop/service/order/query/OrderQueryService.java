package jpabook.jpashop.service.order.query;

import jpabook.jpashop.repository.order.query.OrderFlatDto;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {
    private final OrderQueryRepository orderQueryRepository;

    public List<OrderQueryDto> findOrderQueryDtos() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    public List<OrderQueryDto> findAllByDto_optimization() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    public List<OrderFlatDto> findAllByDto_flat() {
        return orderQueryRepository.findAllByDto_flat();
    }
}
