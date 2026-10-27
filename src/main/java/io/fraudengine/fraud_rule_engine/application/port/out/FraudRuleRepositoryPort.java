package io.fraudengine.fraud_rule_engine.application.port.out;

import io.fraudengine.fraud_rule_engine.domain.model.FraudRule;

import java.util.List;

public interface FraudRuleRepositoryPort {

    List<FraudRule> findActiveRules();
}