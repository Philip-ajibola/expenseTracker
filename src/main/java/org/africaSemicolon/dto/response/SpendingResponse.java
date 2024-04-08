package org.africaSemicolon.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpendingResponse {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;
}
