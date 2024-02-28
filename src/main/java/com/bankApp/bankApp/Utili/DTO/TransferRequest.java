package com.bankApp.bankApp.Utili.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferRequest {
    @JsonProperty("fromUserId")
    private int fromUserId;

    @JsonProperty("toUserId")
    private int toUserId;

    @JsonProperty("amount")
    private double amount;

    // Constructors
    public TransferRequest(int fromUserId, int toUserId, double amount) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
    }

    public TransferRequest() {
    }
}
