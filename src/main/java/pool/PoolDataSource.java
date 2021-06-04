package pool;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PoolDataSource {
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();
    /* описание настроек
    static {
        cpds.setDriverClass("org.postgresql.Driver");
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/lessons");
    }*/
    // Настройки  писать лучше в статик блоке, если работаем со статиком

    public static Connection getConnection(){// получить из пула доступный объект

        try {
            return cpds.getConnection() ;
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось установить" + "соединение " + e.getMessage());
        }

        // Если не готовы обр Exp, тогда только в сигнатуру метода.
    }
}
