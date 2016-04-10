package team5.calendarproject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by edward on 4/1/16.
 */
public enum CalendarDates {
    JANUARY(31), FEBRUARY(28), MARCH(31), APRIL(30), MAY(31), JUNE(30), JULY(31),
    AUGUST(31), SEPTEMBER(30), OCTOBER(31), NOVEMBER(30), DECEMBER(31);

    private int daysInMonth;
    private GregorianCalendar cal;

    CalendarDates(int days) {
        cal = new GregorianCalendar();
        this.daysInMonth = days;
    }

    public String toString() {
        if(this == CalendarDates.JANUARY) {
            return "January";
        }
        if(this == CalendarDates.FEBRUARY) {
            return "February";
        }
        if(this == CalendarDates.MARCH) {
            return "March";
        }
        if(this == CalendarDates.APRIL) {
            return "April";
        }
        if(this == CalendarDates.MAY) {
            return "May";
        }
        if(this == CalendarDates.JUNE) {
            return "June";
        }
        if(this == CalendarDates.JULY) {
            return "July";
        }
        if(this == CalendarDates.AUGUST) {
            return "August";
        }
        if(this == CalendarDates.SEPTEMBER) {
            return "September";
        }
        if(this == CalendarDates.OCTOBER) {
            return "October";
        }
        if(this == CalendarDates.NOVEMBER) {
            return "November";
        }
        if(this == CalendarDates.DECEMBER) {
            return "December";
        }

        return "Error";
    }

    public int getNumberOfDays(int year){

        if(cal.isLeapYear(year) && this == CalendarDates.FEBRUARY) {
            return this.daysInMonth + 1;
        }

        return daysInMonth;
    }

}
