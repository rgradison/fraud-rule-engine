package io.fraudengine.fraud_rule_engine.infrastructure.persistence.repository;

import io.fraudengine.fraud_rule_engine.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface SpringDataTransactionRepository
        extends JpaRepository<TransactionEntity, Long> {

    Optional<TransactionEntity> findByTransactionReference(
            String transactionReference);

    @Modifying
    @Query(value = """
        INSERT INTO fraud_transaction (
            transaction_reference,
            account_number,
            amount,
            currency
        )
        VALUES (:transactionReference, :accountNumber, :amount, :currency)
        ON CONFLICT (transaction_reference) DO NOTHING
        """, nativeQuery = true)
    int insertIfAbsent(
            @Param("transactionReference") String transactionReference,
            @Param("accountNumber") String accountNumber,
            @Param("amount") BigDecimal amount,
            @Param("currency") String currency
    );
}