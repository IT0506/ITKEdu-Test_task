package com.example.wallet.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.model.Wallet;
import com.example.wallet.service.WalletService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService service;
    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet() {
        Wallet wallet = service.createWallet(); // generates UUID + balance=0
        return ResponseEntity.ok(wallet);
    }
    @PostMapping("/wallet")
    public ResponseEntity<Void> operate(@RequestBody @Valid WalletOperationRequest request) {
        service.processOperation(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<Long> getBalance(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getBalance(id));
    }
}
