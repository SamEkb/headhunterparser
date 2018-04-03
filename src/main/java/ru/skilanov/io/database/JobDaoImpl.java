package ru.skilanov.io.database;

import ru.skilanov.io.exceptions.DaoException;
import ru.skilanov.io.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {

    private static final String COLUMN_URL = "url";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SALARY = "salary";
    private static final String COLUMN_COMPANY_NAME = "company_name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_CREATE_DATE = "create_date";
    private static final String FIND_BY_COMPANY_NAME = "SELECT * FROM job WHERE company_name=?";
    private static final String FIND_BY_ID = "SELECT * FROM job WHERE id=?";
    private static final String DELETE_ALL = "DELETE FROM job";
    private static final String DELETE_BY_ID = "DELETE FROM job WHERE id=?";
    private static final String GET_ALL = "SELECT * FROM job";
    private static final String INSERT = "INSERT INTO job (url, title, salary, company_name, location, create_date)" +
            " VALUES (?,?,?,?,?,?)";
    private static final String CREATE_TABLE_IF_NOT_EXIST = "CREATE TABLE IF NOT EXISTS job " +
            "(id SERIAL PRIMARY KEY NOT NULL," +
            "url VARCHAR(255)," +
            "title VARCHAR(255)," +
            "salary VARCHAR(255)," +
            "company_name VARCHAR(255)," +
            "location VARCHAR(255)," +
            "create_date TIMESTAMP)";
    private ConnectionFactory connectionFactory;

    public JobDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Создание таблицы, если она не существует.
     */
    @Override
    public void create() {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    CREATE_TABLE_IF_NOT_EXIST);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new DaoException("Method create has thrown an exception", sqle);
        }
    }

    /**
     * Ввод данных в балицу.
     *
     * @param job Job
     */
    @Override
    public void insert(Job job) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, job.getUrl());
            ps.setString(2, job.getTitle());
            ps.setString(3, job.getSalary());
            ps.setString(4, job.getCompanyName());
            ps.setString(5, job.getLocation());
            ps.setDate(6, new Date(job.getDate().getTime()));
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new DaoException("Method insert has thrown an exception", sqle);
        }
    }

    /**
     * Получение всех записей.
     *
     * @return List
     */
    @Override
    public List<Job> getAll() {
        List<Job> jobsList = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(GET_ALL);
            while (rs.next()) {
                jobsList.add(new Job(rs.getString(COLUMN_URL),
                        rs.getString(COLUMN_TITLE),
                        rs.getString(COLUMN_SALARY),
                        rs.getString(COLUMN_COMPANY_NAME),
                        rs.getString(COLUMN_LOCATION),
                        rs.getTimestamp(COLUMN_CREATE_DATE)));
            }
        } catch (SQLException sqle) {
            throw new DaoException("Method getAll has thrown an exception", sqle);
        }
        return jobsList;
    }

    /**
     * Получение всех вакансий по наименованию компании.
     *
     * @param name String
     * @return List
     */
    @Override
    public List<Job> findByCompanyName(String name) {
        List<Job> jobsList = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(FIND_BY_COMPANY_NAME);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                jobsList.add(new Job(rs.getString(COLUMN_URL),
                        rs.getString(COLUMN_TITLE),
                        rs.getString(COLUMN_SALARY),
                        rs.getString(COLUMN_COMPANY_NAME),
                        rs.getString(COLUMN_LOCATION),
                        rs.getTimestamp(COLUMN_CREATE_DATE)));
            }
        } catch (SQLException sqle) {
            throw new DaoException("Method findByCompanyName has thrown an exception", sqle);
        }
        return jobsList;
    }

    /**
     * Поиск вакансии по id.
     *
     * @param id int
     * @return Job
     */
    @Override
    public Job findById(int id) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return new Job(rs.getString(COLUMN_URL),
                        rs.getString(COLUMN_TITLE),
                        rs.getString(COLUMN_SALARY),
                        rs.getString(COLUMN_COMPANY_NAME),
                        rs.getString(COLUMN_LOCATION),
                        rs.getTimestamp(COLUMN_CREATE_DATE));
            }
        } catch (SQLException sqle) {
            throw new DaoException("Method findById has thrown an exception", sqle);
        }
        return null;
    }

    /**
     * Удаление вакансии по id.
     *
     * @param id int
     */
    @Override
    public void deleteById(int id) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new DaoException("Method deleteById has thrown an exception", sqle);
        }
    }

    /**
     * Удаление всех вакансий.
     */
    @Override
    public void deleteAll() {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(DELETE_ALL);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new DaoException("Method deleteAll has thrown an exception", sqle);
        }
    }
}
