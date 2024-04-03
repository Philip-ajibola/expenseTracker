package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.data.repository.Incomes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeServiceImpl implements IncomeService{
    @Autowired
    private Incomes incomes;
    @Override
    public void addIncome(Income income) {
        incomes.save(income);
    }
}
