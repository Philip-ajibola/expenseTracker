package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.data.repository.Incomes;
import org.africaSemicolon.dto.request.DeleteIncomeRequest;
import org.africaSemicolon.dto.request.IncomeRequest;
import org.africaSemicolon.dto.response.AddIncomeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.africaSemicolon.utilities.Mapper.map;

@Service
public class IncomeServiceImpl implements IncomeService{
    @Autowired
    private Incomes incomes;

    @Override
    public Income findBy(String username ,String incomeTitle) {
        return incomes.findByUsernameAndIncomeTitle(username,incomeTitle);
    }

    @Override
    public AddIncomeResponse addIncome(IncomeRequest incomeRequest){
        Income income = incomes.save(map(incomeRequest));
        return map(income);
    }

    @Override
    public String deleteIncome(DeleteIncomeRequest deleteIncomeRequest) {
      return  map(deleteIncomeRequest,incomes);
    }





}


