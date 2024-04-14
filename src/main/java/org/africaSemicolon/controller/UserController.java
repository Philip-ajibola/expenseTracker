package org.africaSemicolon.controller;

import org.africaSemicolon.dto.request.*;
import org.africaSemicolon.dto.response.ApiResponse;
import org.africaSemicolon.exception.ExpenseTrackerException;
import org.africaSemicolon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register_user")
    public ResponseEntity<?>  registerUser(@RequestBody RegisterRequest request){
        try{
            var result = userService.registerUser(request);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch(ExpenseTrackerException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            var result  = userService.login(loginRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch (ExpenseTrackerException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @PostMapping("/add_income")
    public ResponseEntity<?> addIncome(@RequestBody AddIncomeRequest addIncomeRequest){
        try{
            var result  = userService.addIncome(addIncomeRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch (ExpenseTrackerException | InputMismatchException e) {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @PostMapping("/add_expense")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequest expenseRequest){
        try{
            var result  = userService.addExpense(expenseRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch (ExpenseTrackerException | InputMismatchException e) {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @GetMapping("/spendings")
    public ResponseEntity<?> showSpendings(@RequestBody SpendingRequest spendingRequest){
        try{
            var result  = userService.showSpendings(spendingRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch (ExpenseTrackerException e) {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @GetMapping("/get_incomes/{username}")
    public ResponseEntity<?> getAllIncomes(@PathVariable("username") String username){
        try{
            var result  = userService.getAllIncome(username);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch (ExpenseTrackerException e) {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete_income")
    public ResponseEntity<?> deleteIncome(@RequestBody DeleteIncomeRequest deleteIncomeRequest){
        try{
            var result  = userService.deleteIncome(deleteIncomeRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch (ExpenseTrackerException e) {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete_expense")
    public ResponseEntity<?> deleteExpense(@RequestBody DeleteExpenseRequest deleteExpenseRequest){
        try{
            var result  = userService.deleteExpense(deleteExpenseRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch (ExpenseTrackerException e) {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @GetMapping("/all_expenses/{username}")
    public ResponseEntity<?> findAllExpenses(@PathVariable("username") String username){
        try{
            var result  = userService.findAllExpenses(username);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch (ExpenseTrackerException e) {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @DeleteMapping("delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username){
        try{
            var result  = userService.deleteUser(username);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch (ExpenseTrackerException e) {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
    @PostMapping("/log_out")
    public ResponseEntity<?> logout(LogoutRequest logoutRequest){
        try{
            var result  = userService.logout(logoutRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch (ExpenseTrackerException e) {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }

}
