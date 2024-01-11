package com.example.cmsbe.models;

import com.example.cmsbe.models.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE customer SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(length = 100)
    private String name;
    private String phone;
    @Past(message = "Birthday can not be after today.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @Column(length = 200)
    private String contactAddress;
    private String taxCode;
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<SaleOrder> orders;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Debt> debts;
    private boolean isDeleted = false;

    public CustomerDTO toDTO() {
        return new CustomerDTO(this);
    }

    public Double getTotalDebt() {
        double totalDebt = 0;
        for (Debt debt : debts) {
            totalDebt += debt.getAmount();
        }
        return totalDebt;
    }
}
