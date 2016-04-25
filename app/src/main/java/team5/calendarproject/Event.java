package team5.calendarproject;

import java.io.Serializable;

/**
 * Created by edward on 3/25/16.
 */
abstract class Event  implements Serializable {
    private int time;
    private int hour;
    private int minute;
    private String date;
    private int day;
    private int month;
    private int year;
    private String title;
    private String color;
    private boolean alarm;
    private final int DB_ID_NUMBER;

    public Event(int time, String date, String title, String color, boolean alarm1, int dbIDNumber) {
        this.time = time;
        this.date = date;
        this.title = title;
        this.color = color;
        this.alarm = alarm1;
        this.DB_ID_NUMBER = dbIDNumber;
    }

    public Event(int hour, int minute, int day, int month, int year, String title, String color, boolean alarm1, int dbIDNumber) {
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        this.title = title;
        this.color = color;
        this.alarm = alarm1;
        this.DB_ID_NUMBER = dbIDNumber;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isAlarmSet() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public int getDbIDNumber() {
        return this.DB_ID_NUMBER;
    }
}
