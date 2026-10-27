package io.fraudengine.fraud_rule_engine.web.dto;

import java.math.BigDecimal;
import java.util.*;

public record FraudDecisionResponse(UUID decisionId,
                                    String transactionReference,
                                    BigDecimal amount,
                                    String currency,
                                    String status,
                                    List<String> triggeredRules) {
}
