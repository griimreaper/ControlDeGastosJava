package com.ProyectoIntegradorJava.dao.impl;

import com.ProyectoIntegradorJava.config.JdbcConfiguration;
import com.ProyectoIntegradorJava.dao.ExpenseDao;
import com.ProyectoIntegradorJava.dao.dto.ExpenseDto;
import com.ProyectoIntegradorJava.models.Expense;
import com.ProyectoIntegradorJava.models.ExpenseCategory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseImplH2 implements ExpenseDao {
    private final Connection connection;

    public ExpenseImplH2(Connection connection) {
        this.connection = connection;
        try {
            Statement statement = connection.createStatement();
            String createTableExpense = "CREATE TABLE IF NOT EXISTS expenses (id VARCHAR(60) PRIMARY KEY, amount DOUBLE, categoryID VARCHAR(50), date VARCHAR(50))";
            statement.executeUpdate(createTableExpense);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void insert(Expense expense) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO expenses (id, amount, categoryID, date) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, expense.getId());
            preparedStatement.setDouble(2, expense.getAmount());
            preparedStatement.setString(3, expense.getCategory().getId());
            preparedStatement.setString(4, expense.getDate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Expense expense) {
        String sql = "UPDATE expenses SET amount = ?, categoryID = ?, date = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, expense.getAmount());
            preparedStatement.setString(2, expense.getCategory().getId());
            preparedStatement.setString(3, expense.getDate());
            preparedStatement.setString(4, expense.getId());

            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("El gasto se actualizo correctamente.");
            } else {
                System.out.println("No se encontro el gasto correspondiente.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM expenses WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, id);

            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("El gasto se eliminó exitosamente.");
            } else {
                System.out.println("No se encontró el gasto especificado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readExpense(String id) {
        String sql = "SELECT * FROM tabla_usuarios WHERE id = ?";
        try {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, id);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String rsId = resultSet.getString("id");
                    Double rsAmount = resultSet.getDouble("amount");
                    String rsCategory = resultSet.getString("categoryID");
                    String rsDate = resultSet.getString("date");

                    System.out.println("ID: " + rsId);
                    System.out.println("Categoria: " + rsCategory);
                    System.out.println("Monto: " + rsAmount);
                    System.out.println("Fecha: " + rsDate);
                    System.out.println("--------------");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public ExpenseDto getExpense(String id) {
        ExpenseDto expense = new ExpenseDto();
        String sql = "SELECT * FROM expenses WHERE id = ?";
        try {
             PreparedStatement statement = connection.prepareStatement(sql);
             statement.setString(1, id);

             ResultSet resultSet = statement.executeQuery();

             if (resultSet.next()) {
                String rsId = resultSet.getString("id");
                Double rsAmount = resultSet.getDouble("amount");
                String rsCategory = resultSet.getString("categoryID");
                String rsDate = resultSet.getString("date");

                expense.setId(rsId);
                expense.setCategory(rsCategory);
                expense.setAmount(rsAmount);
                expense.setDate(rsDate);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
        }
        return expense;
    }

    @Override
    public List<ExpenseDto> getAllExpenses() {
        List<ExpenseDto> expenses = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String rsId = resultSet.getString("id");
                Double rsAmount = resultSet.getDouble("amount");
                String rsCategory = resultSet.getString("categoryID");
                String rsDate = resultSet.getString("date");

                ExpenseDto exps = new ExpenseDto(rsId, rsAmount, rsCategory, rsDate);
                exps.setId(rsId); // se le setea un id para mostrar el pantalla el mismo que se extrae de la base de datos

                expenses.add(exps);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return expenses;
    }

    public void cerrarConexion(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
