package com.example.cmsbe.utils;

import com.example.cmsbe.models.Customer;
import com.example.cmsbe.models.dto.CustomerDTO;

import java.util.List;

public class ListUtil {
    static public List<CustomerDTO> convertToDTOList(List<Customer> customers) {
        return customers.stream().map(Customer::toDTO).toList();
    }
}
