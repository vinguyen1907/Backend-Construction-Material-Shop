package com.example.cmsbe.controllers;

import com.example.cmsbe.models.Customer;
import com.example.cmsbe.models.User;
import com.example.cmsbe.models.dto.CustomerDTO;
import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.services.generators.CustomerExcelGenerator;
import com.example.cmsbe.services.generators.EmployeeExcelGenerator;
import com.example.cmsbe.services.interfaces.ICustomerService;
import com.example.cmsbe.services.interfaces.IUserService;
import com.example.cmsbe.utils.ListUtil;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;
    private final IUserService userService;

    @GetMapping
    public ResponseEntity<PaginationDTO<CustomerDTO>> getAllCustomer(
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
                return ResponseEntity.ok(customerService.getAllCustomerDTO(page, size));
            }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer customerId) throws EntityNotFoundException {
        var result = customerService.getCustomerById(customerId);

        return ResponseEntity.ok(result.toDTO());
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderDTO>> getCustomerOrders(@PathVariable Integer customerId) throws EntityNotFoundException {
        return ResponseEntity.ok(customerService.getCustomerOrders(customerId));
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody @Valid Customer customer) {
        return ResponseEntity.ok(customerService.addCustomer(customer));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable Integer customerId,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String taxCode,
            @RequestParam(required = false) LocalDate dateOfBirth
    ) {
        try {
            return ResponseEntity.ok(customerService.updateCustomer(customerId,
                    customerName,
                    phone,
                    address,
                    taxCode,
                    dateOfBirth
                    ));
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
        CustomerExcelGenerator excelExporter = new CustomerExcelGenerator(userService, customers);

        excelExporter.export(response);
    }
}
