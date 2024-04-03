package org.africaSemicolon.data.repository;

import org.africaSemicolon.data.model.Category;
import org.africaSemicolon.data.model.Expense;
import org.africaSemicolon.dto.ExpenseRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class ExpensesTest {
    @Autowired
    private Expenses expenses;
    @Test
    public void testThatExpenseAreSaved(){
        Expense expense = new Expense();
        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setAmount(BigDecimal.valueOf(2000));
        expenseRequest.setCategory(Category.CLOTHES);
        expense.setCategory(expenseRequest.getCategory());
        expense.setAmount(expenseRequest.getAmount());
        expenses.save(expense);
        assertEquals(1,expenses.count());
    }

}