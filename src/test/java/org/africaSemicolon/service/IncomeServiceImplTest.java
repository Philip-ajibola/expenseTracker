package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.data.repository.Incomes;
import org.africaSemicolon.dto.request.DeleteIncomeRequest;
import org.africaSemicolon.dto.request.IncomeRequest;
import org.africaSemicolon.exception.IncomeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class IncomeServiceImplTest {
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private Incomes incomes;
    @BeforeEach
    public void deleteAll(){
        incomes.deleteAll();
    }
    @Test
    public void testThatIncomeCanBeAdded(){
        Income income = new Income();
        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setIncome("600000");
        income.setIncome(BigDecimal.valueOf(Double.parseDouble(incomeRequest.getIncome())));
        incomeService.addIncome(incomeRequest);
        assertEquals(1,incomes.count());
    }
    @Test
    public void testThatIncomeCanBeDeleted(){
        Income income = new Income();
        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setIncome("600000");
        incomeRequest.setIncomeTitle("title");
        incomeRequest.setUsername("username");

        income.setIncome(BigDecimal.valueOf(Double.parseDouble(incomeRequest.getIncome())));
        income.setUsername("username");
        income.setIncomeTitle("title");
        incomeService.addIncome(incomeRequest);
        DeleteIncomeRequest deleteIncomeRequest = new DeleteIncomeRequest();
        deleteIncomeRequest.setIncomeTitle("title");
        deleteIncomeRequest.setUsername("username");
        incomeService.deleteIncome(deleteIncomeRequest);
        assertEquals(0,incomes.count());
    }
    @Test
    public void testThatWhenUserNameIsNotSameErrorIsThrown(){
        Income income = new Income();
        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setIncome("600000");
        incomeRequest.setIncomeTitle("title");
        incomeRequest.setUsername("username1");
        income.setIncome(BigDecimal.valueOf(Double.parseDouble(incomeRequest.getIncome())));
        income.setUsername("username");
        income.setIncomeTitle("title");
        incomeService.addIncome(incomeRequest);
        DeleteIncomeRequest deleteIncomeRequest = new DeleteIncomeRequest();
        deleteIncomeRequest.setIncomeTitle("title");
        deleteIncomeRequest.setUsername("username");
        assertThrows(IncomeNotFoundException.class,()->incomeService.deleteIncome(deleteIncomeRequest));
        assertEquals(1,incomes.count());

    }
}