package com.example.cmsbe.controllers;

import com.example.cmsbe.models.Customer;
import com.example.cmsbe.models.User;
import com.example.cmsbe.services.generators.CustomerExcelGenerator;
import com.example.cmsbe.services.generators.EmployeeExcelGenerator;
import com.example.cmsbe.services.interfaces.ICustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllCustomer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String phone
    ) {
            if (customerName != null) {
                return ResponseEntity.ok(customerService.searchCustomerByName(page, size, customerName));
            } else if (phone != null) {
                return ResponseEntity.ok(customerService.searchCustomerByPhone(page, size, phone));
            } else {
                return ResponseEntity.ok(customerService.getAllCustomer(page, size));
            }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer customerId) throws EntityNotFoundException {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<?> getCustomerOrders(@PathVariable Integer customerId) throws EntityNotFoundException {
        return ResponseEntity.ok(customerService.getCustomerOrders(customerId));
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody @Valid Customer customer) {
        return ResponseEntity.ok(customerService.addCustomer(customer));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer customerId, @RequestBody Customer customer) {
        try {
            return ResponseEntity.ok(customerService.updateCustomer(customerId, customer));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export/excel")
    public void exportCustomerToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=customers_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Customer> customers = customerService.getAllCustomer();
        CustomerExcelGenerator excelExporter = new CustomerExcelGenerator(customers);

        excelExporter.export(response);
    }
}
