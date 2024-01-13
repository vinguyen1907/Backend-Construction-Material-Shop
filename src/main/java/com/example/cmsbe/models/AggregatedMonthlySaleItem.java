package com.example.cmsbe.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregatedMonthlySaleItem {
    private Integer productId;
    private String productName;
    private Double currentMonthValue;
    private Double previousMonthValue;
}
