package ru.skilanov.database;

import ru.skilanov.model.Vacancy;

import java.util.List;

/**
 * Интерфейс содержащий методы для работы с объектами класса Job.
 */
public interface VacancyDao {

    /**
     * Ввод данных в балицу.
     *
     * @param vacancy Job
     */
    void insert(Vacancy vacancy);

    /**
     * Получение всех записей.
     *
     * @return List
     */
    List<Vacancy> getAll();

    /**
     * Получение всех вакансий по наименованию компании.
     *
     * @param name String
     * @return List
     */
    List<Vacancy> findByCompanyName(String name);

    /**
     * Поиск вакансии по id.
     *
     * @param id int
     * @return Job
     */
    Vacancy findById(int id);

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
