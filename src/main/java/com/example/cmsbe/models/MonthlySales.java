package com.example.cmsbe.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MonthlySales {
    private List<MonthlySalesItem> monthlySalesItems;
}
