package ru.skilanov.io.model;

import java.util.Date;
import java.util.Objects;

/**
 * Класс модель вакансии.
 */
public class Job {
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

    public Job(int id, String url, String title, String salary, String companyName, String location, Date date) {
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
    public Job(String url, String title, String salary, String companyName, String location, Date date) {
        this.url = url;
        this.title = title;
        this.salary = salary;
        this.companyName = companyName;
        this.location = location;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        Job job = (Job) o;
        return id == job.id &&
                Objects.equals(url, job.url) &&
                Objects.equals(title, job.title) &&
                Objects.equals(salary, job.salary) &&
                Objects.equals(companyName, job.companyName) &&
                Objects.equals(location, job.location) &&
                Objects.equals(date, job.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, url, title, salary, companyName, location, date);
    }
}
