package ru.skilanov.io.database;

import ru.skilanov.io.exceptions.DaoException;
import ru.skilanov.io.model.Vacancy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacancyDaoImpl implements VacancyDao {

    private static final String FIND_BY_COMPANY_NAME = "SELECT * FROM job WHERE company_name=?";
    private static final String FIND_BY_ID = "SELECT * FROM job WHERE id=?";
    private static final String DELETE_ALL = "DELETE FROM job";
    private static final String DELETE_BY_ID = "DELETE FROM job WHERE id=?";
    private static final String GET_ALL = "SELECT * FROM job";
    private static final String INSERT = "INSERT INTO job (url, title, salary, company_name, location, create_date)" +
            " VALUES (?,?,?,?,?,?)";
    private Connection connection;

    public void openConnection() {
        try {
            connection = ConnectionPoolFactory.getInstance().getConnection();
        } catch (SQLException sqle) {
            throw new DaoException("Method openConnection has thrown an exception", sqle);
        }

    }

    public void closeConnection() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException sqle) {
            throw new DaoException("Method closeConnection has thrown an exception", sqle);
        }
    }

    /**
     * Ввод данных в балицу.
     *
     * @param vacancy Job
     */
    @Override
    public void insert(Vacancy vacancy) {
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, vacancy.getUrl());
            ps.setString(2, vacancy.getTitle());
            ps.setString(3, vacancy.getSalary());
            ps.setString(4, vacancy.getCompanyName());
            ps.setString(5, vacancy.getLocation());
            ps.setDate(6, new Date(vacancy.getDate().getTime()));
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
    public List<Vacancy> getAll() {
        List<Vacancy> jobsList = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(GET_ALL);
            while (rs.next()) {
                jobsList.add(new Vacancy(rs.getString(Vacancy.COLUMN_URL),
                        rs.getString(Vacancy.COLUMN_TITLE),
                        rs.getString(Vacancy.COLUMN_SALARY),
                        rs.getString(Vacancy.COLUMN_COMPANY_NAME),
                        rs.getString(Vacancy.COLUMN_LOCATION),
                        rs.getTimestamp(Vacancy.COLUMN_CREATE_DATE)));
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
    public List<Vacancy> findByCompanyName(String name) {
        List<Vacancy> jobsList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(FIND_BY_COMPANY_NAME);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                jobsList.add(new Vacancy(rs.getString(Vacancy.COLUMN_URL),
                        rs.getString(Vacancy.COLUMN_TITLE),
                        rs.getString(Vacancy.COLUMN_SALARY),
                        rs.getString(Vacancy.COLUMN_COMPANY_NAME),
                        rs.getString(Vacancy.COLUMN_LOCATION),
                        rs.getTimestamp(Vacancy.COLUMN_CREATE_DATE)));
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
    public Vacancy findById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return new Vacancy(rs.getString(Vacancy.COLUMN_URL),
                        rs.getString(Vacancy.COLUMN_TITLE),
                        rs.getString(Vacancy.COLUMN_SALARY),
                        rs.getString(Vacancy.COLUMN_COMPANY_NAME),
                        rs.getString(Vacancy.COLUMN_LOCATION),
                        rs.getTimestamp(Vacancy.COLUMN_CREATE_DATE));
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
        try {
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
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_ALL);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new DaoException("Method deleteAll has thrown an exception", sqle);
        }
    }
}
