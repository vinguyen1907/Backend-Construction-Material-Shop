package com.example.cmsbe.models;

import com.example.cmsbe.models.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Overview {
    private MonthlySales monthlySales;
    private OrderStatistics orderStatistics;
    private List<CustomerDTO> valuableCustomers;
//    private List<Product> lowOnStockItems;
    private List<AggregatedMonthlySaleItem> aggregatedMonthlySales;
}
