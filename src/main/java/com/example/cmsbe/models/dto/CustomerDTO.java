package com.example.cmsbe.models.dto;

import com.example.cmsbe.models.Customer;
import com.example.cmsbe.models.Debt;
import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.SaleOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerDTO {
    private Integer id;
    private String name;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String contactAddress;
    private String taxCode;
    private List<Integer> orderIds = new ArrayList<>();
    private Double ordersValue;
    private List<DebtDTO> debts = new ArrayList<>();
    private boolean isDeleted;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.phone = customer.getPhone();
        this.dateOfBirth = customer.getDateOfBirth();
        this.contactAddress = customer.getContactAddress();
        this.taxCode = customer.getTaxCode();
        this.isDeleted = customer.isDeleted();
        List<SaleOrder> orders = customer.getOrders();
        if (orders == null) {
            this.orderIds = null;
            this.ordersValue = 0.0;
        } else {
            orders.forEach(order -> this.orderIds.add(order.getId()));
            this.ordersValue = orders.stream().mapToDouble(Order::getTotal).sum();
        }
        List<Debt> debts = customer.getDebts();
        if (debts == null) {
            this.debts = null;
        } else {
            debts.forEach(debt -> this.debts.add(debt.toDTO()));
        }
    }
}
