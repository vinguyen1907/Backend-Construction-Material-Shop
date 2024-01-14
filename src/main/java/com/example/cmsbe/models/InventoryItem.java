package com.example.cmsbe.models;

import com.example.cmsbe.models.dto.InventoryItemDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
//    @Past(message = "Manufacturing date must be in the past.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate manufacturingDate;
//    @Future(message = "Expiry date must be in the future.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate importedDate;
    private Double importedPrice = 0.0;

    public InventoryItemDTO toDTO() {
        return new InventoryItemDTO(
                this.id,
                this.product.getId(),
                this.warehouse,
                this.quantity,
                this.manufacturingDate,
                this.expiryDate,
                this.importedDate,
                this.importedPrice
        );
    }
}
