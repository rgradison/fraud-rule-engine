package io.fraudengine.fraud_rule_engine.application.port.in;

import io.fraudengine.fraud_rule_engine.application.command.EvaluateTransactionCommand;
import io.fraudengine.fraud_rule_engine.domain.model.FraudDecision;

public interface EvaluateTransactionUseCase {
    FraudDecision evaluate(EvaluateTransactionCommand command);
}
