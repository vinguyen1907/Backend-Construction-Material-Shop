package com.example.cmsbe.services;

import com.example.cmsbe.models.*;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.repositories.OrderRepository;
import com.example.cmsbe.repositories.ProductRepository;
import com.example.cmsbe.services.interfaces.IOverviewService;
import com.example.cmsbe.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OverviewService implements IOverviewService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public Overview getOverview() {
        var result = orderRepository.getMonthlySales();
        var monthlySales = result.stream()
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
                                new Customer(
                                        (Integer) objects[0],
                                        (String) objects[1],
                                        objects[2].toString(),
                                        DateTimeUtil.convertToLocalDateViaMillisecond((Date) objects[3]),
                                        (String) objects[4],
                                        (String) objects[5],
                                        null,
                                        null,
                                        (boolean) objects[6]
                                )
                        ).toDTO())
                        .toList(),
                productRepository.getOnLowestProductStock()
        );
    }

}
