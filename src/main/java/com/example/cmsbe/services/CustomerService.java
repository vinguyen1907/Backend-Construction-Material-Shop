package com.example.cmsbe.services;

import com.example.cmsbe.models.Customer;
import com.example.cmsbe.repositories.CustomerRepository;
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

    @Override
    public List<Customer> getAllCustomer(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Customer> searchCustomerByName(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findByNameContaining(name, pageable);
    }

    @Override
    public List<Customer> searchCustomerByPhone(int page, int size, String phone) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findByPhoneContaining(phone, pageable);
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
