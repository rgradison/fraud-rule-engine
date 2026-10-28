package io.fraudengine.fraud_rule_engine.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
        name = "fraud_rules",
        uniqueConstraints = @UniqueConstraint(
        name = "uk_fraud_rule_code",
        columnNames = "code"
    )
)
public class FraudRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String code;

    @Column(name = "amount_threshold", precision = 19, scale = 2)
    private BigDecimal amountThreshold;

    @Column(nullable = false)
    private boolean active;

    protected FraudRuleEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getAmountThreshold() {
        return amountThreshold;
    }

    public boolean isActive() {
        return active;
    }
}