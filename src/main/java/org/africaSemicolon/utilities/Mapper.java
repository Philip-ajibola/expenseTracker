package org.africaSemicolon.utilities;

import org.africaSemicolon.data.model.Expense;
import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.data.model.User;
import org.africaSemicolon.data.repository.Expenses;
import org.africaSemicolon.data.repository.Incomes;
import org.africaSemicolon.dto.request.*;
import org.africaSemicolon.dto.response.AddExpenseResponse;
import org.africaSemicolon.dto.response.AddIncomeResponse;
import org.africaSemicolon.dto.response.CreateUserResponse;
import org.africaSemicolon.exception.IncomeNotFoundException;
import org.africaSemicolon.exception.InvalidAmountException;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Mapper {
    public static Expense map(ExpenseRequest expenseRequest) {
        Expense expense = new Expense();
        validateAmount(expenseRequest.getAmount());
        expense.setAmount(BigDecimal.valueOf(Double.parseDouble(expenseRequest.getAmount())));
        expense.setCategory(expenseRequest.getCategory());
        expense.setExpenseOwnerName(expenseRequest.getExpenseOwnerName());
        expense.setExpenseTitle(expenseRequest.getExpenseTitle());
        return expense;
    }

    public static AddExpenseResponse map(Expense expense) {
        AddExpenseResponse addExpenseResponse = new AddExpenseResponse();
        addExpenseResponse.setDate(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(expense.getDate()));
        addExpenseResponse.setId(expense.getId());
        addExpenseResponse.setCategory(expense.getCategory());
        addExpenseResponse.setAmount(expense.getAmount());
        return addExpenseResponse;
    }

    public static String map(DeleteExpenseRequest deleteExpenseRequest, Expenses expenses) {
        Expense expense = expenses.findByExpenseTitleAndExpenseOwnerName(deleteExpenseRequest.getExpenseTitle(),deleteExpenseRequest.getUsername());
        if (expense==null) throw new NoSuchElementException("Expense not Found");
        expenses.delete(expense);
        return "Expense Deleted Successfully";
    }
    public static Income map(IncomeRequest incomeRequest) {
        Income income = new Income();
        income.setIncomeTitle(incomeRequest.getIncomeTitle());
        validateAmount(incomeRequest.getIncome());
        income.setIncome(BigDecimal.valueOf(Double.parseDouble(incomeRequest.getIncome())));
        income.setUsername(incomeRequest.getUsername());
        if (income.getIncome().compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidAmountException("Invalid Income Amount");
        return income;
    }

    private static void validateAmount(String incomeRequest) {
        if (!incomeRequest.matches("^\\d+(\\.\\d+)?$")) throw new InputMismatchException("Invalid Input");
    }

    public static String map(DeleteIncomeRequest deleteIncomeRequest, Incomes incomes) {
        Income income = incomes.findByUsernameAndIncomeTitle(deleteIncomeRequest.getUsername(), deleteIncomeRequest.getIncomeTitle());
        if(income== null) throw new IncomeNotFoundException("Income Not Found");
        incomes.delete(income);
        return "Income deleted Successfully";
    }
    public static User map(RegisterRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setUsername(request.getUsername());
        user.setLastName(request.getLastName());
        return user;
    }
    public static CreateUserResponse map(User user){
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setId(user.getId());
        createUserResponse.setUsername(user.getUsername());
        return createUserResponse;
    }
    public static AddIncomeResponse map(Income income){
        AddIncomeResponse addIncomeResponse = new AddIncomeResponse();
        addIncomeResponse.setIncome(income.getIncome());
        addIncomeResponse.setUsername(income.getUsername());
        addIncomeResponse.setId(income.getId());
        addIncomeResponse.setDate(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(income.getAddIncomeDay()));
        return addIncomeResponse;
    }
}