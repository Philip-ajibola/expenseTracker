package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Expense;
import org.africaSemicolon.data.repository.Expenses;
import org.africaSemicolon.dto.request.DeleteExpenseRequest;
import org.africaSemicolon.dto.request.ExpenseRequest;
import org.africaSemicolon.dto.response.AddExpenseResponse;
import org.africaSemicolon.exception.InvalidAmountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.africaSemicolon.utilities.Mapper.map;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private Expenses expenses;

    @Override
    public AddExpenseResponse addExpense(ExpenseRequest expenseRequest) {
        if(Double.parseDouble(expenseRequest.getAmount())<=0)throw new InvalidAmountException("Invalid Amount");
        Expense expense = expenses.save(map(expenseRequest));
        return map(expense);
    }

    @Override
    public String deleteExpense(DeleteExpenseRequest deleteExpenseRequest) {
           return map(deleteExpenseRequest, expenses);
    }

    @Override
    public Expense findByTitleAndUsername(String expenseTitle,String username) {
        return expenses.findByExpenseTitleAndExpenseOwnerName(expenseTitle,username);
    }

}
