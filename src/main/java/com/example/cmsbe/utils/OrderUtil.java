package com.example.cmsbe.utils;

import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.OrderItem;
import com.example.cmsbe.models.SaleOrder;

import java.util.List;

public class OrderUtil {
    public static double calculateTotal(List<SaleOrder> orders) {
        double total = 0;
        for (Order order : orders) {
            total += order.getTotal();
        }
        return total;
    }
}
