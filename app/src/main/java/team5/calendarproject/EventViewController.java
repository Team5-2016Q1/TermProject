package team5.calendarproject;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class EventViewController extends AppCompatActivity {

    private Button btnEdit, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);
    }

    public void PopulateEventView(int id) {
        Database db = new Database(this);

        String title;
        String date;
        int time;
        int endTime;
        String color;
        boolean alarm1;
        boolean alarm2;
        boolean alarm3;
        int repeating;
        String location;
        String participants;

        /*
         * _ID, TITLE, DATE, TIME, END_TIME, COLOR, ALARM1, ALARM2, ALARM3,
         *               REPEATING, LOCATION, PARTICIPANTS, APP_ID
         */
        Cursor dbInfo = db.getEventRow(id);

        title = dbInfo.getString(1); //_ID is 0, TITLE is 1 and so on...

        //TODO: populate each item with dbInfo.getType_of_object(#); uncomment event

        //CalendarEvent event = new CalendarEvent(time, endTime, date, title, color, alarm1,
        //        alarm2, alarm3, participants, location, repeating);

    }
}
