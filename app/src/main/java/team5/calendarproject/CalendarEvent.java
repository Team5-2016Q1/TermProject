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
        super(time, date, title, color, alarm1, idNumber);
        this.participants = participants;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public String getParticipantsAsString() {
        String ret = "";
        for(String s : getParticipants()) {
            ret += s + " ";
        }
        return ret;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public void removeParticipant(int participantNumber) {
        this.participants.remove(participantNumber);
    }

    public void addParticipant(String participantEmail) {
        this.participants.add(participantEmail);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Repeating getRepeats() {
        return repeats;
    }

    public void setRepeats(Repeating repeats) {
        this.repeats = repeats;
    }
}
