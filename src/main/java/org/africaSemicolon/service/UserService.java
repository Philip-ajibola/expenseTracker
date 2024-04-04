package org.africaSemicolon.service;

import org.africaSemicolon.dto.request.*;
import org.africaSemicolon.dto.response.AddExpenseResponse;
import org.africaSemicolon.dto.response.AddIncomeResponse;
import org.africaSemicolon.dto.response.CreateUserResponse;

public interface UserService {
    AddExpenseResponse addExpense(ExpenseRequest expenseRequest);
    CreateUserResponse registerUser(RegisterRequest request);

    AddIncomeResponse addIncome(IncomeRequest incomeRequest);

    void deleteExpense(DeleteExpenseRequest deleteExpenseRequest);

    void deleteIncome(DeleteIncomeRequest deleteIncomeRequest);

    void login(LoginRequest loginRequest);
}
