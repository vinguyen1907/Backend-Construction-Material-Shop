package com.example.cmsbe.models.dto;

import com.example.cmsbe.models.Customer;
import com.example.cmsbe.models.Order;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerDTO {
    private Integer id;
    private String name;
    private String phone;
    private LocalDate dateOfBirth;
    private String contactAddress;
    private String taxCode;
    private List<Integer> orderIds = new ArrayList<>();
    private boolean isDeleted;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.phone = customer.getPhone();
        this.dateOfBirth = customer.getDateOfBirth();
        this.contactAddress = customer.getContactAddress();
        this.taxCode = customer.getTaxCode();
        this.isDeleted = customer.isDeleted();
        List<Order> orders = customer.getOrders();
        for (Order order : orders) {
            assert false;
            this.orderIds.add(order.getId());
        }
    }
}
