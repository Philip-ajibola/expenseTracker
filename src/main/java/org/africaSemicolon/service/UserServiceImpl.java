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
        validateUserName(expenseRequest.getExpenseOwnerName());
        validateMoney(expenseRequest.getAmount());
        User user = users.findByUsername(expenseRequest.getExpenseOwnerName().toLowerCase());
        validateUser(user);
        validateLogin(user);
        user.setBalance(user.getBalance().subtract(BigDecimal.valueOf(Double.parseDouble(expenseRequest.getAmount()))).setScale(2, RoundingMode.HALF_UP));
        AddExpenseResponse addExpenseResponse = expenseService.addExpense(expenseRequest);
        Expense expense = expenseService.findByTitleAndUsername(expenseRequest.getExpenseTitle(),expenseRequest.getExpenseOwnerName().toLowerCase());
        List<Expense> expenseList = user.getExpenseList();
        expenseList.add(expense);
        user.setExpenseList(expenseList);
        users.save(user);
        return addExpenseResponse;
    }


    @Override
    public CreateUserResponse registerUser(RegisterRequest request) {
        validateRequest(request);
        checkIfUserExist(request.getUsername().toLowerCase());
        User user = users.save(map(request));
        return map(user);
    }

    private void checkIfUserExist(String username) {
        for(User user : users.findAll()){
            if(username.equals(user.getUsername()))throw new UsernameALreadyExistException("User Name Already Exist");
        }
    }

    @Override
    public AddIncomeResponse addIncome(IncomeRequest incomeRequest) {
        validateUserName(incomeRequest.getUsername());
        validateMoney(incomeRequest.getIncome());
        User user = users.findByUsername(incomeRequest.getUsername().toLowerCase());
        validateUser(user);
        validateLogin(user);
        AddIncomeResponse addIncomeResponse = incomeService.addIncome(incomeRequest);
        user.setBalance(user.getBalance().add(BigDecimal.valueOf(Double.parseDouble(incomeRequest.getIncome()))).setScale(2, RoundingMode.HALF_UP));
        Income income = incomeService.findBy(incomeRequest.getUsername().toLowerCase(),incomeRequest.getIncomeTitle());
        if(income == null) throw new IncomeNotFoundException("Income Not Found");
        List<Income> incomes = user.getIncome();
        incomes.add(income);
        user.setIncome(incomes);
        users.save(user);
            return addIncomeResponse;
    }
    private void validateMoney(String amount){
        if (amount.isEmpty() || !amount.matches("\\d+(\\.\\d+)?"))throw new InvalidAmountException("Invalid Amount Provivde");
    }
    private void validateUserName(String username){
        if(username.isEmpty())throw new InValidUserNameException("Please Provide A username");
    }


    @Override
    public String deleteExpense(DeleteExpenseRequest deleteExpenseRequest) {
        validateUserName(deleteExpenseRequest.getUsername());
        User user = users.findByUsername(deleteExpenseRequest.getUsername().toLowerCase());
        validateUser(user);
        validateLogin(user);
        Expense expense  = expenseService.findByTitleAndUsername(deleteExpenseRequest.getExpenseTitle(), deleteExpenseRequest.getUsername());
        user.setBalance(user.getBalance().add(expense.getAmount()).setScale(2, RoundingMode.HALF_UP));
        List<Expense> expenseList = user.getExpenseList();
        expenseList.remove(expense);
        users.save(user);
        return expenseService.deleteExpense(deleteExpenseRequest);

    }

    @Override
    public String deleteIncome(DeleteIncomeRequest deleteIncomeRequest) {
        validateUserName(deleteIncomeRequest.getUsername());
            User user = users.findByUsername(deleteIncomeRequest.getUsername().toLowerCase());
            validateUser(user);
            validateLogin(user);
            Income income = incomeService.findBy(deleteIncomeRequest.getUsername(),deleteIncomeRequest.getIncomeTitle());
            if(income == null) throw new IncomeNotFoundException("Income Not Found");
            user.setBalance(user.getBalance().subtract(income.getIncome()).setScale(2, RoundingMode.HALF_UP));
            List<Income> incomes = user.getIncome();
            incomes.remove(income);
            user.setIncome(incomes);
            users.save(user);
            return incomeService.deleteIncome(deleteIncomeRequest);
    }

    private static void validateLogin(User user) {
        if(!user.isLoggedIn())throw new UserNotLoggedInException( "User Not LoggedIn");
    }

    @Override
    public String login(LoginRequest loginRequest) {
        validateUserName(loginRequest.getUsername());
        validatePassword(loginRequest.getPassword());
        User  user= users.findByUsername(loginRequest.getUsername().toLowerCase());
        validateUser(user);
        if(!user.getPassword().equals(loginRequest.getPassword()))throw new InvalidPasswordException("Wrong Password \n Provide A Valid ");
        user.setLoggedIn(true);
        users.save(user);
        return "Login Successful";
    }
    private void validatePassword(String password){
        if(password.isEmpty())throw new InvalidPasswordException("Provide  A  Password");
    }

    @Override
    public SpendingResponse showSpendings(SpendingRequest spendingRequest) {
        validateUserName(spendingRequest.getUsername());
        User user = users.findByUsername(spendingRequest.getUsername().toLowerCase());
        validateUser(user);
        validateLogin(user);
        return spendingMap(user);
    }

    @Override
    public List<Income> getAllIncome(String username) {
        validateUserName(username);
        User user = users.findByUsername(username.toLowerCase());
        validateUser(user);
        return user.getIncome();
    }

    @Override
    public List<Expense> findAllExpenses(String username) {
        validateUserName(username);
        User user = users.findByUsername(username);
        validateUser(user);
        return user.getExpenseList();
    }

    @Override
    public String deleteUser(String username) {
        validateUserName(username);
        username = username.toLowerCase();
        User user = users.findByUsername(username);
        validateUser(user);
        validateLogin(user);
        expenseService.deleteAllExpenseOf(username);
        incomeService.deleteAllIncomeOf(username);
        users.delete(user);
        return "User Deleted Successful";
    }

    @Override
    public String logout(LogoutRequest logoutRequest) {
        validateUserName(logoutRequest.getUsername());
        validatePassword(logoutRequest.getPassword());
        User user = users.findByUsername(logoutRequest.getUsername().toLowerCase());
        validateUser(user);
        if(!user.getPassword().equals(logoutRequest.getPassword()))throw new InvalidPasswordException("Wrong Password \n Provide A Valid ");
        user.setLoggedIn(false);
        users.save(user);
        return "Logout Successful";
    }


    private static void validateUser(User user) {
        if(user == null) throw new UserNotFoundException("User Not Found");
    }



}
