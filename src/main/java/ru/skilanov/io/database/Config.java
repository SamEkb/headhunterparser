package ru.skilanov.io.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс настроек к базе данныхю
 */
public class Config {

    /**
     * Свойства.
     */
    private Properties properties = new Properties();

    /**
     * Метод загружает настройки из файла в ресурсах.
     */
    public void load() {
        try {
            String path = "src\\main\\resources\\config.properties";
            FileInputStream fis = new FileInputStream(path);
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод возвращает совйство url в виде строки.
     *
     * @return String
     */
    public String getUrl() {
        return properties.getProperty("jdbc.url");
    }

    /**
     * Метод возвращает совйство driver в виде строки.
     *
     * @return String
     */
    public String getDriver() {
        return properties.getProperty("jdbc.driver");
    }

    /**
     * Метод возвращает совйство login в виде строки.
     *
     * @return String
     */
    public String getLogin() {
        return properties.getProperty("jdbc.username");
    }

    /**
     * Метод возвращает совйство password в виде строки.
     *
     * @return String
     */
    public String getPassword() {
        return properties.getProperty("jdbc.password");
    }
}
