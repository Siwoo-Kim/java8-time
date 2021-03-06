package com.siwoo.java8time;

import org.apache.tomcat.jni.Local;
import org.junit.Test;
import org.springframework.util.Assert;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TimeHelper {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd E'day'");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss 'seconds', SS 'm/s'");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd E'day' hh:MM:ss 'seconds', SS 'm/s'");

    public static void display(LocalDate date) {
        System.out.println("Date Formatted: " + dateFormatter.format(date) );
    }

    public static void display(LocalTime time) {
        System.out.println("Time Formatted: " + timeFormatter.format(time));
    }

    public static void display(LocalDateTime dateTime) {
        System.out.println("DateTime Formatted: " + dateTimeFormatter.format(dateTime));
    }

    public static void validateParseString(String string) {
        if(string != null && string.trim().length() > 0) {
            return;
        }
        throw new IllegalArgumentException("Not valid input " + string);
    }

    public static LocalDate parseDate(String string) {
        validateParseString(string);
        return LocalDate.parse(string);
    }

    public static LocalTime parseTime(String string) {
        validateParseString(string);
        return LocalTime.parse(string);
    }

    public static void withValidator(String field, String value, Object localDate) {
        validateParseString(value);
        validateParseString(field);
        Assert.notNull(localDate, "Value must not empty");
    }

    public static LocalDate with(String field, String value, LocalDate localDate) {
        withValidator(field, value, localDate);
        switch (field.toUpperCase()) {
            case "YEAR":
                return localDate.with(ChronoField.YEAR, Integer.parseInt(value));
            case "MONTH":
                return localDate.with(ChronoField.MONTH_OF_YEAR, Integer.parseInt(value));
            case "DATE":
                return localDate.with(ChronoField.DAY_OF_MONTH, Integer.parseInt(value));
            case "DAY" :
                return localDate.with(ChronoField.DAY_OF_WEEK, DayOfWeek.valueOf(value).getValue());
        }
        throw new IllegalArgumentException("Not know field " + field);
    }

    public static LocalTime with(String field, String value, LocalTime localTime) {
        withValidator(field, value, localTime);

        if(timeFormatter.format(localTime).split(":").length == 3) {
            switch (field.toUpperCase()) {
                case "HOUR":
                    return localTime.with(ChronoField.HOUR_OF_DAY, Integer.parseInt(value));
                case "MINUTE":
                    return localTime.with(ChronoField.MINUTE_OF_HOUR, Integer.parseInt(value));
                case "SECOND":
                    return localTime.with(ChronoField.SECOND_OF_MINUTE, Integer.parseInt(value));
            }
        }
        throw new IllegalArgumentException("Not know field " + field);
    }

    public enum Units {
        YEAR(ChronoUnit.YEARS, "년"),
        MONTH(ChronoUnit.MONTHS, "월"),
        DAY(ChronoUnit.DAYS, "일"),
        WEEK(ChronoUnit.WEEKS, "주"),
        HOUR(ChronoUnit.HOURS, "시"),
        MINUTE(ChronoUnit.MINUTES, "분"),
        SECOND(ChronoUnit.SECONDS, "초"),
        NANO(ChronoUnit.NANOS, "나노");
        private ChronoUnit unit;
        private String value;

        Units(ChronoUnit chronoUnit, String korean) {
            unit = chronoUnit;
            this.value = korean;
        }

        static Units of(String korean) {
            switch (korean) {
                case "년": return YEAR;
                case "월": return MONTH;
                case "일": return DAY;
                default: throw new UnsupportedOperationException("Not supported value " + korean);
            }
        }
    }
    public static LocalDate plus(String field, long value, LocalDate localDate) {
        ChronoUnit unit =  Units.of(field).unit;
        return localDate.plus(value, unit);

    }

    @Test
    public void test() {
        TimeHelper.display(LocalDate.now());
        TimeHelper.display(LocalTime.now());
        TimeHelper.display(LocalDateTime.now());

        assertNotNull(TimeHelper.parseDate("1989-03-04"));
        LocalDate localDate = LocalDate.now();
        localDate = TimeHelper.with("YEAR","1989", localDate);
        assertNotNull(localDate);
        assertThat(localDate.getYear(),is(1989));
        System.out.println(localDate);

        localDate = TimeHelper.with("MONTH","03", localDate);
        assertNotNull(localDate);
        assertThat(localDate.getMonth(),is(Month.MARCH));
        System.out.println(localDate);

        localDate = TimeHelper.with("DATE","04", localDate);
        assertNotNull(localDate);
        assertThat(localDate.getDayOfMonth(),is(4));
        System.out.println(localDate);


        localDate = TimeHelper.with("DAY","FRIDAY", localDate);
        assertNotNull(localDate);
        assertThat(localDate.getDayOfWeek(),is(DayOfWeek.FRIDAY));
        System.out.println(localDate);

        LocalTime localTime = LocalTime.now();
        localTime = TimeHelper.with("HOUR", "23", localTime);
        assertEquals(localTime.get(ChronoField.HOUR_OF_DAY), 23);
        System.out.println(localTime);

        localTime = TimeHelper.with("MINUTE", "59", localTime);
        assertEquals(localTime.get(ChronoField.MINUTE_OF_HOUR), 59);
        System.out.println(localTime);

        localTime = TimeHelper.with("SECOND", "59", localTime);
        assertEquals(localTime.get(ChronoField.SECOND_OF_MINUTE), 59);
        System.out.println(localTime);

        localDate = LocalDate.now();
        localDate = TimeHelper.plus("년", -1, localDate);
        assertEquals(localDate.getYear(), 2017);
        System.out.println(localDate);

        localDate = TimeHelper.plus("월", 2, localDate);
        assertEquals(localDate.getMonth(), Month.of(10));
        System.out.println(localDate);

        localDate = TimeHelper.plus("일", -2, localDate);
        assertEquals(localDate.getDayOfMonth(), 1);
        System.out.println(localDate);


    }

}
