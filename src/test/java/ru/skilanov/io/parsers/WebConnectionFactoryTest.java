package ru.skilanov.io.parsers;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Класс тестирует соединение с сатом.
 */
public class WebConnectionFactoryTest {
    /**
     * Соединение с сайтом.
     */
    private WebConnectionFactory webConnectionFactory;

    /**
     * Метод инициализации перед тестами.
     */
    @Before
    public void setUp() {
        webConnectionFactory = new WebConnectionFactory();
    }

    /**
     * Метод проверяет осуществлено ли соединение.
     */
    @Test
    public void whenGetConnectionThenReturnRightResult() {
        assertNotNull(webConnectionFactory.getConnection(1));
    }

    /**
     * Метод проверяет возвращаемый заголовок.
     */
    @Test
    public void whenWeGetTitleThenReturnRightResult() {
        String expected = "Работа в Екатеринбурге, вакансии Екатеринбурга, поиск работы в Екатеринбурге - ekaterinburg.hh.ru";
        String result = webConnectionFactory.getConnection(1).title();

        assertThat(expected, is(result));
    }
}
