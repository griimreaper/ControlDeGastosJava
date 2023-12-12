package com.ProyectoIntegradorJava.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ExpenseCategory {
    private String id = UUID.randomUUID().toString();
    private String name;
    public ExpenseCategory() {
    }

    public ExpenseCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ExpenseCategory{" +
                "name='" + name + '\'' +
                '}';
    }

    public void getCategories (Map<String, Integer> mapa) {
        System.out.println("Contador por categoria:");
        for (Map.Entry<String,Integer> category: mapa.entrySet()) {
            System.out.println(category.getKey()+ ": " + category.getValue());
        }
    }
}
