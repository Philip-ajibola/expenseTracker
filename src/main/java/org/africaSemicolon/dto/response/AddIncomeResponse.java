package org.africaSemicolon.dto.response;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class AddIncomeResponse {
    private String username;
    private BigDecimal income;
    private String id;
    private String date;
}
