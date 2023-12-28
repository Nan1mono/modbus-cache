package com.tecpie.modbus.toolkit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private DateTimeUtil() {

    }

    public static String format(LocalDateTime localDateTime, String style) {
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern(style);
        return dtf2.format(localDateTime);
    }

    public static String format(LocalTime localTime, String style) {
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern(style);
        return dtf2.format(localTime);
    }

    public static String format(LocalDate localDate, String style) {
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern(style);
        return dtf2.format(localDate);
    }

}
