package ru.skilanov.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для подключения к базе данных.
 */
public class ConnectionFactory {

    /**
     * Константная строка.
     */
    private static final String NOT_FOUND_CLASS = "Not found class ";

    /**
     * Конструктор.
     */
    public ConnectionFactory() {
        Config config = new Config();
        config.load();
        try {
            Class.forName(config.getDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(NOT_FOUND_CLASS + config.getDriver(), e);
        }
    }

    /**
     * Метод соединения с БД.
     *
     * @return соединение
     * @throws SQLException исключение
     */
    public Connection getConnection() throws SQLException {
        Config config = new Config();
        config.load();
        return DriverManager.getConnection(config.getUrl(), config.getLogin(), config.getPassword());
    }
}
