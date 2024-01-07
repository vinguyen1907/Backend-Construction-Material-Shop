package com.example.cmsbe.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DashboardResultDTO {
    private long customerCount;
    private long orderCount;
    private long productCount;
    private Double revenue;
    private List<OrderDTO> newestOrders;
    private double soldQuantity;
    private double remainingQuantity;
    private double totalQuantity;
}
