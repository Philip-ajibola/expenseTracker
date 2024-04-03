package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.data.repository.Incomes;
import org.africaSemicolon.dto.IncomeRequest;
import org.junit.jupiter.api.AfterAll;
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
    @Test
    public void testThatIncomeCanBeAdded(){
        Income income = new Income();
        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setIncome(BigDecimal.valueOf(600000));
        income.setIncome(incomeRequest.getIncome());
        incomeService.addIncome(income);
        assertEquals(1,incomes.count());
    }
}