package io.fraudengine.fraud_rule_engine.infrastructure.persistence.repository;

import io.fraudengine.fraud_rule_engine.infrastructure.persistence.entity.FraudRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataFraudRuleRepository
        extends JpaRepository<FraudRuleEntity, Long> {

    List<FraudRuleEntity> findByActiveTrue();
}