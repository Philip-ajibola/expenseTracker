package org.africaSemicolon.data.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document("income")
public class Income {
    private BigDecimal income;
    private LocalDate addIncomeDay;
}
