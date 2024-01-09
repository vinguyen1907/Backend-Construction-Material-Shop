package com.example.cmsbe.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySalesItem {
    @JsonFormat(pattern = "yyyy-MM")
    private LocalDate month;
    private Double revenue;
}
