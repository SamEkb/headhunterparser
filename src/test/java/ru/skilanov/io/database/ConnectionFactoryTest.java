package ru.skilanov.io.database;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

/**
 * Класс тестирует подключение к БД.
 */
public class ConnectionFactoryTest {

    /**
     * Подключение
     */
    private ConnectionFactory connectionFactory;

    /**
     * Инициализация перед тестами.
     */
    @Before
    public void setUp() {
        connectionFactory = new ConnectionFactory();
    }

    /**
     * Метод проверяет происходит ли подключение к БД.
     *
     * @throws SQLException exception
     */
    @Test
    public void whenWeGetConnectionThenReturnRightResult() throws SQLException {
        assertNotNull(connectionFactory.getConnection());
    }
}
