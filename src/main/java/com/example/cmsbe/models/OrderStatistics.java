package com.example.cmsbe.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatistics {
    private long processingOrderCount;
    private long deliveringOrderCount;
    private long completedOrderCount;
    private long cancelledOrderCount;
}
