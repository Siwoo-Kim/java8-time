package com.siwoo.java8time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    @Test
    public void test() {
        TimeHelper.display(LocalDate.now());
        TimeHelper.display(LocalTime.now());
        TimeHelper.display(LocalDateTime.now());
    }

}
