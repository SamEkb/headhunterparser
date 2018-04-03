package ru.skilanov.io.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skilanov.io.model.Job;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Класс тестирует dao слой.
 */
@RunWith(MockitoJUnitRunner.class)
public class JobDaoImplTest {

    /**
     * Фабрика подключений.
     */
    @Mock
    private ConnectionFactory connectionFactory;

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
    private Job job;

    /**
     * метод инициализации перед тестами.
     *
     * @throws SQLException exception
     */
    @Before
    public void setUp() throws SQLException {
        assertNotNull(connectionFactory);
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        Date jobDate = new GregorianCalendar(2018, Calendar.APRIL, 2).getTime();
        job = new Job(1, "https://ekaterinburg.hh.ru/vacancy/3", "Руководитель отдела разработки ПО",
                "", "It tech", "Екатеринбург", jobDate);

        when(resultSet.first()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("url")).thenReturn(job.getUrl());
        when(resultSet.getString("title")).thenReturn(job.getTitle());
        when(resultSet.getString("salary")).thenReturn(job.getSalary());
        when(resultSet.getString("company_name")).thenReturn(job.getCompanyName());
        when(resultSet.getString("location")).thenReturn(job.getLocation());
        when(resultSet.getTimestamp("create_date")).thenReturn(Timestamp.valueOf("2018-04-02 00:00:00"));
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    /**
     * После завершения тестов.
     *
     * @throws SQLException exception
     */
    @After
    public void afterTest() throws SQLException {
        connection.close();
    }

    /**
     * Тестирует добавление null и жидает исключение.
     */
    @Test(expected = NullPointerException.class)
    public void whenWeCreateNullUserThenReturnException() {
        new JobDaoImpl(connectionFactory).insert(null);
    }

    /**
     * Тестирует добавление пользователя.
     */
    @Test
    public void whenWeCreateUserThenReturnRightResult() {
        new JobDaoImpl(connectionFactory).insert(job);
    }
}
