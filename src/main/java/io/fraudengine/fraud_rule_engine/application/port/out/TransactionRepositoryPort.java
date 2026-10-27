package io.fraudengine.fraud_rule_engine.application.port.out;

import io.fraudengine.fraud_rule_engine.domain.model.FraudTransaction;

public interface TransactionRepositoryPort {

    FraudTransaction save(FraudTransaction transaction);
}