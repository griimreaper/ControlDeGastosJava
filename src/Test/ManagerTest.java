package Test;

import com.ProyectoIntegradorJava.config.JdbcConfiguration;
import com.ProyectoIntegradorJava.dao.dto.ExpenseDto;
import com.ProyectoIntegradorJava.dao.impl.ExpenseCategoryImplh2;
import com.ProyectoIntegradorJava.dao.impl.ExpenseImplH2;
import com.ProyectoIntegradorJava.manager.Manager;
import com.ProyectoIntegradorJava.models.Expense;
import com.ProyectoIntegradorJava.models.ExpenseCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@DisplayName("Manager Test")
class ManagerTest {
    @Mock
    private JdbcConfiguration jdbcConfiguration = mock(JdbcConfiguration.class);
    private ExpenseImplH2 expenseImplH2Mock = mock(ExpenseImplH2.class);
    private ExpenseCategoryImplh2 expenseCategoryImplh2 = mock(ExpenseCategoryImplh2.class);
    @InjectMocks
    Manager manager;

    @BeforeEach
    public void init() {
        manager = new Manager();

    }

}
