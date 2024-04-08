package org.africaSemicolon.exception;

public class NoExpenseExistException extends ExpenseTrackerException {
    public NoExpenseExistException(String message) {
        super(message);
    }
}
