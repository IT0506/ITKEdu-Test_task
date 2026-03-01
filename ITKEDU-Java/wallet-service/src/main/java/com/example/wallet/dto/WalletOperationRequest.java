package com.example.wallet.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WalletOperationRequest(

        @NotNull(message = "walletId is required")
        UUID walletId,

        @NotNull(message = "operationType is required")
        OperationType operationType,

        @NotNull(message = "amount is required")
        @Min(value = 1, message = "amount must be greater than 0")
        Long amount
) {}