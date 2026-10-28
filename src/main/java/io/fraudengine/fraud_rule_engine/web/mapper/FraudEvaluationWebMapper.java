package io.fraudengine.fraud_rule_engine.web.mapper;

import io.fraudengine.fraud_rule_engine.application.command.EvaluateTransactionCommand;
import io.fraudengine.fraud_rule_engine.domain.model.FraudDecision;
import io.fraudengine.fraud_rule_engine.web.dto.EvaluateTransactionRequest;
import io.fraudengine.fraud_rule_engine.web.dto.FraudDecisionResponse;

import org.springframework.stereotype.Component;

@Component
public class FraudEvaluationWebMapper {

    public EvaluateTransactionCommand toCommand(
            EvaluateTransactionRequest request) {

        return new EvaluateTransactionCommand(
                request.transactionReference(),
                request.accountNumber(),
                request.amount(),
                request.currency()
        );
    }

    public FraudDecisionResponse toResponse(FraudDecision decision) {

        return new FraudDecisionResponse(
                decision.decisionId(),
                decision.transactionReference(),
                decision.amount(),
                decision.currency(),
                decision.status(),
                decision.triggeredRules()
        );
    }
}