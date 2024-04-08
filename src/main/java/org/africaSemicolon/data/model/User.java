package org.africaSemicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("users")
public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isLoggedIn;
    private BigDecimal balance = BigDecimal.valueOf(0);
    @DBRef
    private List<Income> income = new ArrayList<>();
    @Id
    private String id;
    @DBRef
    private List<Expense> expenseList = new ArrayList<>();
}
