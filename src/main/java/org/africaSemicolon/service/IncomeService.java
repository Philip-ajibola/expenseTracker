package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.dto.request.DeleteIncomeRequest;
import org.africaSemicolon.dto.request.AddIncomeRequest;

public interface IncomeService {
     Income findBy(String username, String incomeTitle) ;


    Income addIncome(AddIncomeRequest addIncomeRequest);

    String deleteIncome(DeleteIncomeRequest deleteIncomeRequest);


    String deleteAllIncomeOf(String username);
}

