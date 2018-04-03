package ru.skilanov.io.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для подключения к базе данных.
 */
public class ConnectionFactory {
    /**
     * Драйвер postgreSql
     */
    private static final String DRIVER = "org.postgresql.Driver";

    /**
     * Ссылка на базу данных.
     */
    private static final String URL = "jdbc:postgresql://localhost:5432/headhunter";

    /**
     * Логин для подключения.
     */
    private static final String USERNAME = "postgres";

    /**
     * пароль для подключения.
     */
    private static final String PASSWORD = "root";

    /**
     *
     */
    public ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Not found class " + DRIVER, e);
        }
    }

    /**
     * Метод соединения с БД.
     *
     * @return соединение
     * @throws SQLException исключение
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
