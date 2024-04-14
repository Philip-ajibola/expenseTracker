package org.africaSemicolon.dto.request;

import lombok.Data;

@Data
public class AddIncomeRequest {
    private String income;
    private String username;
    private String incomeTitle;
}
