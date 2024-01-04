package com.example.cmsbe.models.dto;

import com.example.cmsbe.models.Debt;
import lombok.Data;

@Data
public class DebtDTO {
    private Integer id;
    private Double amount;
    private Boolean alreadyPaid;
    private Integer orderId;
    private Integer customerId;

    public DebtDTO(Debt debt) {
        this.id = debt.getId();
        this.amount = debt.getAmount();
        this.alreadyPaid = debt.getAlreadyPaid();
        this.orderId = debt.getOrder().getId();
        this.customerId = debt.getCustomer().getId();
    }
}
