package com.example.cmsbe.services;

import com.example.cmsbe.models.*;
import com.example.cmsbe.models.dto.ValuableCustomerDTO;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.repositories.OrderRepository;
import com.example.cmsbe.repositories.ProductRepository;
import com.example.cmsbe.services.interfaces.IOverviewService;
import com.example.cmsbe.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OverviewService implements IOverviewService {
    private final OrderRepository<Order> orderRepository;
    private final ProductRepository productRepository;

    @Override
    public Overview getOverview() {
        var result = orderRepository.getMonthlySales();
        var monthlySales =
                result.stream()
                        .map(objects -> new MonthlySalesItem(LocalDate.of((Integer) objects[2], (Integer) objects[1], 1), (Double) objects[0]))
                        .toList();
        return new Overview(
                new MonthlySales(monthlySales),
                new OrderStatistics(
                        orderRepository.countByStatus(OrderStatus.PROCESSING),
                        orderRepository.countByStatus(OrderStatus.DELIVERING),
                        orderRepository.countByStatus(OrderStatus.COMPLETED),
                        orderRepository.countByStatus(OrderStatus.CANCELLED)
                ),
                orderRepository.findTop10CustomersByTotalOrderValue()
                        .stream().map(objects -> (
                                new ValuableCustomerDTO(
                                        (Integer) objects[0],
                                        (String) objects[1],
                                        objects[2].toString(),
                                        ((Long) objects[7]).intValue(),
                                        (Double) objects[8]
                                )
                        ))
                        .toList(),
//                productRepository.getOnLowestProductStock(),
                productRepository.getAggregatedMonthlySales()
                        .stream().map(object -> {
                            return new AggregatedMonthlySaleItem(
                                    (Integer) object[0],
                                        String.valueOf(object[1]),
                                    Double.parseDouble(object[2].toString()),
                                    Double.parseDouble(object[3].toString())
                            );
                                }
                        ).toList()
        );
    }
}
