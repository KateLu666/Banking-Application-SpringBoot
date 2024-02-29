package com.bankApp.bankApp.Utili.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class TransactionRequest {
    @JsonProperty("amount")
    private double amount;

    // Constructors
    public TransactionRequest(double amount) {
        this.amount = amount;
    }

    public TransactionRequest() {
    }
}
