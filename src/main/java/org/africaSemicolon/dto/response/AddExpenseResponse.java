package org.africaSemicolon.dto.response;

import lombok.Data;
import org.africaSemicolon.data.model.Category;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class AddExpenseResponse {
    @Id
    private String id;
    private BigDecimal amount;
    private String date;
    private Category category;
}
