package com.example.cmsbe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

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
    private LocalDate dateOfBirth;
    @Column(length = 200)
    private String contactAddress;
    private String taxCode;
    private boolean isDeleted = false;
}
