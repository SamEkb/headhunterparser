package ru.skilanov.database;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

/**
 * Класс тестирует подключение к БД.
 */
public class ConnectionFactoryTest {

    /**
     * Connection.
     */
    private Connection connection;

    /**
     * Инициализация перед тестом.
     */
    @Before
    public void setUp() throws SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connection = connectionFactory.getConnection();
    }

    /**
     * После завершения теста.
     *
     * @throws SQLException sqlexceprion
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
