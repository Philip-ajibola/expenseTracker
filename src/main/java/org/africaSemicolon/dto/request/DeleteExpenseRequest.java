package org.africaSemicolon.dto.request;

import lombok.Data;

@Data
public class DeleteExpenseRequest {
    private String expenseTitle;
    private String username;
}
