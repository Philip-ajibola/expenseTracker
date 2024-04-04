package org.africaSemicolon.exception;

public class UserNotFoundException extends ExpenseTrackerException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
