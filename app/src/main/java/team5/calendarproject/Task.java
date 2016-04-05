package team5.calendarproject;

/**
 * Created by edward on 3/25/16.
 */
public class Task extends Event {
    public Task(int idNumber, int time, int date, String title, String color, boolean alarm) {
        super(time, date, title, color, alarm, idNumber);
    }
}
