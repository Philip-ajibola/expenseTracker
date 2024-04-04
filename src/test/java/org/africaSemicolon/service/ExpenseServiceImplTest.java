package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Category;
import org.africaSemicolon.data.model.Expense;
import org.africaSemicolon.data.repository.Expenses;
import org.africaSemicolon.dto.request.DeleteExpenseRequest;
import org.africaSemicolon.dto.request.ExpenseRequest;
import org.africaSemicolon.exception.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ExpenseServiceImplTest {
    private ExpenseRequest expenseRequest;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private Expenses expenses;
    @BeforeEach
    public void initializer(){
        expenses.deleteAll();
        expenseRequest = new ExpenseRequest();
        expenseRequest.setAmount("2000");
        expenseRequest.setCategory(Category.CLOTHES);
        expenseRequest.setExpenseOwnerName("username");
        expenseRequest.setExpenseTitle("title");
    }
    @Test
    public void testThatExpensesCanBeAdded(){
        expenseService.addExpense(expenseRequest);
        assertEquals(1,expenses.count());
    }
    @Test
    public void testThatWhenAmountIsLessThanZeroExceptionIsThrown(){
        expenseRequest.setAmount("0");
        assertThrows(InvalidAmountException.class, ()->expenseService.addExpense(expenseRequest));
    }
    @Test
    public void testThatExpenseCanBeDeleted(){
        expenseService.addExpense(expenseRequest);
        DeleteExpenseRequest deleteExpenseRequest = new DeleteExpenseRequest();
        deleteExpenseRequest.setUsername(expenseRequest.getExpenseOwnerName());
        deleteExpenseRequest.setExpenseTitle(expenseRequest.getExpenseTitle());
        expenseService.deleteExpense(deleteExpenseRequest);
        assertEquals(0,expenses.count());
    }


}