package org.africaSemicolon.service;

import org.africaSemicolon.data.model.Income;
import org.africaSemicolon.dto.request.DeleteIncomeRequest;
import org.africaSemicolon.dto.request.IncomeRequest;
import org.africaSemicolon.dto.response.AddIncomeResponse;

public interface IncomeService {
     Income findBy(String username, String incomeTitle) ;


    AddIncomeResponse addIncome(IncomeRequest incomeRequest);

    String deleteIncome(DeleteIncomeRequest deleteIncomeRequest);


    String deleteAllIncomeOf(String username);
}

