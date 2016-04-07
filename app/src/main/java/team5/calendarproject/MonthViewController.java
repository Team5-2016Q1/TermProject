package team5.calendarproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

//Android Studio Comment time.
public class MonthViewController extends AppCompatActivity {
    private Button addEventButton;
    private Button viewEventButton;
    private TextView monthName;
    private ArrayList<CalendarEvent> events;
    private ArrayList<Task> tasks;
    private Database db;
    private int weekIDs[];
    private int dayIDs[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        openDB();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_view);

        addEventButton = (Button) findViewById(R.id.monthly_add_event_button);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddEventView();
            }
        });

        viewEventButton = (Button) findViewById(R.id.viewEventButton);
        viewEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEventView();
            }
        });


    }

    @Override
    //close db before activity exit
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupMonth(Calendar.MONTH);
        makeEventsList();
    }

    //instantiates the database and recovers User_ID from the shared preference file
    private void openDB() {
        //User_ID = getSharedPreferences("loginPrefs", MODE_PRIVATE).getInt("ID", -1);
        //Log.d("User ID ContactList", "" + User_ID);

        db = new Database(this);
        db.open();
    }


    private void setupMonth(int m) {

        int totalDaysInMonth = CalendarDates.values()[m].getNumberOfDays(Calendar.YEAR);
        int setupDayNumber = 1;

        dayIDs = new int[] {R.id.weekly_sunday, R.id.weekly_monday, R.id.weekly_tuesday,
                R.id.weekly_wednesday, R.id.weekly_thursday, R.id.weekly_friday, R.id.weekly_saturday};

        weekIDs = new int[] {R.id.monthly_week_1, R.id.monthly_week_2, R.id.monthly_week_3,
                R.id.monthly_week_4, R.id.monthly_week_5, R.id.monthly_week_6};

        //String dayNames[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        View workingDay;

        monthName = (TextView)findViewById(R.id.monthly_view_month_name);
        //set to current month name
        monthName.setText(CalendarDates.values()[m].toString());

        //TODO: figure out to properly do this
        for(int weekNumber = 0; weekNumber < 6; weekNumber++) {
            for(int dayNumber = 0; dayNumber < 7; dayNumber++) {
                workingDay = findViewById(weekIDs[weekNumber]).findViewById(dayIDs[dayNumber]);

                TextView dayName = (TextView)workingDay.findViewById(R.id.day_name);
                dayName.setText("" + setupDayNumber);
                setupDayNumber++;
                if(setupDayNumber == totalDaysInMonth) setupDayNumber = 1;
            }
        }

    }

    private void makeEventsList() {
        Cursor c = db.getAllEventRows();
        this.events = new ArrayList<>();
        if(c != null && c.moveToFirst()) {
            do {
                boolean alarm1 = false;
                if (c.getInt(6) != 0) {
                    alarm1 = true;
                }

                boolean alarm2 = false;
                if (c.getInt(7) != 0) {
                    alarm2 = true;
                }

                boolean alarm3 = false;
                if (c.getInt(8) != 0) {
                    alarm3 = true;
                }

                ArrayList<String> participants = new ArrayList<>();
                String[] result = c.getString(11).split(" ");
                for (int x = 0; x < result.length; x++)
                    participants.add(result[x]);

                /*  0     1     2     3       4        5      6       7       8
                 * _ID, TITLE, DATE, TIME, END_TIME, COLOR, ALARM1, ALARM2, ALARM3,
                 *                   9         10          11         12
                 *               REPEATING, LOCATION, PARTICIPANTS, APP_ID
                 */
                // int idNumber, int time, int endTime, String date, String title,
                // String color, Boolean alarm1, Boolean alarm2, Boolean alarm3,
                // ArrayList<String> participants, String location, int repeats
                events.add(new CalendarEvent(
                                c.getInt(0), c.getInt(3), c.getInt(4), c.getString(2),
                                c.getString(1), c.getString(5), alarm1, alarm2, alarm3,
                                participants, c.getString(10), c.getInt(9))
                );

                //TODO: figure out date parsing from string
                //if date is on this month
                //     find weekID / dayID
                //     workingDay = (View)
                //     textView event1 = wD.findViewByID
                //     event1.setVisible(true)
                //     .setColor(getColor)


                if(c.isLast()) return;

                c.moveToNext();

            } while (true);
        } else
            toast("Testing: c is null");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return true;
    }

    /**
     *
     * day the day the event is on
     * month the month the event is on
     *
     */
    private void goToEventView() {
        if(events.isEmpty()) {
            toast("No event found");
            return;
        }
        startActivity(
                new Intent(this, EventViewController.class).putExtra("Event", events.get(0))
        );
    }

    //ADD EVENT
    public void goToAddEventView(){
        startActivity(
                new Intent(this, AddEventController.class)
        );
    }

    private void toast(String description) {
        Toast.makeText(getApplicationContext(), description, Toast.LENGTH_LONG).show();
    }

}
