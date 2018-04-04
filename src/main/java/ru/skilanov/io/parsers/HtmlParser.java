package ru.skilanov.io.parsers;

import ru.skilanov.io.model.Vacancy;

import java.util.Collection;

/**
 * Интерфейс содержащий методы для парсинга вакансии при помощи jsoup.
 */
public interface HtmlParser {

    /**
     * Метод разбора вакансии.
     *
     * @param page String
     * @return вакансии
     */
    Collection<Vacancy> parseVacations(String page);
}
