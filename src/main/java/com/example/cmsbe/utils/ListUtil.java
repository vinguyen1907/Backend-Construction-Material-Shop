package com.example.cmsbe.utils;

import com.example.cmsbe.models.Customer;
import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.dto.CustomerDTO;
import com.example.cmsbe.models.dto.OrderDTO;

import java.util.List;

public class ListUtil {
    static public List<CustomerDTO> convertToDTOList(List<Customer> customers) {
        return customers.stream().map(Customer::toDTO).toList();
    }

    static public List<OrderDTO> convertToOrderDTOList(List<Order> orders) {
        return orders.stream().map(Order::toDTO).toList();
    }
}
