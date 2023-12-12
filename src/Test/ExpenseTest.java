package Test;

import com.ProyectoIntegradorJava.models.Expense;
import com.ProyectoIntegradorJava.models.ExpenseCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Pruebas para Expenses")
class ExpenseTest {
    @Test
    @DisplayName("Prueba de creacion")
    public void newExpensesWithValidValues() {
        // GIVEN
        Double amount = 20000.0;
        ExpenseCategory category = new ExpenseCategory("comida");
        String date = "20/19/1999";
        // WHEN
        Expense newExpense = new Expense(amount, category, date);
        // THEN
        assertNotNull(newExpense.getId());
        assertEquals(newExpense.getAmount(), amount);
        assertEquals(newExpense.getCategory(), category);
        assertEquals(newExpense.getDate(), date);
    }

    @Test
    @DisplayName("Prueba de seteo de atributos.")
    public void ExpensesSetMethods() {
        // GIVEN
        Double amount = 20000.0;
        ExpenseCategory category = new ExpenseCategory("comida");
        String date = "20/19/1999";
        Expense newExpense = new Expense(amount, category, date);
        // WHEN
        ExpenseCategory categoryFalse = new ExpenseCategory("varios");
        newExpense.setCategory(categoryFalse);
        newExpense.setAmount(3000.0);
        newExpense.setDate("20/10/2022");
        newExpense.setId("idfalso");
        // THEN
        assertEquals(newExpense.getId(), "idfalso");
        assertEquals(newExpense.getDate(), "20/10/2022");
        assertEquals(newExpense.getCategory(),categoryFalse);
        assertEquals(newExpense.getAmount(), 3000.0);
    }
}