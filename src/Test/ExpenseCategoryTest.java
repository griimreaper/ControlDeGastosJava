package Test;

import com.ProyectoIntegradorJava.models.ExpenseCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Prueba para Categorias")
class ExpenseCategoryTest {

    @Test
    @DisplayName("Prueba de creacion")
    void createExpenseCategory() {
        // GIVEN
        String name = "comida";
        // WHEN
        ExpenseCategory newCategory = new ExpenseCategory(name);
        // THEN
        assertNotNull(newCategory);
        assertEquals(newCategory.getName(), name);
    }

    @Test
    @DisplayName("Prueba de seteo de atributos")
    public void categorySetMethods() {
        // GIVEN
        String name = "comida";
        ExpenseCategory newCategory = new ExpenseCategory(name);
        // WHEN
        String newName = "ropa";
        newCategory.setName(newName);
        // THEN
        assertNotNull(newCategory);
        assertEquals(newCategory.getName(), newName);
    }

}