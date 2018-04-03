package ru.skilanov.io.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import ru.skilanov.io.model.Job;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Класс тестирует парсинг страницы.
 */
public class HtmlParserImplTest {

    /**
     * Список элементов страницы hh.html.
     */
    private Elements hhElements;

    /**
     * Список элиментов страницы input.html.
     */

    private Elements spElements;
    /**
     * Конвертер даты.
     */

    private DateConverter converter;
    /**
     * Парсер.
     */
    private HtmlParserImpl parser;

    /**
     * Соединение со страницей.
     */
    @Mock
    private WebConnectionFactory webConnectionFactory;

    /**
     * Метод инициализации перед тестами.
     */
    @Before
    public void setUp() throws IOException, URISyntaxException {
        parser = new HtmlParserImpl(webConnectionFactory);

        String simpleHTML = new String(Files.readAllBytes(Paths.get(getClass().getResource("/input.html").toURI())));
        String headhuterHTML = new String(Files.readAllBytes(Paths.get(getClass().getResource("/hh.html").toURI())));

        converter = new DateConverter();

        Document simplePageDocument = Jsoup.parse(simpleHTML, "UTF-8");
        Document hhPageDocument = Jsoup.parse(headhuterHTML, "UTF-8");

        spElements = simplePageDocument.select("body");
        hhElements = hhPageDocument.select("div[class=\"vacancy-serp-item\"]");
    }

    /**
     * Метод тестирует парсинг вакансии с html страницы и сравнивает вакансию разобранную со страницы с созданной
     * вакансией. При положительном результате метод assertTrue возвращает true.
     */
    @Test
    public void whenWeCompareGotJobAndCreatedJobThenReturnTrue() {
        Set<Job> result = new LinkedHashSet<>();

        Set<Job> expectedJob = new LinkedHashSet<>();
        Date expectedDate = new GregorianCalendar(2018, Calendar.APRIL, 1).getTime();
        expectedJob.add(new Job("www.somewhere.org", "Java developer",
                "from 3000 usd", "Oracle", "Redwood City, California, USA", expectedDate));

        for (Element element : spElements) {
            String urlElement = element.select("div[class=\"title-body\"] > a[class=\"title\"]").attr("href");
            String titleElement = element.select("div[class=\"title-body\"] > a[class=\"title\"]").text();
            String salaryElement = element.select("div[class=\"salary\"] > a[class=\"salary-amount\"]").text();
            String companyNameElement = element.select("div[class=\"company\"] > a[class=\"company-name\"]").text();
            String locationElement = element.select("div[class=\"location\"] > a[class=\"city-name\"]").text();
            String dateElement = element.select("div[class=\"date\"] > a[class=\"create-date\"]").text();
            Date resultDate = converter.convertDate(dateElement);
            result.add(new Job(urlElement,
                    titleElement,
                    salaryElement,
                    companyNameElement,
                    locationElement,
                    resultDate));
        }

        assertTrue(result.equals(expectedJob));
    }

    /**
     * Метод тестирует разбор url.
     */
    @Test
    public void whenWeParseUrlThenReturnListOfUrl() {
        List<String> result = new ArrayList<>();
        for (Element element : hhElements) {
            result.add(parser.getUrl(element));
        }

        List<String> expected = new ArrayList<>();
        expected.add("https://ekaterinburg.hh.ru/vacancy/3");
        expected.add("https://ekaterinburg.hh.ru/vacancy/1");
        expected.add("https://ekaterinburg.hh.ru/vacancy/2");

        assertThat(result, is(expected));
    }

    /**
     * Метод тестирует разбор title.
     */
    @Test
    public void whenWeParseJobTitleThenReturnRightListOfTitles() {
        List<String> result = new ArrayList<>();
        for (Element hhElement : hhElements) {
            result.add(parser.getTitle(hhElement));
        }

        List<String> expected = new ArrayList<>();
        expected.add("Руководитель отдела разработки ПО");
        expected.add("Старший программист Javascript");
        expected.add("Java разработчик");

        assertThat(result, is(expected));
    }

    /**
     * Метод тестирует разбор salary.
     */
    @Test
    public void whenWeParseSalaryThenReturnRightListOfSalaries() {
        List<String> result = new ArrayList<>();

        for (Element hhElement : hhElements) {
            result.add(parser.getSalary(hhElement));
        }

        List<String> expected = new ArrayList<>();
        expected.add("");
        expected.add("");
        expected.add("30 700-30 700 руб.");

        assertThat(result, is(expected));
    }

    /**
     * Метод тестирует разбор companyName.
     */
    @Test
    public void whenWeParseCompanyNameThenReturnRightListOfCompanies() {
        List<String> result = new ArrayList<>();
        for (Element hhElement : hhElements) {
            result.add(parser.getCompanyName(hhElement));
        }

        List<String> expected = new ArrayList<>();
        expected.add("It tech");
        expected.add("Solutions");
        expected.add("ПАО КБ «Уральский банк реконструкции и развития» (УБРиР)");

        assertThat(result, is(expected));
    }

    /**
     * Метод тестирует разбор location.
     */
    @Test
    public void whenWeParseLocationThenReturnRightListOfLocations() {
        List<String> result = new ArrayList<>();
        for (Element hhElement : hhElements) {
            result.add(parser.getLocation(hhElement));
        }

        List<String> expected = new ArrayList<>();
        expected.add("Екатеринбург");
        expected.add("Екатеринбург");
        expected.add("Екатеринбург");

        assertThat(result, is(expected));
    }

    /**
     * Метод тестирует разбор date.
     */
    @Test
    public void whenWeParseDateThenReturnRightListOfDates() {
        List<Date> result = new ArrayList<>();
        for (Element hhElement : hhElements) {
            result.add(parser.getDate(hhElement));
        }

        List<Date> expected = new ArrayList<>();
        Date jobDate = new GregorianCalendar(2018, Calendar.APRIL, 2).getTime();
        expected.add(jobDate);
        expected.add(jobDate);
        expected.add(jobDate);

        assertThat(result, is(expected));
    }

    /**
     * Метод тестирует разбор всех вакансий со страницы.
     */
    @Test
    public void whenWeCreateNewJobThenReturnRightListOfJobs() {
        List<Job> result = new ArrayList<>();
        for (Element hhElement : hhElements) {
            result.add(parser.createJob(hhElement));
        }

        List<Job> expected = new ArrayList<>();
        Date jobDate = new GregorianCalendar(2018, Calendar.APRIL, 2).getTime();
        expected.add(new Job("https://ekaterinburg.hh.ru/vacancy/3", "Руководитель отдела разработки ПО",
                "", "It tech", "Екатеринбург", jobDate));
        expected.add(new Job("https://ekaterinburg.hh.ru/vacancy/1", "Старший программист Javascript",
                "", "Solutions", "Екатеринбург", jobDate));
        expected.add(new Job("https://ekaterinburg.hh.ru/vacancy/2", "Java разработчик",
                "30 700-30 700 руб.", "ПАО КБ «Уральский банк реконструкции и развития» (УБРиР)",
                "Екатеринбург", jobDate));

        assertThat(result, is(expected));
    }

    /**
     * Метод тестирует фильтрацию вакансий по наименованию.
     */
    @Test
    public void whenWeFilterJobByTitleTheReturnRightListOfJobs() {
        List<Job> result = new ArrayList<>();
        Date jobDate = new GregorianCalendar(2018, Calendar.APRIL, 2).getTime();

        for (Element hhElement : hhElements) {
            String jobTitle = parser.getTitle(hhElement).toLowerCase();
            if (jobTitle.contains("java") && !jobTitle.contains("javascript") && !jobTitle.contains("java script")) {
                result.add(parser.createJob(hhElement));
            }
        }

        List<Job> expected = new ArrayList<>();
        expected.add(new Job("https://ekaterinburg.hh.ru/vacancy/2", "Java разработчик",
                "30 700-30 700 руб.", "ПАО КБ «Уральский банк реконструкции и развития» (УБРиР)",
                "Екатеринбург", jobDate));

        assertThat(result, is(expected));
    }
}
