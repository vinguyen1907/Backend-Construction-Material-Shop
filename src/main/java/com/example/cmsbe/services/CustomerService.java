package com.example.cmsbe.services;

import com.example.cmsbe.models.dto.CustomerDTO;
import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.Customer;
import com.example.cmsbe.models.Order;
import com.example.cmsbe.repositories.CustomerRepository;
import com.example.cmsbe.repositories.OrderRepository;
import com.example.cmsbe.services.interfaces.ICustomerService;
import com.example.cmsbe.utils.ListUtil;
import com.example.cmsbe.utils.OrderUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public PaginationDTO<Customer> getAllCustomer(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new PaginationDTO<>(customerRepository.findAll(pageable));
    }

    @Override
    public PaginationDTO<CustomerDTO> getAllCustomerDTO(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var result = customerRepository.findAll(pageable);
        var customers = result.getContent();
        var customerDTOs = ListUtil.convertToDTOList(customers);
        return new PaginationDTO<>(
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber(),
                result.getSize(),
                customerDTOs
        );
    }

    @Override
    public List<OrderDTO> getCustomerOrders(Integer customerId) {
        return ListUtil.convertToOrderDTOList(orderRepository.findByCustomerId(customerId));
    }

    @Override
    public PaginationDTO<CustomerDTO> searchCustomerByName(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        var result = customerRepository.findByNameContaining(name, pageable);
        var customers = result.getContent();
        var customerDTOs = ListUtil.convertToDTOList(customers);
        return new PaginationDTO<>(
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber(),
                result.getSize(),
                customerDTOs
        );
    }

    @Override
    public PaginationDTO<CustomerDTO> searchCustomerByPhone(int page, int size, String phone) {
        Pageable pageable = PageRequest.of(page, size);
        var result = customerRepository.findByPhoneContaining(phone, pageable);
        var customers = result.getContent();
        var customerDTOs = ListUtil.convertToDTOList(customers);
        return new PaginationDTO<>(
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber(),
                result.getSize(),
                customerDTOs
        );
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Integer id, Customer customer) {
        customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        customer.setId(id);
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}
