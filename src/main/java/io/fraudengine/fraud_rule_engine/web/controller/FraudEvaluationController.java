package io.fraudengine.fraud_rule_engine.web.controller;

import io.fraudengine.fraud_rule_engine.application.port.in.EvaluateTransactionUseCase;
import io.fraudengine.fraud_rule_engine.domain.model.FraudDecision;
import io.fraudengine.fraud_rule_engine.web.dto.EvaluateTransactionRequest;
import io.fraudengine.fraud_rule_engine.web.dto.FraudDecisionResponse;
import io.fraudengine.fraud_rule_engine.web.mapper.FraudEvaluationWebMapper;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fraud-evaluations")
public class FraudEvaluationController {

    private final EvaluateTransactionUseCase evaluateTransactionUseCase;
    private final FraudEvaluationWebMapper mapper;

    public FraudEvaluationController(
            EvaluateTransactionUseCase evaluateTransactionUseCase,
            FraudEvaluationWebMapper mapper) {
        this.evaluateTransactionUseCase = evaluateTransactionUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<FraudDecisionResponse> evaluate(
            @Valid @RequestBody EvaluateTransactionRequest request) {

        FraudDecision decision = evaluateTransactionUseCase.evaluate(
                mapper.toCommand(request)
        );

        return ResponseEntity.ok(mapper.toResponse(decision));
    }
}