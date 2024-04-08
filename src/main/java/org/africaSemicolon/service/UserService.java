package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Expense;
import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.dto.request.*;
import org.africaSemicolon.dto.response.AddExpenseResponse;
import org.africaSemicolon.dto.response.AddIncomeResponse;
import org.africaSemicolon.dto.response.CreateUserResponse;
import org.africaSemicolon.dto.response.SpendingResponse;

import java.util.List;

public interface UserService {
    AddExpenseResponse addExpense(ExpenseRequest expenseRequest);
    CreateUserResponse registerUser(RegisterRequest request);

    AddIncomeResponse addIncome(IncomeRequest incomeRequest);

    String deleteExpense(DeleteExpenseRequest deleteExpenseRequest);

    String deleteIncome(DeleteIncomeRequest deleteIncomeRequest);

    String login(LoginRequest loginRequest);
    SpendingResponse showSpendings(SpendingRequest spendingRequest);
    List<Income> getAllIncome(String username);

    List<Expense> findAllExpenses(String username);
    String deleteUser(String username);

    String logout(LogoutRequest logoutRequest);
}
