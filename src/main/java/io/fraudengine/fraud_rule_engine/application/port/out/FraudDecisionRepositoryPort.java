package io.fraudengine.fraud_rule_engine.application.port.out;

import io.fraudengine.fraud_rule_engine.domain.model.FraudDecision;

import java.util.Optional;

public interface FraudDecisionRepositoryPort {

    Optional<FraudDecision> findByTransactionReference(String transactionReference);

    FraudDecision save(FraudDecision decision);
}