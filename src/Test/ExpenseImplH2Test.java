package Test;

import com.ProyectoIntegradorJava.config.JdbcConfiguration;
import com.ProyectoIntegradorJava.dao.dto.ExpenseDto;
import com.ProyectoIntegradorJava.dao.impl.ExpenseCategoryImplh2;
import com.ProyectoIntegradorJava.dao.impl.ExpenseImplH2;
import com.ProyectoIntegradorJava.models.Expense;
import com.ProyectoIntegradorJava.models.ExpenseCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class ExpenseImplH2Test {
    @Mock
    private JdbcConfigurationTest jdbcConfigurationTest;
    @Mock
    private ExpenseCategoryImplh2 expenseCategoryDaoMock;
    @Mock
    private PreparedStatement preparedStatementMock;
    @Mock
    private Connection connectionMock;
    @Mock
    private ResultSet resultSetMock;
    @Mock
    // se agrega este mock para crear la tabla expenseCategory de la bd
    private ExpenseCategoryImplh2 expenseCategoryDao = new ExpenseCategoryImplh2(jdbcConfigurationTest.getDBConnection());
    @InjectMocks
    private ExpenseImplH2 expenseDaoMock = new ExpenseImplH2(jdbcConfigurationTest.getDBConnection());
    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
    }

    @AfterEach // se eliminan las tablas luego de cada test, asi no se apilan los datos.
    void dropTables() throws SQLException {
        jdbcConfigurationTest.getDBConnection().createStatement().executeUpdate("DROP TABLE expenses");
        jdbcConfigurationTest.getDBConnection().createStatement().executeUpdate("DROP TABLE expensescategory");
    }
    @Test
    @DisplayName("Agregar un nuevo gasto")
    public void addExpenseTest() throws SQLException {
        Double amount = 20000.0;
        ExpenseCategory category = new ExpenseCategory("comida");
        String date = "20/19/1999";

        Expense expense = new Expense(amount, category, date);

        String sql = "INSERT INTO expenses (id, amount, categoryID, date) VALUES (?, ?, ?, ?)";

        try (Connection connection = JdbcConfiguration.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, expense.getId());
            preparedStatement.setDouble(2, amount);
            preparedStatement.setString(3, category.getId());
            preparedStatement.setString(4, date);

            int rowsAffected = preparedStatement.executeUpdate();

            assertEquals(1, rowsAffected, "Se esperaba que una fila fuera afectada por la inserción");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción al intentar insertar en la base de datos: " + e.getMessage());
        }

    }
    @Test
    @DisplayName("Obtener gasto de BD")
    public void getExpenseTest() throws SQLException {
        Double amount = 20000.0;
        ExpenseCategory category = new ExpenseCategory("comida");
        String date = "20/19/1999";
        Expense expense = new Expense(amount, category, date);
        String sql = "SELECT * FROM expenses WHERE id = ?";

        expenseCategoryDaoMock.insert(category);
        expenseDaoMock.insert(expense);
        ExpenseDto toReturn = new ExpenseDto(expense.getId(), amount, date);
        toReturn.setCategory(category.getId());

        ExpenseDto result = expenseDaoMock.getExpense(expense.getId());

        when(connectionMock.prepareStatement(sql)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.getString("id")).thenReturn(anyString());
        when(resultSetMock.getDouble("amount")).thenReturn(eq(amount));
        when(resultSetMock.getString("date")).thenReturn(eq(date));
        when(resultSetMock.getString("category")).thenReturn(eq(category.getId()));

        assertEquals(result.getId(),toReturn.getId());
        assertEquals(result.getCategory(), toReturn.getCategory());
        assertEquals(result.getAmount(), toReturn.getAmount());
        assertEquals(result.getDate(), toReturn.getDate());
        }

    @Test
    @DisplayName("Eliminar un gasto de BD")
    public void deleteExpenseTest() throws SQLException {
        Double amount = 20000.0;
        ExpenseCategory category = new ExpenseCategory("comida");
        String date = "20/19/1999";
        Expense expense = new Expense(amount, category, date);
        String sql = "DELETE FROM expenses WHERE id = ?";

        expenseCategoryDaoMock.insert(category);
        expenseDaoMock.insert(expense);

        expenseDaoMock.delete(expense.getId());

        when(connectionMock.prepareStatement(sql)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(1);
    }
    @Test
    @DisplayName("Obtener lista de gastos")
    public void getExpenseListTest() throws SQLException {
        List<Expense> expenseDtoList = new ArrayList<>();
        String sql = "SELECT * FROM expenses";
        expenseDtoList.add(new Expense(20000.0, new ExpenseCategory("comidas"), "20/10/2020"));
        expenseDtoList.add(new Expense(10000.0, new ExpenseCategory("varios"), "10/10/2020"));

        expenseDaoMock.insert(expenseDtoList.get(0));
        expenseDaoMock.insert(expenseDtoList.get(1));

        List<ExpenseDto> result = expenseDaoMock.getAllExpenses();

        when(connectionMock.prepareStatement(sql)).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);

        for (int x = 0; x < result.size(); x++) {
            assertEquals(result.get(x).getId(), expenseDtoList.get(x).getId());
            assertEquals(result.get(x).getCategory(), expenseDtoList.get(x).getCategory().getId());
            assertEquals(result.get(x).getAmount(), expenseDtoList.get(x).getAmount());
            assertEquals(result.get(x).getDate(), expenseDtoList.get(x).getDate());
        }
    }
    }