package org.africaSemicolon.data.repository;

import org.africaSemicolon.data.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface Expenses extends MongoRepository<Expense,String> {
    Expense findByExpenseTitleAndExpenseOwnerName(String expenseTitle, String username);

    boolean existsByTitleAndExpenseOwnerName(String expenseTitle, String expenseOwnerName);
}
