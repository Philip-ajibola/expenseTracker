package org.africaSemicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@Document("users")
public class User {
    private String firstName;
    private String lastName;
    private String username;
    private BigDecimal balance;
    private Income income;
    @Id
    private String id;
    @DBRef
    private List<Expense> expenseList;
}
