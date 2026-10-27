package io.fraudengine.fraud_rule_engine.domain.model;

import java.math.BigDecimal;

public record FraudRule(
    String code,
    BigDecimal amountThreshold,
    boolean active
) {
}
