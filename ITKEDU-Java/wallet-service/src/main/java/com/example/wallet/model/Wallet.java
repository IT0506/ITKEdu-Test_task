package com.example.wallet.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    private UUID id=UUID.randomUUID();

    @Column(nullable = false)
    private Long balance=1000L;

    @Version
    @Column(nullable = false)
    private Long version=0L;

    public Wallet() {}

    public Wallet(UUID id, Long balance, Long version) {
        this.id = id;
        this.balance = balance;
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getVersion() {
        return version;
    }

	public void setId(UUID randomUUID) {
		this.id=id;
		
	}
}