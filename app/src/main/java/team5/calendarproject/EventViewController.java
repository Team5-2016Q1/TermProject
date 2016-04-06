package team5.calendarproject;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class EventViewController extends AppCompatActivity {

    private Button btnEdit, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);
    }

    public void PopulateEventView(int id) {
        Database db = new Database(this);

        int idNum;
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
        ArrayList<String> participants = new ArrayList<String>();

        /*  0     1     2     3       4        5      6       7       8
         * _ID, TITLE, DATE, TIME, END_TIME, COLOR, ALARM1, ALARM2, ALARM3,
         *                   9         10          11         12
         *               REPEATING, LOCATION, PARTICIPANTS, APP_ID
         */
        Cursor dbInfo = db.getEventRow(id);

        idNum = dbInfo.getInt(0); //_ID is 0

        title = dbInfo.getString(1); //TITLE is 1 and so on...

        date = dbInfo.getString(2);

        time = dbInfo.getInt(3);

        endTime = dbInfo.getInt(4);

        color = dbInfo.getString(5);

        //alarm1
        if (dbInfo.getInt(6) == 0)
            alarm1 = false;
        else
            alarm1 = true;

        //alarm2
        if (dbInfo.getInt(7) == 0)
            alarm2 = false;
        else
            alarm2 = true;

        //alarm3
        if (dbInfo.getInt(8) == 0)
            alarm3 = false;
        else
            alarm3 = true;

        repeating = dbInfo.getInt(9);

        location = dbInfo.getString(10);

        StringTokenizer st = new StringTokenizer(dbInfo.getString(10), " ");
        while (st.hasMoreTokens()) {
            participants.add(st.nextToken());
        }

        //TODO: populate each item with dbInfo.getType_of_object(#); uncomment event

        CalendarEvent event = new CalendarEvent(idNum, time, endTime, date, title, color, alarm1,
                alarm2, alarm3, participants, location, repeating);

    }


}
