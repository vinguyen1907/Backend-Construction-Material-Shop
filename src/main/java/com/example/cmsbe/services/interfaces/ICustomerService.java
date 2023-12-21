package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.Customer;
import com.example.cmsbe.models.Order;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomer(int page, int size);
    List<Order> getCustomerOrders(Integer customerId);
    List<Customer> searchCustomerByName(int page, int size, String name);
    List<Customer> searchCustomerByPhone(int page, int size, String phone);
    Customer getCustomerById(Integer id);
    Customer addCustomer(Customer customer);
    Customer updateCustomer(Integer id, Customer customer);
    void deleteCustomer(Integer id);
}