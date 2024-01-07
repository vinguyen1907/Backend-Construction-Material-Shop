package com.example.cmsbe.services.interfaces;

public interface IDashboardService {
    long getCustomerCount();

    long getOrderCount();

    long getProductCount();

    Double getRevenue();

    Double getTotalRemainingCapacity();

    Double getTotalSoldCapacity();
}
