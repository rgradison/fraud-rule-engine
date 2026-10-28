package io.fraudengine.fraud_rule_engine.infrastructure.persistence.adapter;

import io.fraudengine.fraud_rule_engine.application.port.out.FraudRuleRepositoryPort;
import io.fraudengine.fraud_rule_engine.domain.model.FraudRule;
import io.fraudengine.fraud_rule_engine.infrastructure.persistence.repository.SpringDataFraudRuleRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FraudRulePersistenceAdapter
        implements FraudRuleRepositoryPort {

    private final SpringDataFraudRuleRepository fraudrulerepository;

    public FraudRulePersistenceAdapter(
            SpringDataFraudRuleRepository repository) {
        this.fraudrulerepository = repository;
    }

    @Override
    public List<FraudRule> findActiveRules() {
        return fraudrulerepository.findByActiveTrue()
            .stream()
            .map(entity -> new FraudRule(
                entity.getCode(),
                entity.getAmountThreshold(),
                entity.isActive()
            ))
            .toList();
    }
}