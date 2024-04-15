package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Category;
import org.africaSemicolon.data.model.User;
import org.africaSemicolon.data.repository.Expenses;
import org.africaSemicolon.data.repository.Incomes;
import org.africaSemicolon.data.repository.Users;
import org.africaSemicolon.dto.request.*;
import org.africaSemicolon.exception.InValidUserNameException;
import org.africaSemicolon.exception.InvalidAmountException;
import org.africaSemicolon.exception.InvalidPasswordException;
import org.africaSemicolon.exception.UserNotLoggedInException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    private RegisterRequest request;
    @Autowired
    private Incomes incomes;
    @Autowired
    private Users users;
    @Autowired
    private Expenses expenses;
    private AddIncomeRequest addIncomeRequest;
    private  ExpenseRequest expenseRequest;
    @BeforeEach
    public void Initializer(){
        incomes.deleteAll();
        expenses.deleteAll();
        users.deleteAll();
        request = new RegisterRequest();
        request.setUsername("username");
        request.setFirstName("firstname");
        request.setLastName("lastName");
        request.setPassword("password");

         addIncomeRequest = new AddIncomeRequest();
        addIncomeRequest.setIncome("600000");
        addIncomeRequest.setIncomeTitle("title");
       addIncomeRequest.setUsername("username");

        expenses.deleteAll();
        expenseRequest = new ExpenseRequest();
        expenseRequest.setAmount("2000");
        expenseRequest.setCategory(Category.CLOTHES);
        expenseRequest.setExpenseOwnerName("username");
        expenseRequest.setExpenseTitle("title");
    }
    @Test
    public void testThatUserCanBeCreated(){
        userService.registerUser(request);
        assertEquals(1,users.count());
    }
    @Test
    public void testThatWhenUsernameIsEmptyUserCantBeRegistered(){
        request.setUsername("");
        assertThrows(InValidUserNameException.class,()->userService.registerUser(request));
    }
    @Test
    public void testThatWhenPasswordIsEmptyUserCantBeRegistered(){
        request.setPassword("");
        assertThrows(InvalidPasswordException.class,()->userService.registerUser(request));
    }
    @Test
    public void testThatUserCanLogin(){
        userService.registerUser(request);
        User user = users.findByUsername(request.getUsername());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(user.getPassword());
        loginRequest.setUsername(user.getUsername());
        userService.login(loginRequest);
        user = users.findByUsername(request.getUsername());
        assertTrue(user.isLoggedIn());

    }
    @Test
    public void testThatUserCanAddIncome(){
        userService.registerUser(request);
        User user = users.findByUsername(request.getUsername());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(user.getPassword());
        loginRequest.setUsername(user.getUsername());
        userService.login(loginRequest);
        addIncomeRequest.setUsername(user.getUsername());
        userService.addIncome(addIncomeRequest);
        assertEquals(1,incomes.count());
        user = users.findByUsername(request.getUsername());
        assertEquals(1,user.getIncome().size());
    }
    @Test
    public void testThatUserCanAddExpenses(){
        userService.registerUser(request);
        User user = users.findByUsername(request.getUsername());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(user.getPassword());
        loginRequest.setUsername(user.getUsername());
        userService.login(loginRequest);
        expenseRequest.setExpenseOwnerName(user.getUsername());
        userService.addExpense(expenseRequest);
        assertEquals(1,expenses.count());
    }
    @Test
    public void testThatWhenExpenseIsAddedIncomeAmountReduce(){
        userService.registerUser(request);
        User user = users.findByUsername(request.getUsername());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(user.getPassword());
        loginRequest.setUsername(user.getUsername());
        userService.login(loginRequest);
        addIncomeRequest.setUsername(user.getUsername());
        userService.addIncome(addIncomeRequest);
        expenseRequest.setExpenseOwnerName(user.getUsername());
        userService.addExpense(expenseRequest);
        user =  users.findByUsername(request.getUsername());
        assertEquals(1,incomes.count());
        assertEquals(BigDecimal.valueOf(598_000.00).setScale(2, RoundingMode.HALF_UP),user.getBalance());
    }
    @Test
    public void testThatWhenExpenseIsDeletedTheBalanceChangeStateToFormalState(){
        userService.registerUser(request);
        User user = users.findByUsername(request.getUsername());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(user.getPassword());
        loginRequest.setUsername(user.getUsername());
        userService.login(loginRequest);
        addIncomeRequest.setUsername(user.getUsername());
        userService.addIncome(addIncomeRequest);
        expenseRequest.setExpenseOwnerName(user.getUsername());
        userService.addExpense(expenseRequest);
        DeleteExpenseRequest deleteExpenseRequest = new DeleteExpenseRequest();
        deleteExpenseRequest.setUsername(expenseRequest.getExpenseOwnerName());
        deleteExpenseRequest.setExpenseTitle(expenseRequest.getExpenseTitle());
        userService.deleteExpense(deleteExpenseRequest);

        user =  users.findByUsername(request.getUsername());
        assertEquals(1,incomes.count());
        assertEquals(BigDecimal.valueOf(600_000.00).setScale(2, RoundingMode.HALF_UP),user.getBalance());
    }
    @Test
    public void testThatIncomeCanBeDeleted(){
        userService.registerUser(request);
        User user = users.findByUsername(request.getUsername());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(user.getPassword());
        loginRequest.setUsername(user.getUsername());
        userService.login(loginRequest);
        addIncomeRequest.setUsername(user.getUsername());
        userService.addIncome(addIncomeRequest);
        expenseRequest.setExpenseOwnerName(user.getUsername());
        userService.addExpense(expenseRequest);
        DeleteExpenseRequest deleteExpenseRequest = new DeleteExpenseRequest();
        deleteExpenseRequest.setUsername("username");
        deleteExpenseRequest.setExpenseTitle(expenseRequest.getExpenseTitle());
        userService.deleteExpense(deleteExpenseRequest);
        DeleteIncomeRequest deleteIncomeRequest = new DeleteIncomeRequest();
        deleteIncomeRequest.setUsername("username");
        deleteIncomeRequest.setIncomeTitle(addIncomeRequest.getIncomeTitle());
        userService.deleteIncome(deleteIncomeRequest);

        user =  users.findByUsername(request.getUsername());
        assertEquals(0,incomes.count());
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP),user.getBalance());
    }
    @Test
    public void testThatMultipleIncomeCanBeAdded(){
        userService.registerUser(request);
        User user = users.findByUsername(request.getUsername());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(user.getPassword());
        loginRequest.setUsername(user.getUsername());
        userService.login(loginRequest);
        addIncomeRequest.setUsername(user.getUsername());
        userService.addIncome(addIncomeRequest);
        expenseRequest.setExpenseOwnerName(user.getUsername());
        userService.addExpense(expenseRequest);
        ExpenseRequest expenseRequest1 = new ExpenseRequest();
        expenseRequest1.setExpenseOwnerName("username");
        expenseRequest1.setCategory(Category.KIDS);
        expenseRequest1.setExpenseTitle("title1");
        expenseRequest1.setAmount("50000");
        userService.addExpense(expenseRequest1);
        user =  users.findByUsername(request.getUsername());
        assertEquals(1,incomes.count());
        assertEquals(BigDecimal.valueOf(548_000.00).setScale(2, RoundingMode.HALF_UP),user.getBalance());
    }
    @Test
    public void testThatWhenEntityIsDeletedBalanceGoBackToThePreviousState(){
        userService.registerUser(request);
        User user = users.findByUsername(request.getUsername());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(user.getPassword());
        loginRequest.setUsername(user.getUsername());
        userService.login(loginRequest);
        addIncomeRequest.setUsername(user.getUsername());
        userService.addIncome(addIncomeRequest);
        expenseRequest.setExpenseOwnerName(user.getUsername());
        userService.addExpense(expenseRequest);
        ExpenseRequest expenseRequest1 = new ExpenseRequest();
        expenseRequest1.setExpenseOwnerName("username");
        expenseRequest1.setCategory(Category.KIDS);
        expenseRequest1.setExpenseTitle("title1");
        expenseRequest1.setAmount("50000");
        DeleteExpenseRequest deleteExpenseRequest = new DeleteExpenseRequest();
        deleteExpenseRequest.setExpenseTitle("title1");
        deleteExpenseRequest.setUsername("username");
        userService.addExpense(expenseRequest1);
        userService.deleteExpense(deleteExpenseRequest);
        user =  users.findByUsername(request.getUsername());
        assertEquals(1,incomes.count());
        assertEquals(BigDecimal.valueOf(598_000.00).setScale(2, RoundingMode.HALF_UP),user.getBalance());
    }
    @Test
    public void testThatWhenInputIsInvalid_ExceptionIsThrown(){
        userService.registerUser(request);
        User user = users.findByUsername(request.getUsername());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(user.getPassword());
        loginRequest.setUsername(user.getUsername());
        userService.login(loginRequest);
        addIncomeRequest.setUsername(user.getUsername());
        addIncomeRequest.setIncome("rt6gh");
        assertThrows(InvalidAmountException.class,()-> userService.addIncome(addIncomeRequest));
        assertEquals(0,incomes.count());
        user = users.findByUsername(request.getUsername());
        assertEquals(0,user.getIncome().size());
    }
    @Test
    public void testThatWhenUserIsNotLoggedInUserCantPerformAnyAction(){
        userService.registerUser(request);
        User user = users.findByUsername(request.getUsername());
        assertThrows(UserNotLoggedInException.class,()->userService.addExpense(expenseRequest));
    }
    @Test
    public void testThatUserCanLogout(){
        userService.registerUser(request);
        User user = users.findByUsername(request.getUsername());
        UserLogoutRequest logoutRequest = new UserLogoutRequest();
        logoutRequest.setPassword(user.getPassword());
        logoutRequest.setUsername(user.getUsername());
        assertFalse(user.isLoggedIn());
    }



}