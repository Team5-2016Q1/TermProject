package team5.calendarproject;

import java.util.ArrayList;

/**
 * Created by edward on 3/25/16.
 */
public class CalendarEvent extends Event {
    public ArrayList<String> participants;

    public CalendarEvent(int time, int date, String title, String color, String alarm,
                         ArrayList<String> participants) {
        super(time, date, title, color, alarm);
        this.participants = participants;
    }
}
