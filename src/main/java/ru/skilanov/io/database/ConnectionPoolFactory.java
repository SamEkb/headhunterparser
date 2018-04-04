package ru.skilanov.io.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Класс для подключения к базе данных.
 */
public class ConnectionPoolFactory {

    /**
     * Connection factory.
     */
    private static ConnectionPoolFactory factory;

    /**
     * Pool.
     */
    private ComboPooledDataSource dataSource;

    /**
     * Конструктор
     */
    private ConnectionPoolFactory() {
        Config config = new Config();
        config.load();
        dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(config.getDriver());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSource.setJdbcUrl(config.getUrl());
        dataSource.setUser(config.getLogin());
        dataSource.setPassword(config.getPassword());

    }

    /**
     * Синглтон.
     *
     * @return ConnectionFactory
     */
    public static ConnectionPoolFactory getInstance() {
        if (factory == null) {
            factory = new ConnectionPoolFactory();
        }
        return factory;
    }

    /**
     * Получение соединения.
     *
     * @return Connection
     * @throws SQLException exception
     */
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }
}
