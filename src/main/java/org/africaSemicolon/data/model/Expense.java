package org.africaSemicolon.data.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document("Expense")
public class Expense {
    private Budget budget;
    private BigDecimal balance;
    private String category;
}
