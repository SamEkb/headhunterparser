package ru.skilanov.parsers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Класс тестирует соединение с сатом.
 */
public class WebConnectionFactoryTest {
    /**
     * Соединение с сайтом.
     */
    private HeadHunterPageReaderImpl headHunterPageReaderImpl;

    /**
     * Метод инициализации перед тестами.
     */
    @Before
    public void setUp() {
        headHunterPageReaderImpl = new HeadHunterPageReaderImpl();
    }

    /**
     * Метод проверяет осуществлено ли соединение.
     */
    @Test
    public void whenGetConnection_ThenReturnRightResult() {
        assertNotNull(headHunterPageReaderImpl.readPage(1));
    }

    /**
     * Метод проверяет возвращаемый заголовок.
     */
    @Test
    public void whenGetTitle_ThenReturnRightResult() {
        String expected = "Работа в Екатеринбурге, вакансии Екатеринбурга, поиск работы в Екатеринбурге - ekaterinburg.hh.ru";
        String result = headHunterPageReaderImpl.readPage(1);

        Boolean contains = result.contains(expected);

        assertTrue(contains);
    }
}
