package com.example.cmsbe.controllers;

import com.example.cmsbe.models.dto.DashboardResultDTO;
import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.services.interfaces.IDashboardService;
import com.example.cmsbe.services.interfaces.IOrderService;
import com.example.cmsbe.services.interfaces.IWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final IDashboardService dashboardService;
    private final IOrderService orderService;
    private final IWarehouseService warehouseService;

    @GetMapping
    public DashboardResultDTO getDashboard() {
        long customerCount = dashboardService.getCustomerCount();
        long orderCount = dashboardService.getOrderCount();
        long productCount = dashboardService.getProductCount();
        Double revenue = dashboardService.getRevenue();
        revenue = revenue == null ? 0 : revenue;
        List<OrderDTO> newestOrders = orderService.getNewestOrders(15);
        double soldQuantity = dashboardService.getTotalSoldCapacity();
        double remainingQuantity = dashboardService.getTotalRemainingCapacity();
        double totalQuantity = soldQuantity + remainingQuantity;

        return new DashboardResultDTO(
                customerCount,
                orderCount,
                productCount,
                revenue,
                newestOrders,
                soldQuantity,
                remainingQuantity,
                totalQuantity
        );
    }
}
