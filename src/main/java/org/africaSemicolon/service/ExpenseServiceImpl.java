package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Expense;
import org.africaSemicolon.data.repository.Expenses;
import org.africaSemicolon.dto.request.DeleteExpenseRequest;
import org.africaSemicolon.dto.request.ExpenseRequest;
import org.africaSemicolon.exception.ExpenseExistException;
import org.africaSemicolon.exception.ExpenseNotFoundException;
import org.africaSemicolon.exception.IncomeTitleExistException;
import org.africaSemicolon.exception.InvalidAmountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.africaSemicolon.utilities.Mapper.map;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private Expenses expenses;

    @Override
    public Expense addExpense(ExpenseRequest expenseRequest) {
        validateExpense(expenseRequest);
        if(Double.parseDouble(expenseRequest.getAmount())<=0)throw new InvalidAmountException("Invalid Amount");
        return expenses.save(map(expenseRequest));
    }

    private void validateExpense(ExpenseRequest expenseRequest) {
        for(Expense expense1 : expenses.findAll()) {
            if(expense1 != null) {
                if (expense1.getExpenseOwnerName().equalsIgnoreCase(expenseRequest.getExpenseOwnerName())) {
                    if (normalizeString(expense1.getExpenseTitle()).equals(normalizeString(expenseRequest.getExpenseTitle())))
                        throw new ExpenseExistException("Expense Title Exist Already ");
                }
            }
        }
    }
    @Override
    public String deleteExpense(DeleteExpenseRequest deleteExpenseRequest) {
           return map(deleteExpenseRequest, expenses);
    }

    @Override
    public Expense findByTitleAndUsername(String expenseTitle,String username) {
        Expense expense = null;
        for(Expense expense1 : expenses.findAll()) {
            if(expense1 != null) {
                if (expense1.getExpenseOwnerName().equalsIgnoreCase(username)){
                    if (normalizeString(expense1.getExpenseTitle()).equals(normalizeString(expenseTitle)))
                        expense = expense1;
                }
            }
        }
        return expense;
    }


    private static String normalizeString(String str) {
        return str.replaceAll("\\s+", "");
    }
    @Override
    public String deleteAllExpenseOf(String username) {
        expenses.findAll().forEach(expense -> {if(expense.getExpenseOwnerName().equals(username.toLowerCase())) expenses.delete(expense);});
        return String.format("%s Expenses Deleted Successfully",username);
    }

}
