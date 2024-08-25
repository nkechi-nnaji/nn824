package org.example.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class HolidayUtil {
    public static LocalDate getIndependenceDay(int year) {
        LocalDate july4th = LocalDate.of(year, 7, 4);
        if (july4th.getDayOfWeek().getValue() == 6) {
            return july4th.minusDays(1); // Observed on Friday
        } else if (july4th.getDayOfWeek().getValue() == 7) {
            return july4th.plusDays(1); // Observed on Monday
        }
        return july4th;
    }

    public static LocalDate getLaborDay(int year) {
        return LocalDate.of(year, 9, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
    }
}
