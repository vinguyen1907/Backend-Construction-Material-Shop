package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.Customer;
import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.dto.CustomerDTO;
import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.dto.PaginationDTO;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomer();
    PaginationDTO<Customer> getAllCustomer(int page, int size);
    PaginationDTO<CustomerDTO> getAllCustomerDTO(int page, int size);
    List<OrderDTO> getCustomerOrders(Integer customerId);
    PaginationDTO<CustomerDTO> searchCustomerByName(int page, int size, String name);
    PaginationDTO<CustomerDTO> searchCustomerByPhone(int page, int size, String phone);
    Customer getCustomerById(Integer id);
    Customer addCustomer(Customer customer);
    Customer updateCustomer(Integer id, Customer customer);
    void deleteCustomer(Integer id);
}
