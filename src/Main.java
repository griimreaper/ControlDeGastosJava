import com.ProyectoIntegradorJava.manager.Manager;
import org.h2.jdbc.JdbcConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.manager();
    }
}