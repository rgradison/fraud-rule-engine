package io.fraudengine.fraud_rule_engine.domain.model;

import java.math.BigDecimal;

public record FraudTransaction(
        String transactionReference,
        String accountNumber,
        BigDecimal amount,
        String currency
) {}