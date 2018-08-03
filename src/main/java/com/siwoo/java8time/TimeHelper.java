package com.siwoo.java8time;

import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertNotNull;

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

    @Test
    public void test() {
        TimeHelper.display(LocalDate.now());
        TimeHelper.display(LocalTime.now());
        TimeHelper.display(LocalDateTime.now());

        assertNotNull(TimeHelper.parseDate("1989-03-04"));
    }

}
