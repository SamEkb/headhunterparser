package ru.skilanov.io.model;

import java.util.Date;

/**
 * Класс модель вакансии.
 */
public class Job {
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

    /**
     * Геттер.
     *
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * Геттер.
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Геттер.
     *
     * @return String
     */
    public String getSalary() {
        return salary;
    }

    /**
     * Геттер.
     *
     * @return String
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Геттер.
     *
     * @return String
     */
    public String getLocation() {
        return location;
    }

    /**
     * Геттер.
     *
     * @return String
     */
    public Date getDate() {
        return date;
    }

    /**
     * Метод преобразующий объект в строку.
     *
     * @return преобразованная в String вакансия.
     */
    @Override
    public String toString() {
        return "Job{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", salary='" + salary + '\'' +
                ", companyName='" + companyName + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                '}';
    }
}
