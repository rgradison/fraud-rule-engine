package io.fraudengine.fraud_rule_engine.web.dto;

import io.fraudengine.fraud_rule_engine.domain.enums.DecisionStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record FraudDecisionResponse(
        UUID decisionId,
        String transactionReference,
        BigDecimal amount,
        String currency,
        DecisionStatus status,
        List<String> triggeredRules
) {
    public FraudDecisionResponse {
        triggeredRules = List.copyOf(triggeredRules);
    }
}