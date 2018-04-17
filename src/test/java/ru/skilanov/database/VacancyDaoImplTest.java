package ru.skilanov.database;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.skilanov.model.Vacancy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static org.h2.engine.Constants.UTF8;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Интеграционные тесты на слой Dao.
 */
public class VacancyDaoImplTest {
    /**
     * Dao вакансий.
     */
    private VacancyDao vacancyDao;

    /**
     * Перед стартом тестов создаем базу данных.
     *
     * @throws Exception Exception
     */
    @BeforeClass
    public static void createSchema() throws Exception {
        String db = "src\\test\\resources\\schema.sql";
        RunScript.execute("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                "sa", "", db, UTF8, false);
    }

    /**
     * Перед стартом теста инициализирем Dao.
     */
    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        vacancyDao = new VacancyDaoImpl(connectionFactory);
    }

    @After
    public void tearDown() throws Exception {
        vacancyDao.deleteAll();
        try (Connection connection = new ConnectionFactory().getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("ALTER TABLE job ALTER COLUMN id RESTART WITH 1");
        }
    }

    /**
     * Метод тестирует удаление по id.
     */
    @Test
    public void whenDeleteById_ThenItDeleted() {
        Date date = new GregorianCalendar(2018, Calendar.APRIL, 1).getTime();
        Vacancy microsoftVacancy = new Vacancy(1, "www.somewhere.org", "C# developer",
                "from 3000 usd", "Microsoft", "Los Angele's, California, USA", date);

        vacancyDao.insert(microsoftVacancy);
        vacancyDao.deleteById(1);

        assertNull(vacancyDao.findById(1));
    }

    /**
     * Метод тестирует удаление все вакансий.
     */
    @Test
    public void whenDeleteAll_ThenItEmpty() {
        Date date = new GregorianCalendar(2018, Calendar.APRIL, 1).getTime();
        Vacancy microsoftVacancy = new Vacancy(1, "www.somewhere.org", "C# developer",
                "from 3000 usd", "Microsoft", "Los Angele's, California, USA", date);
        Vacancy oracleVacancy = new Vacancy(2, "www.somewhere.org", "Java developer",
                "from 3000 usd", "Oracle", "Redwood City, California, USA", date);

        vacancyDao.insert(microsoftVacancy);
        vacancyDao.insert(oracleVacancy);

        vacancyDao.deleteAll();

        assertTrue(vacancyDao.getAll().isEmpty());
    }

    /**
     * Метод тестирует поиск по id.
     */
    @Test
    public void whenFindById_ThenReturnRightResult() {
        Date date = new GregorianCalendar(2018, Calendar.APRIL, 1).getTime();
        Vacancy microsoftVacancy = new Vacancy(1, "www.somewhere.org", "C# developer",
                "from 3000 usd", "Microsoft", "Los Angele's, California, USA", date);

        vacancyDao.insert(microsoftVacancy);

        Vacancy result = vacancyDao.findById(1);

        assertThat(microsoftVacancy, is(result));
    }

    /**
     * Метод тестирует поиск по наименованию компании.
     */
    @Test
    public void whenFindByCompanyName_ThenReturnRightResult() {
        Date date = new GregorianCalendar(2018, Calendar.APRIL, 1).getTime();
        Vacancy microsoftVacancy = new Vacancy(1, "www.somewhere.org", "C# developer",
                "from 3000 usd", "Microsoft", "Los Angele's, California, USA", date);

        vacancyDao.insert(microsoftVacancy);

        List<Vacancy> vacancies = vacancyDao.findByCompanyName("Microsoft");

        assertThat(vacancies.get(0).getCompanyName(), is(microsoftVacancy.getCompanyName()));
    }

    /**
     * Метод тестирует добавление вакансии.
     */
    @Test
    public void whenInsertNewVacancy_ThenItInserted() {
        Date jobDate = new GregorianCalendar(2018, Calendar.APRIL, 2).getTime();
        Vacancy oracleVacancy = new Vacancy(1, "www.somewhere.org", "Java developer",
                "from 3000 usd", "Oracle", "Redwood City, California, USA", jobDate);

        vacancyDao.insert(oracleVacancy);
        assertNotNull(vacancyDao.getAll());
    }

    /**
     * Метод тестирует получение всех вакансий.
     */
    @Test
    public void whenGetAll_ThenGetAllVacancies() {
        List<Vacancy> vacancyList = new ArrayList<>();
        Date date = new GregorianCalendar(2018, Calendar.APRIL, 1).getTime();
        Vacancy microsoftVacancy = new Vacancy(1, "www.somewhere.org", "C# developer",
                "from 3000 usd", "Microsoft", "Los Angele's, California, USA", date);
        vacancyList.add(microsoftVacancy);

        vacancyDao.insert(microsoftVacancy);
        List<Vacancy> result = vacancyDao.getAll();

        assertArrayEquals(vacancyList.toArray(), result.toArray());
    }
}

/**
 * Соединение с тестовой БД.
 */
class ConnectionFactory {
    public ConnectionFactory() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
    }
}