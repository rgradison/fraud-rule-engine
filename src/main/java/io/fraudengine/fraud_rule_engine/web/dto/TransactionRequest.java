package io.fraudengine.fraud_rule_engine.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//We use BigDecimal rather than double for financial amounts to avoid binary floating-point rounding problems.
import java.math.BigDecimal;

public record TransactionRequest(
      @NotBlank String transactionReference,
      @NotBlank String accountNumber,
      @NotNull
      @DecimalMin(value = "0.00", inclusive = false)
      BigDecimal amount,
      @NotBlank
      String currency) {}
