package ru.skilanov.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Класс конвертирует дату из строки.
 */
public class DateConverter {

    /**
     * Константа регулярное выражение.
     */
    private static final String REGEX = "[\\u00A0\\s]+";

    /**
     * Метод преобразовывает строку в дату.
     *
     * @param date в виде строки
     * @return преобразованную дату.
     */
    public Date convertDate(String date) {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String str = String.format("%s %s %s", getDay(date), getMonth(date), year);
        DateFormat format = new SimpleDateFormat("d MMMM yyyy");
        try {
            return format.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("Date parsing exception ", e);
        }
    }

    /**
     * Метод разбивает строку по пробелу и служит для получения числа месяца.
     *
     * @param date строка даты
     * @return String первый элемент массива.
     */
    private String getDay(String date) {
        return date.split(REGEX)[0];
    }

    /**
     * Метод разбивает строку по пробелу и служит для получения месяца.
     *
     * @param date строка даты
     * @return String второй элемент массива.
     */
    private String getMonth(String date) {
        return date.split(REGEX)[1];
    }
}
