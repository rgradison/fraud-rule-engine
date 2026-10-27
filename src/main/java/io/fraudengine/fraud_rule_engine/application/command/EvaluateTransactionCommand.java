package io.fraudengine.fraud_rule_engine.application.command;

import java.math.BigDecimal;

//a Command is an object that carries the information needed to request an
// action from the application.
public record EvaluateTransactionCommand(
        String transactionReference,
        String accountNumber,
        BigDecimal amount,
        String currency
){
}