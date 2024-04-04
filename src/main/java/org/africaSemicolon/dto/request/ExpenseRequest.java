package org.africaSemicolon.dto.request;

import lombok.Data;
import org.africaSemicolon.data.model.Category;
import org.africaSemicolon.data.model.Expense;

import java.math.BigDecimal;
@Data
public class ExpenseRequest {
    private String expenseTitle;
    private String expenseOwnerName;
    private String amount;
    private Category category;


}
