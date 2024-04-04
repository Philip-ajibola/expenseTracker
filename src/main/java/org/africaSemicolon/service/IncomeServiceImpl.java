package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.data.repository.Incomes;
import org.africaSemicolon.dto.request.DeleteIncomeRequest;
import org.africaSemicolon.dto.request.IncomeRequest;
import org.africaSemicolon.dto.response.AddIncomeResponse;
import org.africaSemicolon.exception.IncomeNotFoundException;
import org.africaSemicolon.exception.IncomeTitleExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.africaSemicolon.utilities.Mapper.map;

@Service
public class IncomeServiceImpl implements IncomeService{
    @Autowired
    private Incomes incomes;

    @Override
    public Income findBy(String username ,String incomeTitle) {
        Income income = incomes.findByUsernameAndIncomeTitle(username,incomeTitle);
        if(income== null)throw new IncomeNotFoundException("Income Not Found");
        return income;
    }

    @Override
    public AddIncomeResponse addIncome(IncomeRequest incomeRequest){
        validateIncome(incomeRequest);
        Income income = incomes.save(map(incomeRequest));
        return map(income);
    }

    private void validateIncome(IncomeRequest incomeRequest) {
        for(Income income : incomes.findAll()){
            if(income.getIncomeTitle() != null && income.getIncomeTitle().equals(incomeRequest.getIncomeTitle()) && income.getUsername().equals(incomeRequest.getUsername()) )throw new IncomeTitleExistException("Income Exist Already ");
        }
    }

    @Override
    public String deleteIncome(DeleteIncomeRequest deleteIncomeRequest) {
      return  map(deleteIncomeRequest,incomes);
    }





}


