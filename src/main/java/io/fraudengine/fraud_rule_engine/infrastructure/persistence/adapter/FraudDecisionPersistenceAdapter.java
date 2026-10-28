package io.fraudengine.fraud_rule_engine.infrastructure.persistence.adapter;

import io.fraudengine.fraud_rule_engine.application.port.out.FraudDecisionRepositoryPort;
import io.fraudengine.fraud_rule_engine.domain.model.FraudDecision;
import io.fraudengine.fraud_rule_engine.infrastructure.persistence.entity.FraudDecisionEntity;
import io.fraudengine.fraud_rule_engine.infrastructure.persistence.repository.SpringDataFraudDecisionRepository;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FraudDecisionPersistenceAdapter
        implements FraudDecisionRepositoryPort {

    private final SpringDataFraudDecisionRepository repository;

    public FraudDecisionPersistenceAdapter(
            SpringDataFraudDecisionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<FraudDecision> findByTransactionReference(
            String transactionReference) {

        return repository.findByTransactionReference(transactionReference)
                .map(this::toDomain);
    }

    @Override
    public FraudDecision save(FraudDecision decision) {

        String triggeredRules = decision.triggeredRules().stream()
                .collect(Collectors.joining(","));

        FraudDecisionEntity entity = new FraudDecisionEntity(
                decision.decisionId(),
                decision.transactionReference(),
                decision.amount(),
                decision.currency(),
                decision.status(),
                triggeredRules
        );

        FraudDecisionEntity saved = repository.save(entity);

        return toDomain(saved);
    }

    private FraudDecision toDomain(FraudDecisionEntity entity) {

        List<String> rules = entity.getTriggeredRules().isBlank()
                ? List.of()
                : Arrays.asList(entity.getTriggeredRules().split(","));

        return new FraudDecision(
                entity.getDecisionId(),
                entity.getTransactionReference(),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getStatus(),
                rules
        );
    }
}