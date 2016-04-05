package team5.calendarproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

//Android Studio Comment time.
public class MonthViewController extends AppCompatActivity {
    private Button addEventButton;

    //private Button MonthlyAddEventButton = (Button) findViewById(R.id.monthly_add_event_button);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_view);
        setupMonth();

        Button addEventButton = (Button) findViewById(R.id.monthly_add_event_button);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddEventView();
            }
        });

    }


    private void setupMonth() {
        Database db = new Database(this);

        int totalDaysInMonth = CalendarDates.values()[Calendar.MONTH].getNumberOfDays(Calendar.YEAR);
        int setupDayNumber = 1;

        int dayIDs[] = {R.id.weekly_sunday, R.id.weekly_monday, R.id.weekly_tuesday,
                R.id.weekly_wednesday, R.id.weekly_thursday, R.id.weekly_friday, R.id.weekly_saturday};

        int weekIDs[] = {R.id.monthly_week_1, R.id.monthly_week_2, R.id.monthly_week_3,
                R.id.monthly_week_4, R.id.monthly_week_5};

        String dayNames[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        View workingDay;
        TextView monthName = (TextView)findViewById(R.id.monthly_view_month_name);

        monthName.setText(CalendarDates.values()[Calendar.MONTH].toString());

        Cursor c = db.getAllEventRows();
        ArrayList<CalendarEvent> events = new ArrayList<>();
        if(c != null) {
            do {

                c.moveToFirst();

                //c.getInt(0); //_ID is 0

                //c.getString(1); //TITLE is 1 and so on...

                //Date date = new Date(c.getString(2));

                boolean alarm1 = false;
                if (c.getInt(6) != 0) {
                    alarm1 = true;
                }

                ArrayList<String> participants = new ArrayList<>();
                StringTokenizer t = new StringTokenizer(" ");
                //tokenize c.getString(11)

                //TODO: populate each item with dbInfo.getType_of_object(#); uncomment event

        /*
         * 0 _ID, 1 TITLE, 2 DATE, 3 TIME, 4 END_TIME, 5 COLOR, 6 ALARM1, 7 ALARM2, 8 ALARM3,
         *              9 REPEATING, 10 LOCATION, 11 PARTICIPANTS, 12 APP_ID
         */
                events.add(new CalendarEvent(
                                c.getInt(0), c.getInt(3), c.getInt(4), Calendar.APRIL,
                                c.getString(1), c.getString(5), alarm1,
                            /*c.getInt(7), c.getString(8),*/ participants,
                                c.getString(10), c.getInt(9))
                );

                c.moveToNext();
            } while (c.isLast() == false);
        }

        for(int weekNumber = 0; weekNumber < 5; weekNumber++) {
            for(int dayNumber = 0; dayNumber < 7; dayNumber++) {
                workingDay = (View)findViewById(weekIDs[weekNumber]).findViewById(dayIDs[dayNumber]);

                TextView dayName = (TextView)workingDay.findViewById(R.id.day_name);
                dayName.setText(dayNames[dayNumber] + " " + setupDayNumber);
                setupDayNumber++;
                if(setupDayNumber == totalDaysInMonth) setupDayNumber = 1;
            }
        }

    }

    //ADD EVENT
    public void goToAddEventView(){
        startActivity(new Intent(this, AddEventController.class));
    }

}
