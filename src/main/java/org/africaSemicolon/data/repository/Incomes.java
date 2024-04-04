package org.africaSemicolon.data.repository;

import org.africaSemicolon.data.model.Income;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Incomes extends MongoRepository<Income,String> {
    Income findByUsernameAndIncomeTitle(String username, String incomeTitle);
}
