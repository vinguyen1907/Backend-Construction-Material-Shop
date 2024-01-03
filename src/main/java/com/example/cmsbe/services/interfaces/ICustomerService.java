package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.Customer;
import com.example.cmsbe.models.Order;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomer();
    PaginationDTO<Customer> getAllCustomer(int page, int size);
    List<Order> getCustomerOrders(Integer customerId);
    PaginationDTO<Customer> searchCustomerByName(int page, int size, String name);
    PaginationDTO<Customer> searchCustomerByPhone(int page, int size, String phone);
    Customer getCustomerById(Integer id);
    Customer addCustomer(Customer customer);
    Customer updateCustomer(Integer id, Customer customer);
    void deleteCustomer(Integer id);
}
