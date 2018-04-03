package ru.skilanov.io.parsers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.skilanov.io.model.Job;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс реализация интерйеса HtmlParser, который парсит вакансии.
 */
public class HtmlParserImpl implements HtmlParser {
    /**
     * Запрос на получение вакансии.
     */
    private static final String ROOT_QUERY = "div[class=\"vacancy-serp-item\"]";

    /**
     * Запрос на получение наименования и ссылки на вакансию.
     */
    private static final String URL_AND_TITLE_QUERY = "div[class=\"vacancy-serp-item__row\"] " +
            "> div[class=\"vacancy-serp-item__info\"] " +
            "> div[class=\"vacancy-serp-item__title\"] " +
            "> a[class=\"bloko-link HH-LinkModifier\"]";

    /**
     * Аттрибут ссылки.
     */
    private static final String REF_ATTR = "abs:href";

    /**
     * Запрос на получение даты публикации вакансии.
     */
    private static final String DATE_QUERY = "div[class=\"vacancy-serp-item__row vacancy-serp-item__row_controls\"] " +
            "> span[class=\"vacancy-serp-item__controls-item vacancy-serp-item__controls-item_last\"] " +
            "> span[class=\"vacancy-serp-item__publication-date\"]";

    /**
     * Запрос на получение места нахождения.
     */
    private static final String LOCATION_QUERY = "div[class=\"vacancy-serp-item__row\"] " +
            "> div[class=\"vacancy-serp-item__info\"] " +
            "> div[class=\"vacancy-serp-item__meta-info\"] " +
            "> span[class=\"vacancy-serp-item__meta-info\"]";

    /**
     * Запрос на получение наименования компании.
     */
    private static final String COMPANY_NAME_QUERY = "div[class=\"vacancy-serp-item__row\"] " +
            "> div[class=\"vacancy-serp-item__info\"] " +
            "> div[class=\"vacancy-serp-item__meta-info\"] " +
            "> a[class=\"bloko-link bloko-link_secondary\"]";

    /**
     * Запрос на получение заработной платы.
     */
    private static final String SALARY_QUERY = "div[class=\"vacancy-serp-item__row\"] " +
            "> div[class=\"vacancy-serp-item__sidebar\"] " +
            "> div[class=\"vacancy-serp-item__sidebar\"] " +
            "> div[class=\"vacancy-serp-item__compensation\"]";

    /**
     * Счетчик страниц.
     */
    public int pageCounter = 0;

    /**
     * Соединение с сайтом.
     */
    private WebConnectionFactory webConnectionFactory;

    /**
     * Конструктор.
     *
     * @param webConnectionFactory WebConnectionFactory
     */
    public HtmlParserImpl(WebConnectionFactory webConnectionFactory) {
        this.webConnectionFactory = webConnectionFactory;
    }

    /**
     * Метод возвращает лист всех вакансий в заголовках которых указано java
     * @return LinkedHashSet
     */
    public Set<Job> getAllJavaJobs() {
        Set<Job> jobList = new LinkedHashSet<>();
        pageCounter++;
        for (Element element : getPages()) {
            String jobTitle = getTitle(element).toLowerCase();
            if (jobTitle.contains("java") && !jobTitle.contains("javascript") && !jobTitle.contains("java script")) {
                jobList.add(createJob(element));
            }
        }
        return jobList;
    }

    /**
     * Метод возвращает созданную вакансию.
     *
     * @param element Element
     * @return созданная вакансия Job
     */
    public Job createJob(Element element) {
        return new Job(getUrl(element),
                getTitle(element),
                getSalary(element),
                getCompanyName(element),
                getLocation(element),
                getDate(element));
    }

    /**
     * Метод возвращает список элементов.
     *
     * @return список элементов для парсинга.
     */
    public Elements getPages() {
        return webConnectionFactory.getConnection(pageCounter).select(ROOT_QUERY);
    }

    /**
     * Метод возвращает ссылку на вакансию.
     *
     * @param element Element url
     * @return ссылку в виде строки.
     */
    public String getUrl(Element element) {
        return element.select(URL_AND_TITLE_QUERY).attr(REF_ATTR);
    }

    /**
     * Метод возвращает наименования вакансии.
     *
     * @param element Element title
     * @return наименование вакансии в виде строки.
     */
    public String getTitle(Element element) {
        return element.select(URL_AND_TITLE_QUERY).text();
    }

    /**
     * Метод возвращает заработную плату.
     *
     * @param element Element salary
     * @return заработная плата в виде строки.
     */
    public String getSalary(Element element) {
        return element.select(SALARY_QUERY).text();
    }

    /**
     * Метод возвращает наименование компании.
     *
     * @param element Element companyName
     * @return наименование компании в виде строки.
     */
    public String getCompanyName(Element element) {
        return element.select(COMPANY_NAME_QUERY).text();
    }

    /**
     * Метод возвращает место нахождения.
     *
     * @param element Element location
     * @return место нахождения в виде строки.
     */
    public String getLocation(Element element) {
        return element.select(LOCATION_QUERY).text();
    }

    /**
     * Метод возвращает дату публикации.
     *
     * @param element Element date
     * @return дата публикации в виде даты.
     */
    public Date getDate(Element element) {
        String date = element.select(DATE_QUERY).text();
        DateConverter dc = new DateConverter();
        return dc.convertDate(date);
    }
}
