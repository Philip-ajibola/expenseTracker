package org.africaSemicolon.exception;

import org.springframework.data.annotation.Id;

public class InvalidAmountException extends ExpenseTrackerException{
    public InvalidAmountException(String message){
        super(message);
    }
}
