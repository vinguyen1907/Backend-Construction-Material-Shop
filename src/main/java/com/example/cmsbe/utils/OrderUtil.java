package com.example.cmsbe.utils;

import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.OrderItem;

import java.util.List;

public class OrderUtil {
    public static double calculateTotal(List<Order> orders) {
        double total = 0;
        for (Order order : orders) {
            for (OrderItem item : order.getOrderItems()) {
                total += item.getQuantity() * item.getInventoryItem().getProduct().getUnitPrice();
            }
        }
        return total;
    }
}
