package Test;

import com.ProyectoIntegradorJava.dao.impl.ExpenseCategoryImplh2;
import com.ProyectoIntegradorJava.models.ExpenseCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("CategoryDB Test")
class ExpenseCategoryImplh2Test {
    @Mock
    private JdbcConfigurationTest jdbcConfigurationTest;
    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement preparedStatementMock;
    @Mock
    private ResultSet resultSetMock;
    @InjectMocks
    private ExpenseCategoryImplh2 expenseCategoryImplh2 = new ExpenseCategoryImplh2(jdbcConfigurationTest.getDBConnection());
    @BeforeEach
    public void setUp() throws SQLException {
    MockitoAnnotations.initMocks(this);
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);

    }
    @AfterEach
    public void dropTable() throws SQLException {
        jdbcConfigurationTest.getDBConnection().createStatement().executeUpdate("DROP TABLE expensescategory");
    }
    @DisplayName("Insertar una Categoria a la BD")
    @Test
    public void insertCategoryTest() throws SQLException {
        ExpenseCategory expenseCategory = new ExpenseCategory("comidas");
        Connection connection = jdbcConfigurationTest.getDBConnection();
        String sql = "INSERT INTO expensescategory (id, name) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        expenseCategoryImplh2.insert(expenseCategory);

        spy(connection).prepareStatement(sql);
        spy(preparedStatement).setString(1,expenseCategory.getId());
        spy(preparedStatement).setString(2,expenseCategory.getName());

        when(connectionMock.prepareStatement(sql)).thenReturn(preparedStatementMock);
    }

    @DisplayName("Obtener Categoria de BD")
    @Test
    public void getCategoryByIdTest() throws SQLException {
        Connection connection = jdbcConfigurationTest.getDBConnection();
        ExpenseCategory expenseCategory = new ExpenseCategory("comidas");
        String sql = "SELECT * FROM expensescategory WHERE id = ?";
        expenseCategoryImplh2.insert(expenseCategory);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, expenseCategory.getId());
        ResultSet resultSet = preparedStatement.executeQuery();

        ExpenseCategory result = expenseCategoryImplh2.getCategoryById(expenseCategory.getId());

        spy(connection).prepareStatement(sql);
        spy(preparedStatement).setString(1, expenseCategory.getId());
        doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
        spy(resultSet).next();
        spy(resultSet).getString("id");
        doReturn(expenseCategory.getId()).when(resultSetMock).getString("id");
        spy(resultSet).getString("name");
        doReturn(expenseCategory.getName()).when(resultSetMock).getString("name");
        spy(expenseCategory).setId(expenseCategory.getId());
        assertTrue(result instanceof ExpenseCategory);
        assertEquals(result.getId(), expenseCategory.getId());
        assertEquals(result.getName(), expenseCategory.getName());
    }

    @DisplayName("Eliminar Categoria de DB")
    @Test
    public void deleteCategoryTest() throws SQLException {
        Connection connection = jdbcConfigurationTest.getDBConnection();
        ExpenseCategory expenseCategory = new ExpenseCategory("comida");
        String sql = "DELETE FROM expensescategory WHERE id = ?";
        expenseCategoryImplh2.insert(expenseCategory);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        expenseCategoryImplh2.delete(expenseCategory.getId());

        spy(connection).prepareStatement(sql);
        spy(preparedStatement).setString(1, expenseCategory.getId());
        doReturn(1).when(preparedStatementMock).executeUpdate();
    }
    @DisplayName("Obtener varias Categorias")
    @Test
    public void getAllCategoryTest() throws SQLException {
        Connection connection = jdbcConfigurationTest.getDBConnection();
        List<ExpenseCategory> list = new ArrayList<>();
        list.add(new ExpenseCategory("Comidas"));
        list.add(new ExpenseCategory("Varios"));
        list.forEach(expenseCategory -> expenseCategoryImplh2.insert(expenseCategory));
        String sql = "SELECT * FROM expensescategory";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<ExpenseCategory> result = expenseCategoryImplh2.getAllCategories();

        spy(connection).prepareStatement(sql);
        doReturn(resultSet).when(preparedStatementMock).executeQuery();
        spy(resultSet).next();
        for (int i = 0; i < result.size(); i++) {
            assertEquals(result.get(i).getId(), list.get(i).getId());
            assertEquals(result.get(i).getName(), list.get(i).getName());
        }
    }
}