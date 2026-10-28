package io.fraudengine.fraud_rule_engine.infrastructure.persistence.repository;

import io.fraudengine.fraud_rule_engine.infrastructure.persistence.entity.FraudDecisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataFraudDecisionRepository
        extends JpaRepository<FraudDecisionEntity, UUID> {

    Optional<FraudDecisionEntity> findByTransactionReference(
            String transactionReference);
}
