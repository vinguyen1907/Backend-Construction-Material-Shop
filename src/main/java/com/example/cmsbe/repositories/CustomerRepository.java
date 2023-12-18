package com.example.cmsbe.repositories;

import com.example.cmsbe.models.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByNameContaining(String name, Pageable pageable);
    List<Customer> findByPhoneContaining(String phone, Pageable pageable);
}
