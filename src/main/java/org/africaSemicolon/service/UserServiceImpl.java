package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Category;
import org.africaSemicolon.data.model.Expense;
import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.data.model.User;
import org.africaSemicolon.data.repository.Users;
import org.africaSemicolon.dto.request.*;
import org.africaSemicolon.dto.response.AddExpenseResponse;
import org.africaSemicolon.dto.response.AddIncomeResponse;
import org.africaSemicolon.dto.response.CreateUserResponse;
import org.africaSemicolon.dto.response.SpendingResponse;
import org.africaSemicolon.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.africaSemicolon.utilities.Mapper.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private Users users;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenseService expenseService;
    @Override
    public AddExpenseResponse addExpense(ExpenseRequest expenseRequest) {
        validateMoney(expenseRequest.getAmount());
        User user = findByUsername(expenseRequest.getExpenseOwnerName().toLowerCase());
        validateLogin(user);
        Expense expense = expenseService.addExpense(expenseRequest);
        user.setBalance(user.getBalance().subtract(BigDecimal.valueOf(Double.parseDouble(expenseRequest.getAmount()))).setScale(2, RoundingMode.HALF_UP));
        user.getExpenseList().add(expense);
        users.save(user);
        return map(expense);
    }

    private User findByUsername(String username) {
        User user = users.findByUsername(username);
        validateUser(user);
        return user;
    }


    @Override
    public CreateUserResponse registerUser(RegisterRequest request) {
        validateRequest(request);
        checkIfUserExist(request.getUsername().toLowerCase());
        User user = users.save(map(request));
        return map(user);
    }

    private void checkIfUserExist(String username) {
        boolean existByUsername = users.existsByUsername(username);
        if(existByUsername)throw new UserNotFoundException("User Not Found");
    }

    @Override
    public AddIncomeResponse addIncome(IncomeRequest incomeRequest) {
        validateMoney(incomeRequest.getIncome());
        User user = findByUsername(incomeRequest.getUsername().toLowerCase());
        validateLogin(user);
        Income income = incomeService.addIncome(incomeRequest);
        user.setBalance(user.getBalance().add(BigDecimal.valueOf(Double.parseDouble(incomeRequest.getIncome()))).setScale(2, RoundingMode.HALF_UP));
        user.getIncome().add(income);
        users.save(user);
        return map(income);
    }


    private void validateMoney(String amount){
        if (amount.isEmpty() || !amount.matches("\\d+(\\.\\d+)?"))throw new InvalidAmountException("Invalid Amount Provivde");
    }

    @Override
    public String deleteExpense(DeleteExpenseRequest deleteExpenseRequest) {
        User user = findByUsername(deleteExpenseRequest.getUsername().toLowerCase());
        validateLogin(user);
        Expense expense  = expenseService.findByTitleAndUsername(deleteExpenseRequest.getExpenseTitle(), deleteExpenseRequest.getUsername());
        user.setBalance(user.getBalance().add(expense.getAmount()).setScale(2, RoundingMode.HALF_UP));
        user.getExpenseList().remove(expense);
        users.save(user);
        return expenseService.deleteExpense(deleteExpenseRequest);

    }

    @Override
    public String deleteIncome(DeleteIncomeRequest deleteIncomeRequest) {
            User user = findByUsername(deleteIncomeRequest.getUsername().toLowerCase());
            validateLogin(user);
            Income income = incomeService.findBy(deleteIncomeRequest.getUsername(),deleteIncomeRequest.getIncomeTitle());
            user.setBalance(user.getBalance().subtract(income.getIncome()).setScale(2, RoundingMode.HALF_UP));
            user.getIncome().remove(income);
            users.save(user);
            return incomeService.deleteIncome(deleteIncomeRequest);
    }

    private static void validateLogin(User user) {
        if(!user.isLoggedIn())throw new UserNotLoggedInException( "User Not LoggedIn");
    }

    @Override
    public String login(LoginRequest loginRequest) {
        User  user= findByUsername(loginRequest.getUsername().toLowerCase());
        validateUser(user);
        if(!user.getPassword().equals(loginRequest.getPassword()))throw new InvalidPasswordException("Wrong Password \n Provide A Valid ");
        user.setLoggedIn(true);
        users.save(user);
        return "Login Successful";
    }


    @Override
    public SpendingResponse showSpendings(SpendingRequest spendingRequest) {
        User user = findByUsername(spendingRequest.getUsername().toLowerCase());
        validateUser(user);
        validateLogin(user);
        return spendingMap(user);
    }

    @Override
    public List<Income> getAllIncome(String username) {
        User user = users.findByUsername(username.toLowerCase());
        validateUser(user);
        return user.getIncome();
    }

    @Override
    public List<Expense> findAllExpenses(String username) {
        User user = users.findByUsername(username);
        validateUser(user);
        return user.getExpenseList();
    }

    @Override
    public String deleteUser(String username) {
        User user = findByUsername(username.toLowerCase());
        validateLogin(user);
        expenseService.deleteAllExpenseOf(username);
        incomeService.deleteAllIncomeOf(username);
        users.delete(user);
        return "User Deleted Successful";
    }

    @Override
    public String logout(LogoutRequest logoutRequest) {
        User user = findByUsername(logoutRequest.getUsername().toLowerCase());
        if(!user.getPassword().equals(logoutRequest.getPassword()))throw new InvalidPasswordException("Wrong Password \n Provide A Valid ");
        user.setLoggedIn(false);
        users.save(user);
        return "Logout Successful";
    }


    private static void validateUser(User user) {
        if(user == null) throw new UserNotFoundException("User Not Found");
    }



}
