package ru.skilanov.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import ru.skilanov.model.Vacancy;

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
public class HeadHunterParserImplTest {

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
    private HeadHunterParserImpl parser;

    /**
     * hh.html
     */
    private String headhuterHTML;

    /**
     * Метод инициализации перед тестами.
     */
    @Before
    public void setUp() throws IOException, URISyntaxException {
        parser = new HeadHunterParserImpl();

        String simpleHTML = new String(Files.readAllBytes(Paths.get(getClass().getResource("/input.html").toURI())));
        headhuterHTML = new String(Files.readAllBytes(Paths.get(getClass().getResource("/hh.html").toURI())));

        converter = new DateConverter();

        Document simplePageDocument = Jsoup.parse(simpleHTML, "UTF-8");

        spElements = simplePageDocument.select("body");
    }

    /**
     * Метод тестирует парсинг вакансии с html страницы и сравнивает вакансию разобранную со страницы с созданной
     * вакансией. При положительном результате метод assertTrue возвращает true.
     */
    @Test
    public void whenCompareGotJobAndCreatedJob_ThenReturnTrue() {
        Set<Vacancy> result = new LinkedHashSet<>();

        Set<Vacancy> expectedVacancy = new LinkedHashSet<>();
        Date expectedDate = new GregorianCalendar(2018, Calendar.APRIL, 1).getTime();
        expectedVacancy.add(new Vacancy("www.somewhere.org", "Java developer",
                "from 3000 usd", "Oracle", "Redwood City, California, USA", expectedDate));

        for (Element element : spElements) {
            String urlElement = element.select("div[class=\"title-body\"] > a[class=\"title\"]").attr("href");
            String titleElement = element.select("div[class=\"title-body\"] > a[class=\"title\"]").text();
            String salaryElement = element.select("div[class=\"salary\"] > a[class=\"salary-amount\"]").text();
            String companyNameElement = element.select("div[class=\"company\"] > a[class=\"company-name\"]").text();
            String locationElement = element.select("div[class=\"location\"] > a[class=\"city-name\"]").text();
            String dateElement = element.select("div[class=\"date\"] > a[class=\"create-date\"]").text();
            Date resultDate = converter.convertDate(dateElement);
            result.add(new Vacancy(urlElement,
                    titleElement,
                    salaryElement,
                    companyNameElement,
                    locationElement,
                    resultDate));
        }

        assertTrue(result.equals(expectedVacancy));
    }

    /**
     * Метод тестирует разбор всех вакансий со страницы.
     */
    @Test
    public void whenCreateNewJob_ThenReturnRightListOfJobs() {
        Set<Vacancy> result = parser.parseVacations(headhuterHTML);

        Set<Vacancy> expected = new LinkedHashSet<>();
        Date jobDate = new GregorianCalendar(2018, Calendar.APRIL, 2).getTime();
        expected.add(new Vacancy("https://ekaterinburg.hh.ru/vacancy/2", "Java разработчик",
                "30 700-30 700 руб.", "ПАО КБ «Уральский банк реконструкции и развития» (УБРиР)",
                "Екатеринбург", jobDate));

        assertThat(result, is(expected));
    }
}
