package com.ProyectoIntegradorJava.models;

import java.util.UUID;

public class Expense {
    private String id = UUID.randomUUID().toString();
    private Double amount;
    private ExpenseCategory category;
    private String date;

    public Expense() {
    }

    public Expense(Double amount, ExpenseCategory category, String date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return  "id= " + id +
                "\ngasto= " + amount +
                "\ncategoria= " + category.getName() +
                "\nfecha= '" + date + '\'';
    }
}
