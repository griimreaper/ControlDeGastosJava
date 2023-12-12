package com.ProyectoIntegradorJava.interfaces;

import com.ProyectoIntegradorJava.dao.dto.ExpenseDto;
import com.ProyectoIntegradorJava.models.Expense;

import java.util.List;

public interface ExpenseCalculator {
    double calculateExpense(Expense expense);
    double calculateTotalExpense(List<Expense> expenses);

    double calculateAmount (List<Double> amounts);

    double calculateTotalExpenseDto(List<ExpenseDto> allExpenses);
}
