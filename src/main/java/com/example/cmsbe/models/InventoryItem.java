package com.example.cmsbe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Warehouse warehouse;
    private int quantity;
    @Past(message = "Manufacturing date must be in the past.")
    private LocalDate manufacturingDate;
    @Future(message = "Expiry date must be in the future.")
    private LocalDate expiryDate;
    @Past(message = "Imported date must be in the past.")
    private LocalDate importedDate;
}
