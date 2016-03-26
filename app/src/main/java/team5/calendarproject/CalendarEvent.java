package team5.calendarproject;

import java.util.ArrayList;

/**
 * Created by edward on 3/25/16.
 */
public class CalendarEvent extends Event {
    private ArrayList<String> participants;
    private String location;
    private String endTime;
    private Repeating repeats;

    public CalendarEvent(int time, int date, String title, String color, String alarm,
                         ArrayList<String> participants, String location, int r) {
        super(time, date, title, color, alarm);
        this.participants = participants;
    }
}
