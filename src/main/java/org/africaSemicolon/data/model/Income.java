package org.africaSemicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document("income")
public class Income {
    @Id
    private String id;
    private String username;
    private String incomeTitle;
    private BigDecimal income = BigDecimal.valueOf(0);
    private LocalDate addIncomeDay = LocalDate.now();
}
