package team5.calendarproject;

/**
 * Created by edward on 3/25/16.
 */
abstract class Event  {
    private int time;
    private int date;
    private String title;
    private String color;
    private String alarm;

    public Event(int time, int date, String title, String color, String alarm) {
        this.time = time;
        this.date = date;
        this.title = title;
        this.color = color;
        this.alarm = alarm;
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

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
}
