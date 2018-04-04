package ru.skilanov.io.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skilanov.io.model.Vacancy;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Класс тестирует dao слой.
 */
@RunWith(MockitoJUnitRunner.class)
public class VacancyDaoImplTest {

    /**
     * Фабрика подключений.
     */
    @Mock
    private ConnectionPoolFactory connectionPoolFactory;

    /**
     * Подключение.
     */
    @Mock
    private Connection connection;

    /**
     * Запрос.
     */
    @Mock
    private PreparedStatement preparedStatement;

    /**
     * Резадт сэт.
     */
    @Mock
    private ResultSet resultSet;

    /**
     * Объект job.
     */
    private Vacancy vacancy;

    /**
     * DAO
     */
    @Mock
    private VacancyDaoImpl vacancyDao;

    /**
     * Метод инициализации перед тестом.
     *
     * @throws SQLException exception
     */
    @Before
    public void setUp() throws SQLException {
        vacancyDao.openConnection();

        assertNotNull(connectionPoolFactory);
        when(connectionPoolFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        Date jobDate = new GregorianCalendar(2018, Calendar.APRIL, 2).getTime();
        vacancy = new Vacancy(1, "https://ekaterinburg.hh.ru/vacancy/3", "Руководитель отдела разработки ПО",
                "", "It tech", "Екатеринбург", jobDate);

        when(resultSet.first()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("url")).thenReturn(vacancy.getUrl());
        when(resultSet.getString("title")).thenReturn(vacancy.getTitle());
        when(resultSet.getString("salary")).thenReturn(vacancy.getSalary());
        when(resultSet.getString("company_name")).thenReturn(vacancy.getCompanyName());
        when(resultSet.getString("location")).thenReturn(vacancy.getLocation());
        when(resultSet.getTimestamp("create_date")).thenReturn(Timestamp.valueOf("2018-04-02 00:00:00"));
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    /**
     * После завершения тестов.
     */
    @After
    public void afterTest() {
        vacancyDao.closeConnection();
    }

    /**
     * Тестирует добавление пользователя.
     */
    @Test
    public void whenWeCreateUserThenReturnRightResult() {
        vacancyDao.insert(vacancy);
    }
}
