package ru.skilanov.io.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Класс для соединения со страницей.
 */
class WebConnectionFactory {
    /**
     * Ссылка на сайт к которым происходит соединение.
     */
    private static final String HH_URL = "https://ekaterinburg.hh.ru/search/vacancy?enable_snippets=true&clusters=" +
            "true&order_by=publication_time&specialization=1&area=1261&page=";
    /**
     * Используемый браузер.
     */
    private static final String AGENT = "Google Chrome";

    /**
     * Метод соединения с сайтом в котором в переменной url происходит конкатинация строк для
     * смены страницы на следующую.
     *
     * @param count int для смены страницы
     * @return html документ
     */
    protected Document getConnection(int count) {
        String url = HH_URL.concat(String.valueOf(count));
        try {
            return Jsoup
                    .connect(url)
                    .userAgent(AGENT)
                    .get();
        } catch (IOException e) {
            throw new RuntimeException("Get headhunter connection exception ", e);
        }
    }
}
