package com.example.cmsbe.controllers;

import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.SaleOrder;
import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.dto.OrderRequestDTO;
import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import com.example.cmsbe.services.generators.InvoicePdfGenerator;
import com.example.cmsbe.services.interfaces.IOrderService;
import com.itextpdf.text.DocumentException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @GetMapping
    public ResponseEntity<PaginationDTO<OrderDTO>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) OrderType orderType,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(orderService.searchWithFilter(page, size, id, customerName, status, orderType, startDate, endDate));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer orderId) throws EntityNotFoundException {
        return ResponseEntity.ok(orderService.getOrderDTOById(orderId));
    }

    @GetMapping("newest/{size}")
    public ResponseEntity<List<OrderDTO>> getNewestOrders( @PathVariable Integer size) {
        return ResponseEntity.ok(orderService.getNewestOrders());
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequestDTO requestOrder) {
        Order order;
        if (requestOrder.getOrderType() == OrderType.PURCHASE) {
            order = requestOrder.toPurchaseOrder();
        } else if (requestOrder.getOrderType() == OrderType.SALE) {
            order = requestOrder.toSaleOrder();
        } else {
            throw new IllegalArgumentException("Order type is not valid");
        }
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Integer orderId,
            @RequestParam(name = "status") OrderStatus newStatus
    ) throws EntityNotFoundException {
        return ResponseEntity.ok(orderService.updateOrder(orderId, newStatus));
    }

    @GetMapping("/export/invoice/pdf/{orderId}")
    public ResponseEntity<byte[]> exportPdf(
            @PathVariable Integer orderId
    ) throws IOException, DocumentException {
        SaleOrder order = (SaleOrder) orderService.getOrderById(orderId);

//        List<Map<String, Object>> queryResults = myService.executeQuery(request);
        ByteArrayOutputStream pdfStream = new InvoicePdfGenerator(order).generateInvoicePDF();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + orderId  + ".pdf");
        headers.setContentLength(pdfStream.size());
        return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
    }

//    @DeleteMapping("/{orderId}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
//        orderService.deleteOrder(orderId);
//        return ResponseEntity.noContent().build();
//    }
}
