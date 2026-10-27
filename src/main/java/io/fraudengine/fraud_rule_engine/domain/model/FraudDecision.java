package io.fraudengine.fraud_rule_engine.domain.model;

import io.fraudengine.fraud_rule_engine.domain.enums.DecisionStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record FraudDecision(
        UUID decisionId,
        String transactionReference,
        BigDecimal amount,
        String currency,
        DecisionStatus status,
        List<String> triggeredRules
) {
    public FraudDecision {
        triggeredRules = List.copyOf(triggeredRules);
    }
}