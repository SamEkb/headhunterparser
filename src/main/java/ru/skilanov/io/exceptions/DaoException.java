package ru.skilanov.io.exceptions;

/**
 * Исключения dao.
 */
public class DaoException extends RuntimeException {
    /**
     * Метод описывающий ислючение.
     *
     * @param message текст описывающий ошибку
     * @param cause   причина исключения
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
