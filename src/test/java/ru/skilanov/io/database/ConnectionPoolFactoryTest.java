package ru.skilanov.io.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Класс тестирует подключение к БД.
 */
public class ConnectionPoolFactoryTest {

    /**
     * Connection.
     */
    private Connection connection;

    /**
     * Инициализация перед тестом.
     */
    @Before
    public void setUp() throws SQLException {
        connection = ConnectionPoolFactory.getInstance().getConnection();
    }

    /**
     * После завершения теста.
     *
     * @throws SQLException
     */
    @After
    public void afterTest() throws SQLException {
        connection.close();
    }

    /**
     * Метод проверяет происходит ли подключение к БД.
     */
    @Test
    public void whenWeGetConnectionThenReturnRightResult() {
        assertNotNull(connection);
    }
}
