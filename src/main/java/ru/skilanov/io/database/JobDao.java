package ru.skilanov.io.database;

import ru.skilanov.io.model.Job;

import java.util.List;

/**
 * Интерфейс содержащий методы для работы с объектами класса Job
 */
public interface JobDao {

    /**
     * Создание таблицы, если она не существует.
     */
    void create();

    /**
     * Ввод данных в балицу.
     *
     * @param job Job
     */
    void insert(Job job);

    /**
     * Получение всех записей.
     *
     * @return List
     */
    List<Job> getAll();

    /**
     * Получение всех вакансий по наименованию компании.
     *
     * @param name String
     * @return List
     */
    List<Job> findByCompanyName(String name);

    /**
     * Поиск вакансии по id.
     *
     * @param id int
     * @return Job
     */
    Job findById(int id);

    /**
     * Удаление вакансии по id.
     *
     * @param id int
     */
    void deleteById(int id);

    /**
     * Удаление всех вакансий.
     */
    void deleteAll();
}
