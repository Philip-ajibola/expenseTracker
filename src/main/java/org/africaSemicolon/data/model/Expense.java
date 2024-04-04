package org.africaSemicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document("Expense")
public class Expense {
    private String expenseOwnerName;
    @Id
    private String id;
    private String expenseTitle;
    private BigDecimal amount;
    private LocalDate date = LocalDate.now();
    private Category category;
}
