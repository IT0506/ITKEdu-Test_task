package com.example.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.wallet.dto.OperationType;
import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.WalletService;

@SpringBootTest
@ActiveProfiles("test")
class WalletConcurrencyTest {

    @Autowired WalletService service;
    @Autowired WalletRepository repository;

    @Test
    void shouldHandleConcurrentWithdrawals() throws Exception {

        // Generate a string ID (UUID as string)
        UUID id = java.util.UUID.randomUUID();

        // Save wallet with string ID
        repository.save(new Wallet(id, 10000L, 0L));

        ExecutorService executor = Executors.newFixedThreadPool(20);
        int operations = 100;
        java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(operations);

        for (int i = 0; i < operations; i++) {
            executor.submit(() -> {
                try {
                    service.processOperation(
                        new WalletOperationRequest(id, OperationType.WITHDRAW, 10L)
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        // Wait for all threads to finish
        latch.await(); // blocks until latch counts down to 0
        executor.shutdown();

        // Reload wallet and check final balance
        Wallet wallet = repository.findById(id).orElseThrow();
        assertEquals(10000L - operations * 10L, wallet.getBalance());
    }
}