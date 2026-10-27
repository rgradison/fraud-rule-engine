package io.fraudengine.fraud_rule_engine.infrastructure.persistence.adapter;

import io.fraudengine.fraud_rule_engine.application.port.out.TransactionRepositoryPort;
import io.fraudengine.fraud_rule_engine.domain.model.FraudTransaction;
import io.fraudengine.fraud_rule_engine.infrastructure.persistence.entity.TransactionEntity;
import io.fraudengine.fraud_rule_engine.infrastructure.persistence.repository.SpringDataTransactionRepository;

import org.springframework.stereotype.Repository;

@Repository
public class TransactionPersistenceAdapter
        implements TransactionRepositoryPort {

    private final SpringDataTransactionRepository repository;

    public TransactionPersistenceAdapter(
            SpringDataTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public FraudTransaction save(FraudTransaction transaction) {

        TransactionEntity entity = new TransactionEntity(
                transaction.transactionReference(),
                transaction.accountNumber(),
                transaction.amount(),
                transaction.currency()
        );

        TransactionEntity saved = repository.save(entity);

        return new FraudTransaction(
                saved.getTransactionReference(),
                saved.getAccountNumber(),
                saved.getAmount(),
                saved.getCurrency()
        );
    }
}