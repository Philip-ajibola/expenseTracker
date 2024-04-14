package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.data.repository.Incomes;
import org.africaSemicolon.dto.request.DeleteIncomeRequest;
import org.africaSemicolon.dto.request.AddIncomeRequest;
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
        AddIncomeRequest addIncomeRequest = new AddIncomeRequest();
        addIncomeRequest.setIncome("600000");
        addIncomeRequest.setUsername("username");
        income.setIncomeTitle("title");
        income.setIncome(BigDecimal.valueOf(Double.parseDouble(addIncomeRequest.getIncome())));
        incomeService.addIncome(addIncomeRequest);
        assertEquals(1,incomes.count());
    }
    @Test
    public void testThatIncomeCanBeDeleted(){
        Income income = new Income();
        AddIncomeRequest addIncomeRequest = new AddIncomeRequest();
        addIncomeRequest.setIncome("600000");
        addIncomeRequest.setIncomeTitle("title");
        addIncomeRequest.setUsername("username");

        income.setIncome(BigDecimal.valueOf(Double.parseDouble(addIncomeRequest.getIncome())));
        income.setUsername("username");
        income.setIncomeTitle("title");
        incomeService.addIncome(addIncomeRequest);
        DeleteIncomeRequest deleteIncomeRequest = new DeleteIncomeRequest();
        deleteIncomeRequest.setIncomeTitle("title");
        deleteIncomeRequest.setUsername("username");
        incomeService.deleteIncome(deleteIncomeRequest);
        assertEquals(0,incomes.count());
    }
    @Test
    public void testThatWhenUserNameIsNotSameErrorIsThrown(){
        Income income = new Income();
        AddIncomeRequest addIncomeRequest = new AddIncomeRequest();
        addIncomeRequest.setIncome("600000");
        addIncomeRequest.setIncomeTitle("title");
        addIncomeRequest.setUsername("username1");
        income.setIncome(BigDecimal.valueOf(Double.parseDouble(addIncomeRequest.getIncome())));
        income.setUsername("username");
        income.setIncomeTitle("title");
        incomeService.addIncome(addIncomeRequest);
        DeleteIncomeRequest deleteIncomeRequest = new DeleteIncomeRequest();
        deleteIncomeRequest.setIncomeTitle("title");
        deleteIncomeRequest.setUsername("username");
        assertThrows(IncomeNotFoundException.class,()->incomeService.deleteIncome(deleteIncomeRequest));
        assertEquals(1,incomes.count());

    }
}