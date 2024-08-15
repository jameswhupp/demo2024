package com.jameswhupp.demo.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

@Service
public class RentalCalendarService {

    public static boolean isWeekEnd(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static boolean isWeekDay(LocalDate date) {
        return !isWeekEnd(date);
    }

    public static boolean isHoliday(LocalDate date) {
        return isJuly4thObserved(date) || isLaborDayObserved(date);
    }

    public static boolean isJuly4thObserved(LocalDate date) {
        if (date.getMonth() == Month.JULY) {
            LocalDate july4thObservedDate = getJuly4thObservedDate(date.getYear());
            return date.equals(july4thObservedDate);
        }
        return false;
    }

    public static LocalDate getJuly4thObservedDate(int calendarYear) {
        LocalDate july4thObservedDate = LocalDate.of(calendarYear, Month.JULY, 4);
        if (july4thObservedDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            july4thObservedDate = july4thObservedDate.minusDays(1);
        } else if (july4thObservedDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            july4thObservedDate = july4thObservedDate.plusDays(1);
        }
        return july4thObservedDate;
    }

    public static boolean isLaborDayObserved(LocalDate date) {
        if (date.getMonth() == Month.SEPTEMBER) {
            LocalDate laborDay = LocalDate.of(date.getYear(), Month.SEPTEMBER, 1);
            laborDay = laborDay.with(firstInMonth(DayOfWeek.MONDAY));
            return date.isEqual(laborDay);
        }
        return false;
    }
}
