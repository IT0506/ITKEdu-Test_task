package com.example.wallet.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.wallet.dto.OperationType;
import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.exception.InsufficientFundsException;
import com.example.wallet.exception.InvalidAmountException;
import com.example.wallet.exception.WalletNotFoundException;
import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository repository;

    @Transactional
    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setId(UUID.fromString("11111111-1111-1111-1111-111111111111")); // generate UUID
        wallet.setBalance(10000L);           // initial balance 0
        return repository.save(wallet);
    }
    @Transactional
    public void processOperation(WalletOperationRequest request) {

        Wallet wallet = repository.findByIdForUpdate(request.walletId())
                .orElseThrow(() -> new WalletNotFoundException());

        if (request.amount() <= 0)
            throw new InvalidAmountException();

        if (request.operationType() == OperationType.WITHDRAW) {
            if (wallet.getBalance() < request.amount())
                throw new InsufficientFundsException();

            wallet.setBalance(wallet.getBalance() - request.amount());
        } else {
            wallet.setBalance(wallet.getBalance() + request.amount());
        }

        repository.save(wallet);
    }

    public Long getBalance(UUID id) {
        return repository.findById(id)
                .orElseThrow(WalletNotFoundException::new)
                .getBalance();
    }
}
