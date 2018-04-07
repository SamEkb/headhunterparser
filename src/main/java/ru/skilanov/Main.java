package ru.skilanov;


import ru.skilanov.database.ConnectionFactory;
import ru.skilanov.database.VacancyDaoImpl;
import ru.skilanov.model.Vacancy;
import ru.skilanov.parsers.HeadHunterPageReaderImpl;
import ru.skilanov.parsers.HeadHunterParserImpl;

/**
 * Класс для запуска.
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        HeadHunterPageReaderImpl parsConnection = new HeadHunterPageReaderImpl();
        ConnectionFactory factory = new ConnectionFactory();
        VacancyDaoImpl jd = new VacancyDaoImpl(factory);

        jd.deleteAll();

        HeadHunterParserImpl parser = new HeadHunterParserImpl();

        for (int i = 0; i < 80; i++) {
            String page = parsConnection.readPage(i);
            for (Vacancy vacancy : parser.parseVacations(page)) {
                jd.insert(vacancy);
            }
        }

        for (Vacancy vacancy : jd.findByCompanyName("ANCOR FinTech")) {
            System.out.println(vacancy);
        }
    }
}
