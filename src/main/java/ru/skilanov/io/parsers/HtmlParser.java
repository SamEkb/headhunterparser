package ru.skilanov.io.parsers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.skilanov.io.model.Job;

import java.util.Date;

/**
 * Интерфейс содержащий методы для парсинга вакансии при помощи jsoup.
 */
public interface HtmlParser {

    /**
     * Метод возвращает созданную вакансию.
     *
     * @param element Element
     * @return созданная вакансия Job
     */
    Job createJob(Element element);

    /**
     * Метод возвращает список элементов.
     *
     * @return список элементов для парсинга.
     */
    Elements getPages();

    /**
     * Метод возвращает ссылку на вакансию.
     *
     * @param element Element url
     * @return ссылку в виде строки.
     */
    String getUrl(Element element);

    /**
     * Метод возвращает наименования вакансии.
     *
     * @param element Element title
     * @return наименование вакансии в виде строки.
     */
    String getTitle(Element element);

    /**
     * Метод возвращает заработную плату.
     *
     * @param element Element salary
     * @return заработная плата в виде строки.
     */
    String getSalary(Element element);

    /**
     * Метод возвращает наименование компании.
     *
     * @param element Element companyName
     * @return наименование компании в виде строки.
     */
    String getCompanyName(Element element);

    /**
     * Метод возвращает место нахождения.
     *
     * @param element Element location
     * @return место нахождения в виде строки.
     */
    String getLocation(Element element);

    /**
     * Метод возвращает дату публикации.
     *
     * @param element Element date
     * @return дата публикации в виде даты.
     */
    Date getDate(Element element);
}
