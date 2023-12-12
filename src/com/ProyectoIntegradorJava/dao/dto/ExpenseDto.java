package com.ProyectoIntegradorJava.dao.dto;

public class ExpenseDto {
    private String id;
    private Double amount;
    private String category;
    private String date;

    public ExpenseDto() {
    }

    public ExpenseDto(String id, Double amount, String date) {
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public ExpenseDto(String id, Double amount, String category, String date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Id = " + id + "\n" +
                "Amount = " + amount + "\n" +
                "Category = " + category + '\n' +
                "Date = " + date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
