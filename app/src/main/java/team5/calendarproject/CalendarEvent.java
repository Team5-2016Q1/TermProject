package team5.calendarproject;

import java.util.ArrayList;

/**
 * Created by edward on 3/25/16.
 */
public class CalendarEvent extends Event {
    private ArrayList<String> participants;
    private String location;
    private int endTime;
    private int ends_hours_later;
    private int repeats;
    private boolean secondAlarm;
    private boolean thirdAlarm;

    public CalendarEvent(int idNumber, int time, int endTime, String date, String title, String color, Boolean alarm1, Boolean alarm2, Boolean alarm3,
                         ArrayList<String> participants, String location, int repeats) {
        super(time, date, title, color, alarm1, idNumber);
        this.participants = participants;
        this.endTime = endTime;
        this.location = location;
        secondAlarm = alarm2;
        thirdAlarm = alarm3;
        //this.repeats = repeats;
    }


    public CalendarEvent(int idNumber, int startHour, int startMinute, int startDay, int startMonth, int startYear,
                         int hours_later, String title, String color, Boolean alarm1, Boolean alarm2, Boolean alarm3,
                         ArrayList<String> participants, String location, int repeats) {
        super(startHour, startMinute, startDay, startMonth, startYear, title, color, alarm1, idNumber);
        this.participants = participants;
        this.ends_hours_later = hours_later;
        this.location = location;
        secondAlarm = alarm2;
        thirdAlarm = alarm3;
        //this.repeats = repeats;
    }

    private void setEnds_hours_later(int totalHours) {
        this.ends_hours_later = totalHours;
    }

    private int getEnds_hours_later() {
        return ends_hours_later;
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

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public boolean isSecondAlarmSet() {
        return secondAlarm;
    }

    public void setSecondAlarm(boolean secondAlarm) {
        this.secondAlarm = secondAlarm;
    }

    public boolean isThirdAlarmSet() {
        return thirdAlarm;
    }

    public void setThirdAlarm(boolean thirdAlarm) {
        this.thirdAlarm = thirdAlarm;
    }


    //I used this for debugging purposes -- Benson
    @Override
    public String toString() {
        return  "Event: " + getTitle() + "\n" +
                "From: " + getTime() +
                "  Until: " + getEndTime() +
                "  On: " + getDate();
    }
}
