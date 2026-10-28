
package io.fraudengine.fraud_rule_engine.infrastructure.persistence.entity;

import io.fraudengine.fraud_rule_engine.domain.enums.DecisionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "fraud_decisions",
        uniqueConstraints = @UniqueConstraint(
            name = "uk_fraud_decision_transaction_reference",
            columnNames = "transaction_reference"
        )
)
public class FraudDecisionEntity {

    @Id
    @Column(name = "decision_id", nullable = false, updatable = false)
    private UUID decisionId;

    @Column(
            name = "transaction_reference",
            nullable = false,
            updatable = false
    )
    private String transactionReference;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DecisionStatus status;

    @Column(name = "triggered_rules", nullable = false, columnDefinition = "text")
    private String triggeredRules;

    protected FraudDecisionEntity() {
    }

    public FraudDecisionEntity(
            UUID decisionId,
            String transactionReference,
            BigDecimal amount,
            String currency,
            DecisionStatus status,
            String triggeredRules) {
        this.decisionId = decisionId;
        this.transactionReference = transactionReference;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.triggeredRules = triggeredRules;
    }

    public UUID getDecisionId() {
        return decisionId;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public DecisionStatus getStatus() {
        return status;
    }

    public String getTriggeredRules() {
        return triggeredRules;
    }
}