package ru.skilanov.parsers;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Класс тестирует конвертацию даты.
 */
public class DateConverterTest {
    private static final String STRING_DATE = "1 апреля";
    /**
     * Конвертер даты.
     */
    private DateConverter dateConverter;

    /**
     * Метод инициализации перед тестами.
     */
    @Before
    public void setUp() {
        dateConverter = new DateConverter();
    }

    /**
     * Метод тестирует конвертацию даты.
     */
    @Test
    public void whenConvertStringThenReturnRightDate() {
        Date result = dateConverter.convertDate(STRING_DATE);

        Date expectedDate = new GregorianCalendar(2018, Calendar.APRIL, 1).getTime();

        assertThat(expectedDate, is(result));
    }

    /**
     * Метод тестирует неправильный формат даты.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void whenWeConvertWrongFormatThenReturnException() {
        String date = "1апреля";
        dateConverter.convertDate(date);
    }
}
