package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.data.repository.Incomes;
import org.africaSemicolon.dto.request.DeleteIncomeRequest;
import org.africaSemicolon.dto.request.AddIncomeRequest;
import org.africaSemicolon.exception.IncomeNotFoundException;
import org.africaSemicolon.exception.IncomeTitleExistException;
import org.africaSemicolon.exception.UserNotFoundException;
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
    public Income addIncome(AddIncomeRequest addIncomeRequest){
        validateIncome(addIncomeRequest);
        return incomes.save(map(addIncomeRequest));

    }

    private void validateIncome(AddIncomeRequest addIncomeRequest) {
        for(Income income : incomes.findAll()){
            if(income != null) {
                if (income.getUsername().equals(addIncomeRequest.getUsername())) {
                    if (normalizeString(income.getIncomeTitle()).equals(normalizeString(addIncomeRequest.getIncomeTitle())))
                        throw new IncomeTitleExistException("Income title already exists");
                }
            }
        }
    }
    private static String normalizeString(String str) {
        return str.replaceAll("\\s+", "");
    }

    @Override
    public String deleteIncome(DeleteIncomeRequest deleteIncomeRequest) {
      return  map(deleteIncomeRequest,incomes);
    }

    @Override
    public String deleteAllIncomeOf(String username) {
        incomes.findAll().forEach(income -> {if( income.getUsername().equals(username.toLowerCase())) incomes.delete(income);});
        return String.format("%s Incomes Deleted Successfully",username);
    }


}


