package ru.skilanov.io;


import ru.skilanov.io.database.VacancyDaoImpl;
import ru.skilanov.io.model.Vacancy;
import ru.skilanov.io.parsers.HeadHunterPageReaderImpl;
import ru.skilanov.io.parsers.HeadHunterParserImpl;

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
        VacancyDaoImpl jd = new VacancyDaoImpl();

        jd.openConnection();

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

        jd.closeConnection();
    }
}
