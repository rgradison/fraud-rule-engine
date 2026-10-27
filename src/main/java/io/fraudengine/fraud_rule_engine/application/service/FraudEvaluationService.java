package io.fraudengine.fraud_rule_engine.application.service;

import io.fraudengine.fraud_rule_engine.application.command.EvaluateTransactionCommand;
import io.fraudengine.fraud_rule_engine.application.port.in.EvaluateTransactionUseCase;
import io.fraudengine.fraud_rule_engine.application.port.out.FraudDecisionRepositoryPort;
import io.fraudengine.fraud_rule_engine.application.port.out.FraudRuleRepositoryPort;
import io.fraudengine.fraud_rule_engine.application.port.out.TransactionRepositoryPort;

import io.fraudengine.fraud_rule_engine.domain.enums.DecisionStatus;
import io.fraudengine.fraud_rule_engine.domain.model.FraudDecision;
import io.fraudengine.fraud_rule_engine.domain.model.FraudRule;
import io.fraudengine.fraud_rule_engine.domain.model.FraudTransaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FraudEvaluationService implements EvaluateTransactionUseCase {

    private final TransactionRepositoryPort transactionRepository;
    private final FraudRuleRepositoryPort fraudRuleRepository;
    private final FraudDecisionRepositoryPort fraudDecisionRepository;

    public FraudEvaluationService(
            TransactionRepositoryPort transactionRepository,
            FraudRuleRepositoryPort fraudRuleRepository,
            FraudDecisionRepositoryPort fraudDecisionRepository) {

        this.transactionRepository = transactionRepository;
        this.fraudRuleRepository = fraudRuleRepository;
        this.fraudDecisionRepository = fraudDecisionRepository;
    }

    @Override
    @Transactional
    public FraudDecision evaluate(EvaluateTransactionCommand command) {

        // 1. Check whether a decision already exists.
        var existingDecision =
                fraudDecisionRepository.findByTransactionReference(
                        command.transactionReference());

        if (existingDecision.isPresent()) {
            return existingDecision.get();
        }

        // 2. Convert the command into a domain transaction.
        FraudTransaction transaction = new FraudTransaction(
                command.transactionReference(),
                command.accountNumber(),
                command.amount(),
                command.currency()
        );

        // 3. Save the transaction.
        transactionRepository.save(transaction);

        // 4. Load active fraud rules.
        List<FraudRule> rules = fraudRuleRepository.findActiveRules();

        // 5. Evaluate the transaction against the rules.
        List<String> triggeredRules = rules.stream()
                .filter(FraudRule::active)
                .filter(rule -> rule.amountThreshold() != null)
                .filter(rule -> command.amount()
                        .compareTo(rule.amountThreshold()) > 0)
                .map(FraudRule::code)
                .toList();

        // 6. Determine the decision.
        DecisionStatus status = triggeredRules.isEmpty() ? DecisionStatus.APPROVED
                                                         : DecisionStatus.REVIEW;

        // 7. Create the fraud decision.
        FraudDecision decision = new FraudDecision(
                UUID.randomUUID(),
                command.transactionReference(),
                command.amount(),
                command.currency(),
                status,
                triggeredRules
        );

        // 8. Save and return the decision.
        return fraudDecisionRepository.save(decision);
    }
}