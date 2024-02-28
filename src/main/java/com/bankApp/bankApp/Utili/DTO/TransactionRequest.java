package com.bankApp.bankApp.Utili.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class TransactionRequest {
    @JsonProperty("accountId")
    private int accountId;

    @JsonProperty("amount")
    private double amount;

    // Constructors
    public TransactionRequest(int accountId, double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public TransactionRequest() {
    }
}
