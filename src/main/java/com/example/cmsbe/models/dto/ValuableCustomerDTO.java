package com.example.cmsbe.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ValuableCustomerDTO {
    private Integer id;
    private String name;
    private String phone;
    private Integer ordersCount;
    private Double ordersValue;
}
