package com.ProyectoIntegradorJava.interfaces;

import com.ProyectoIntegradorJava.exceptions.InvalidExpenseException;

@FunctionalInterface
public interface ExpenseAmountValidator {
    boolean notvalidAmount(double amount) throws InvalidExpenseException;
}
