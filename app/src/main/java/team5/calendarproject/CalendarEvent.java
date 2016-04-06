package team5.calendarproject;

import java.util.ArrayList;

/**
 * Created by edward on 3/25/16.
 */
public class CalendarEvent extends Event {
    private ArrayList<String> participants;
    private String location;
    private int endTime;
    private Repeating repeats;

    public CalendarEvent(int idNumber, int time, int endTime, String date, String title, String color, Boolean alarm1, Boolean alarm2, Boolean alarm3,
                         ArrayList<String> participants, String location, int repeats) {
        super(time, date, title, color, alarm, idNumber);
        this.participants = participants;
    }
}
