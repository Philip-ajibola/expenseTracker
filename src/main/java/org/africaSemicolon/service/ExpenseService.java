package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Expense;
import org.africaSemicolon.dto.request.DeleteExpenseRequest;
import org.africaSemicolon.dto.request.ExpenseRequest;
import org.africaSemicolon.dto.response.AddExpenseResponse;

public interface ExpenseService {
    Expense addExpense(ExpenseRequest expenseRequest);
    String deleteExpense(DeleteExpenseRequest deleteExpenseRequest);

    Expense findByTitleAndUsername(String expenseTitle,String username);

    String deleteAllExpenseOf(String username);
}
