package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Expense;
import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.data.model.User;
import org.africaSemicolon.data.repository.Users;
import org.africaSemicolon.dto.request.*;
import org.africaSemicolon.dto.response.AddExpenseResponse;
import org.africaSemicolon.dto.response.AddIncomeResponse;
import org.africaSemicolon.dto.response.CreateUserResponse;
import org.africaSemicolon.exception.IncomeNotFoundException;
import org.africaSemicolon.exception.UserNotFoundException;
import org.africaSemicolon.exception.UserNotLoggedInException;
import org.africaSemicolon.exception.UsernameALreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.africaSemicolon.utilities.Mapper.map;

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
        User user = users.findByUsername(expenseRequest.getExpenseOwnerName());
        validateUser(user);
        validateLogin(user);
        user.setBalance(user.getBalance().subtract(BigDecimal.valueOf(Double.parseDouble(expenseRequest.getAmount()))));
        AddExpenseResponse addExpenseResponse = expenseService.addExpense(expenseRequest);
        Expense expense = expenseService.findByTitleAndUsername(expenseRequest.getExpenseTitle(),expenseRequest.getExpenseOwnerName());
        List<Expense> expenseList = user.getExpenseList();
        expenseList.add(expense);
        user.setExpenseList(expenseList);
        users.save(user);
        return addExpenseResponse;
    }

    @Override
    public CreateUserResponse registerUser(RegisterRequest request) {
        checkIfUserExist(request.getUsername());
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
        User user = users.findByUsername(incomeRequest.getUsername());
        validateUser(user);
        validateLogin(user);
        AddIncomeResponse addIncomeResponse = incomeService.addIncome(incomeRequest);
        user.setBalance(user.getBalance().add(BigDecimal.valueOf(Double.parseDouble(incomeRequest.getIncome()))));
        Income income = incomeService.findBy(incomeRequest.getUsername(),incomeRequest.getIncomeTitle());
        if(income == null) throw new IncomeNotFoundException("Income Not Found");
        List<Income> incomes = user.getIncome();
        incomes.add(income);
        user.setIncome(incomes);
        users.save(user);
            return addIncomeResponse;
    }

    @Override
    public void deleteExpense(DeleteExpenseRequest deleteExpenseRequest) {
        User user = users.findByUsername(deleteExpenseRequest.getUsername());
        validateUser(user);
        validateLogin(user);
        Expense expense  = expenseService.findByTitleAndUsername(deleteExpenseRequest.getExpenseTitle(), deleteExpenseRequest.getUsername());
        user.setBalance(user.getBalance().add(expense.getAmount()));
        System.out.println(user.getBalance());
        expenseService.deleteExpense(deleteExpenseRequest);
        List<Expense> expenseList = user.getExpenseList();
        expenseList.remove(expense);
        users.save(user);
    }

    @Override
    public void deleteIncome(DeleteIncomeRequest deleteIncomeRequest) {
            User user = users.findByUsername(deleteIncomeRequest.getUsername());
            validateUser(user);
            validateLogin(user);
            Income income = incomeService.findBy(deleteIncomeRequest.getUsername(),deleteIncomeRequest.getIncomeTitle());
            incomeService.deleteIncome(deleteIncomeRequest);
            if(income == null) throw new IncomeNotFoundException("Income Not Found");
            user.setBalance(user.getBalance().subtract(income.getIncome()));
            List<Income> incomes = user.getIncome();
            incomes.remove(income);
            user.setIncome(incomes);
            users.save(user);
    }

    private static void validateLogin(User user) {
        if(!user.isLoggedIn())throw new UserNotLoggedInException( "User Not LoggedIn");
    }

    @Override
    public String login(LoginRequest loginRequest) {
        User  user= users.findByUsername(loginRequest.getUsername());
        user.setLoggedIn(true);
        users.save(user);
        return "Login Successful";
    }

    private static void validateUser(User user) {
        if(user == null) throw new UserNotFoundException("User Not Found");
    }


}
