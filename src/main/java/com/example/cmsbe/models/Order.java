package com.example.cmsbe.models;

import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="order_type",
        discriminatorType = DiscriminatorType.STRING)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_table")
//@SQLDelete(sql = "UPDATE order_table SET is_deleted = true WHERE id=?")
//@SQLRestriction("is_deleted = false")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    @ManyToOne
    protected User createdUser;
    protected Double discount;
    @Enumerated(EnumType.STRING)
    protected OrderStatus status;
    @Column(name="order_type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    protected OrderType orderType = OrderType.SALE;
    protected Double total;

    // Auditing variable
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDateTime createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDateTime updatedTime;
//    private Boolean isDeleted = false;

    public OrderDTO toDTO() {
        return new OrderDTO(this);
    }

    @PrePersist
    public void prePersist() {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedTime = LocalDateTime.now();
    }
}
