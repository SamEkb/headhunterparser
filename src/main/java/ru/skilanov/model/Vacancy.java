package ru.skilanov.model;

import java.util.Date;
import java.util.Objects;

/**
 * Класс модель вакансии.
 */
public class Vacancy {

    /**
     * Константа url.
     */
    public static final String COLUMN_URL = "url";

    /**
     * Константа title.
     */
    public static final String COLUMN_TITLE = "title";

    /**
     * Константа salary.
     */
    public static final String COLUMN_SALARY = "salary";

    /**
     * Константа company name.
     */
    public static final String COLUMN_COMPANY_NAME = "company_name";

    /**
     * Константа location.
     */
    public static final String COLUMN_LOCATION = "location";

    /**
     * Константа create date.
     */
    public static final String COLUMN_CREATE_DATE = "create_date";

    /**
     * Идентефикатор вакансии.
     */
    private int id;

    /**
     * Ссылка на вакансию.
     */
    private String url;
    /**
     * Наименование вакансии.
     */
    private String title;
    /**
     * Заработная плата.
     */
    private String salary;
    /**
     * Наименование компании
     */
    private String companyName;
    /**
     * Место нахождения.
     */
    private String location;
    /**
     * Дата публикации вакансии.
     */
    private Date date;

    public Vacancy(int id, String url, String title, String salary, String companyName, String location, Date date) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.salary = salary;
        this.companyName = companyName;
        this.location = location;
        this.date = date;
    }

    /**
     * Конструктор инициализирующий вакансию.
     *
     * @param url         String
     * @param title       String
     * @param salary      String
     * @param companyName String
     * @param location    String
     * @param date        Date
     */
    public Vacancy(String url, String title, String salary, String companyName, String location, Date date) {
        this.url = url;
        this.title = title;
        this.salary = salary;
        this.companyName = companyName;
        this.location = location;
        this.date = date;
    }

    /**
     * Геттер для id.
     *
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Геттер для ссылки.
     *
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * Геттер для наименования.
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Геттер для заработной платы.
     *
     * @return String
     */
    public String getSalary() {
        return salary;
    }

    /**
     * Геттер для наименования компании.
     *
     * @return String
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Геттер для места нахождения.
     *
     * @return String
     */
    public String getLocation() {
        return location;
    }

    /**
     * Геттер для даты публикации.
     *
     * @return String
     */
    public Date getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", salary='" + salary + '\'' +
                ", companyName='" + companyName + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id &&
                Objects.equals(url, vacancy.url) &&
                Objects.equals(title, vacancy.title) &&
                Objects.equals(salary, vacancy.salary) &&
                Objects.equals(companyName, vacancy.companyName) &&
                Objects.equals(location, vacancy.location) &&
                Objects.equals(date, vacancy.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, url, title, salary, companyName, location, date);
    }
}
