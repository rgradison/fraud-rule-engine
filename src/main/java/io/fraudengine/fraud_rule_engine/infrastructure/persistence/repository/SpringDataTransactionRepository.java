package io.fraudengine.fraud_rule_engine.infrastructure.persistence.repository;

import io.fraudengine.fraud_rule_engine.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataTransactionRepository
        extends JpaRepository<TransactionEntity, Long> {

    Optional<TransactionEntity> findByTransactionReference(
            String transactionReference);
}