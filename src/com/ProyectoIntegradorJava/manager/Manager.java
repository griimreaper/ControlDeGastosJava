package com.ProyectoIntegradorJava.manager;

import com.ProyectoIntegradorJava.config.JdbcConfiguration;
import com.ProyectoIntegradorJava.dao.ExpenseCategoryDao;
import com.ProyectoIntegradorJava.dao.ExpenseDao;
import com.ProyectoIntegradorJava.dao.dto.ExpenseDto;
import com.ProyectoIntegradorJava.dao.impl.ExpenseCategoryImplh2;
import com.ProyectoIntegradorJava.dao.impl.ExpenseImplH2;
import com.ProyectoIntegradorJava.exceptions.InvalidExpenseException;
import com.ProyectoIntegradorJava.interfaces.ExpenseAmountValidator;
import com.ProyectoIntegradorJava.interfaces.ExpenseAmountValidatorImpl;
import com.ProyectoIntegradorJava.interfaces.ExpenseCalculatorImpl;
import com.ProyectoIntegradorJava.models.Expense;
import com.ProyectoIntegradorJava.models.ExpenseCategory;
import com.ProyectoIntegradorJava.util.Utilities;
import org.h2.jdbc.JdbcConnection;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Stream;

public class Manager {
    Connection connection = new JdbcConfiguration().getDBConnection();
    ExpenseDao expenseDao = new ExpenseImplH2(connection);
    ExpenseCategoryDao expenseCategoryDao = new ExpenseCategoryImplh2(connection);
    Scanner scanner = new Scanner(System.in);
    public void manager() {
        int num = 0;
        do {
            System.out.println("Control De Gastos");
            System.out.println("1 Ver gastos");
            System.out.println("2 Agregar gastos");
            System.out.println("3 Remover gasto");
            System.out.println("4 Top gastos");
            System.out.println("5 Total de gastos");
            System.out.println("6 Ver Categorias");
            System.out.println("7 Filtrar gastos por categoria");
            System.out.println("0 Finalizar");

            num = scanner.nextInt();
            try {
            switch (num) {
                case 1 -> detailExpenses();
                case 2 -> addExpense();
                case 3 -> removeExpense();
                case 4 -> topExpenses();
                case 5 -> totalExpenses();
                case 6 -> countCategories();
                case 7 -> filterExpenseByCategory();
            }
            } catch (InvalidExpenseException e) {
                throw new RuntimeException(e);
            }
        } while (num != 0);
    }
    public void addExpense() throws InvalidExpenseException {
        int index = expenseDao.getAllExpenses().size();

        ExpenseAmountValidator expenseAmountValidator = new ExpenseAmountValidatorImpl();

        boolean cutLogicVar;
        System.out.print("¿Desea cargar un gasto? Si/No: ");
        scanner.nextLine();
        cutLogicVar = scanner.nextLine().equalsIgnoreCase("si");

        while(cutLogicVar) {
            Expense expense = new Expense();

            System.out.print("Ingrese el monto del gasto " + (index + 1) + ": ");
            Double amount = scanner.nextDouble();

            if (!expenseAmountValidator.notvalidAmount(amount)) {
                System.out.println("El monto es valido");
            }

            scanner.nextLine();

            System.out.print("Ingrese la categoria del gasto: ");
            String name = scanner.nextLine().toLowerCase().trim();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);

            System.out.print("Ingrese la fecha del gasto: (dd/MM/yyyy)");
            String date = scanner.nextLine();

            expense.setAmount(amount);
            ExpenseCategory expenseCategory = new ExpenseCategory(name);
            expenseCategoryDao.insert(expenseCategory);
            expense.setCategory(expenseCategory);
            expense.setDate(date);

            expenseDao.insert(expense);

            index++;

            System.out.print("¿Desea cargar otro gasto? Si/No: ");
            cutLogicVar = scanner.nextLine().equalsIgnoreCase("si");
        };
    };

    public void topExpenses() {
        scanner.nextLine();
        System.out.print("Ingrese el número de gastos que desea visualizar: ");
        int num = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Si desea ordenar de mayor a menor escriba Si, de lo contrario escriba No.");
        String option = scanner.nextLine();
        boolean order = option.equalsIgnoreCase("si");
        System.out.println("TOP " + num + " DE GASTOS INGRESADOS");

        List<Double> amounts = expenseDao.getAllExpenses().stream()
                .map(ExpenseDto::getAmount)
                .toList();

        Stream<Double> amountSorted = order
                ? amounts.stream().sorted(Comparator.reverseOrder())
                : amounts.stream().sorted();

        List<Double> result = amountSorted.limit(num)
                .toList();

        result.forEach(System.out::println);
    }

    public void totalExpenses () {
        System.out.println("Total de gastos ingresados: "+ new ExpenseCalculatorImpl().calculateTotalExpenseDto(expenseDao.getAllExpenses()));
    };

    public void detailExpenses () {
        System.out.println("DETALLE DE GASTOS INGRESADOS:");
        System.out.println("|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|");
        Utilities.printElements(expenseDao.getAllExpenses());
    }

    public void filterExpenseByCategory() {
        scanner.nextLine();
        System.out.println("Ingrese el nombre de la categoria");
        String category = scanner.nextLine();
        List<ExpenseDto> filterList = expenseDao.getAllExpenses().stream()
                .filter(expenseDto -> expenseDto.getCategory().equalsIgnoreCase(category))
                .toList();

        System.out.println("Gastos Filtrados Por La Categoria " + category);
        System.out.println("|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|");
        Utilities.printElements(filterList);
        System.out.println("Total de gastos de " + category + ":" + new ExpenseCalculatorImpl().calculateTotalExpenseDto(filterList));
    }

    public void countCategories () {

        new ExpenseCategory().getCategories(countCategory);
    }

    public void removeExp        Map<String, Integer> countCategory = new HashMap();
        expenseCategoryDao.getAllCategories().forEach(category -> {
        String name = category.getName();
        if (countCategory.containsKey(name)) {
            int count = countCategory.get(name);
            countCategory.put(name, count + 1);
        } else {
            countCategory.put(name, 1);
        }
    });ense () {
        scanner.nextLine();
        detailExpenses();
        System.out.println("Ingrese el id del gasto que desea eliminar");
        String id = scanner.nextLine();

        expenseCategoryDao.delete(expenseDao.getExpense(id).getCategory());
        expenseDao.delete(id);
    }
}
