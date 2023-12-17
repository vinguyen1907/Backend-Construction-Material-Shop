package com.example.cmsbe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Positive
    private Double amount;
    private Boolean alreadyPaid;
    @OneToOne
    private Order order;
    @ManyToOne
    private Customer customer;
}
