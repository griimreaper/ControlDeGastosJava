package com.ProyectoIntegradorJava.dao;

import com.ProyectoIntegradorJava.dao.dto.ExpenseDto;
import com.ProyectoIntegradorJava.models.Expense;
import com.ProyectoIntegradorJava.models.ExpenseCategory;

import java.util.List;

public interface ExpenseDao {
    public void insert(Expense expense);
    public void update(Expense expense);
    public void delete(String id);
    public void readExpense(String id);
    public ExpenseDto getExpense(String id);
    public List<ExpenseDto> getAllExpenses();
}
