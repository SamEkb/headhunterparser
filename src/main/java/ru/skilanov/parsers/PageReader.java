package ru.skilanov.parsers;

/**
 * Интерфейс содержащий метод для чтения html страницы.
 */
public interface PageReader {
    /**
     * Метод получения html страницы.
     *
     * @param pageNumber номер страницы.
     * @return String
     */
    String readPage(int pageNumber);
}
