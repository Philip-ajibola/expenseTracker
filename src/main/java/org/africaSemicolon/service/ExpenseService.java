package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Expense;
import org.africaSemicolon.dto.request.DeleteExpenseRequest;
import org.africaSemicolon.dto.request.ExpenseRequest;
import org.africaSemicolon.dto.response.AddExpenseResponse;

public interface ExpenseService {
    AddExpenseResponse addExpense(ExpenseRequest expenseRequest);
    String deleteExpense(DeleteExpenseRequest deleteExpenseRequest);

    Expense findByTitleAndUsername(String expenseTitle,String username);
}
