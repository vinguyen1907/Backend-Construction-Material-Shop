package com.example.cmsbe.services;

import com.example.cmsbe.dto.PaginationDTO;
import com.example.cmsbe.models.Customer;
import com.example.cmsbe.models.InventoryItem;
import com.example.cmsbe.models.Order;
import com.example.cmsbe.repositories.CustomerRepository;
import com.example.cmsbe.repositories.OrderRepository;
import com.example.cmsbe.services.interfaces.ICustomerService;
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
    public PaginationDTO<Customer> getAllCustomer(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        long total = customerRepository.count();
        List<Customer> items = customerRepository.findAll(pageable).getContent();
        return new PaginationDTO<>(
                (long) Math.ceil((double) total / size),
                total,
                page,
                size,
                items
        );
    }

    @Override
    public List<Order> getCustomerOrders(Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public PaginationDTO<Customer> searchCustomerByName(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        long total = customerRepository.countByNameContaining(name);
        List<Customer> items = customerRepository.findByNameContaining(name, pageable);
        return new PaginationDTO<>(
                (long) Math.ceil((double) total / size),
                total,
                page,
                size,
                items
        );
    }

    @Override
    public PaginationDTO<Customer> searchCustomerByPhone(int page, int size, String phone) {
        Pageable pageable = PageRequest.of(page, size);
        long total = customerRepository.countByPhoneContaining(phone);
        List<Customer> items = customerRepository.findByPhoneContaining(phone, pageable);
        return new PaginationDTO<>(
                (long) Math.ceil((double) total / size),
                total,
                page,
                size,
                items
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
