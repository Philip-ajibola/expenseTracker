package org.africaSemicolon.exception;

public class UserNotLoggedInException extends ExpenseTrackerException{
    public UserNotLoggedInException(String message){
        super(message);
    }
}
