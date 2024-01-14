package com.example.cmsbe.services;

import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import com.example.cmsbe.repositories.CustomerRepository;
import com.example.cmsbe.repositories.OrderRepository;
import com.example.cmsbe.repositories.ProductRepository;
import com.example.cmsbe.repositories.SaleOrderRepository;
import com.example.cmsbe.services.interfaces.IDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService implements IDashboardService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final SaleOrderRepository saleOrderRepository;
    private final ProductRepository productRepository;

    @Override
    public long getCustomerCount() {
        return customerRepository.count();
    }

    @Override
    public long getOrderCount() {
        return saleOrderRepository.count();
    }

    @Override
    public long getProductCount() {
        return productRepository.count();
    }

    @Override
    public Double getRevenue() {
        return orderRepository.sumTotalByOrderTypeAndStatus(OrderType.SALE, OrderStatus.COMPLETED);
    }

    @Override
    public Double getTotalRemainingCapacity() {
        return productRepository.getTotalRemainingCapacity();
    }

    @Override
    public Double getTotalSoldCapacity() {
        return productRepository.getTotalSoldCapacity();
    }
}
