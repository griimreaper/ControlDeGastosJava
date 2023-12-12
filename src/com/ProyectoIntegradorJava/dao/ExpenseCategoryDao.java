package com.ProyectoIntegradorJava.dao;

import com.ProyectoIntegradorJava.models.ExpenseCategory;

import java.util.List;

public interface ExpenseCategoryDao {
    public void insert(ExpenseCategory expenseCategory);
    public void update(String name, String newName);
    public void delete(String id);
    public ExpenseCategory getCategoryById(String id);
    public List<ExpenseCategory> getAllCategories();
}
