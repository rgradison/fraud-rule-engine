package io.fraudengine.fraud_rule_engine.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
    name = "fraud_transactions",
    uniqueConstraints = @UniqueConstraint(
            name = "uk_transaction_reference",
            columnNames = "transaction_reference"
    )
)
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "transaction_reference",
            nullable = false,
            updatable = false
    )
    private String transactionReference;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    protected TransactionEntity() {
        // Required by JPA.
    }

    public TransactionEntity(
            String transactionReference,
            String accountNumber,
            BigDecimal amount,
            String currency) {
        this.transactionReference = transactionReference;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}