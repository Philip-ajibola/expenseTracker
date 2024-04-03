package org.africaSemicolon.dto;

import lombok.Data;
import org.africaSemicolon.data.model.Category;

import java.math.BigDecimal;
@Data
public class ExpenseRequest {
    private BigDecimal amount;
    private Category category;
}
