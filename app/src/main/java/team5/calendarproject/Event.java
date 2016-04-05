package team5.calendarproject;

/**
 * Created by edward on 3/25/16.
 */
abstract class Event  {
    private int time;
    private int date;
    private String title;
    private String color;
    boolean alarm;
    private final int DB_ID_NUMBER;

    public Event(int time, int date, String title, String color, boolean alarm, int dbIDNumber) {
        this.time = time;
        this.date = date;
        this.title = title;
        this.color = color;
        this.alarm = alarm;
        this.DB_ID_NUMBER = dbIDNumber;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
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

    public Boolean getAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public int getDbIDNumber() {
        return this.DB_ID_NUMBER;
    }
}
