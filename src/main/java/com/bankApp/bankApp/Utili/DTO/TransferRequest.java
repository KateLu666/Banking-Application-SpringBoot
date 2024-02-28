package com.bankApp.bankApp.Utili.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferRequest {
    @JsonProperty("fromAccountId")
    private int fromAccountId;

    @JsonProperty("toAccountId")
    private int toAccountId;

    @JsonProperty("amount")
    private double amount;

    // Constructors
    public TransferRequest(int fromAccountId, int toAccountId, double amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public TransferRequest() {
    }
}
