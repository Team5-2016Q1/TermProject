package team5.calendarproject;

import java.util.Date;

/**
 * Created by edward on 3/25/16.
 */
public enum Repeating {
    Never(), Daily(), Monthly(), Yearly();

    private Date date;
    private boolean[] days = new boolean[7];

    Repeating() {
        this.date = new Date();
        for(boolean b : days) {
            b = false;
        }
    }

    public String toString(){
        String val = new String("" + date.toString() + " repeats " + this.name() + ".");
        return val;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDays(boolean mon, boolean tues, boolean wed,
              boolean thurs, boolean fri, boolean sat, boolean sun) {
        days[0] = mon;
        days[1] = tues;
        days[2] = wed;
        days[3] = thurs;
        days[4] = fri;
        days[5] = sat;
        days[6] = sun;
    }

    public void setWeekends(){
        days[5] = true;
        days[6] = true;
    }

    public void setWeekdays() {
        days[0] = true;
        days[1] = true;
        days[2] = true;
        days[3] = true;
        days[4] = true;
    }

    public boolean onMonday(){
        return this.days[0];
    }

    public boolean onTuesday(){
        return this.days[1];
    }

    public boolean onWednesday(){
        return this.days[2];
    }

    public boolean onThursday(){
        return this.days[3];
    }

    public boolean onFriday(){
        return this.days[4];
    }

    public boolean onSaturday(){
        return this.days[5];
    }

    public boolean onSunday(){
        return this.days[6];
    }
}
