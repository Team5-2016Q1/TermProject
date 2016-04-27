package team5.calendarproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Calendar;

//Android Studio Comment time.
public class MonthViewController extends AppCompatActivity {
    private Database                    db;
    private Button                      searchButton;
    private Button                      addEventButton;
    private Button                      viewEventButton;
    private TextView                    nextMonthButton;
    private TextView                    prevMonthButton;
    private TextView                    monthName;
    private int                         weekIDs[];
    private int                         dayIDs[];
    private ArrayList<CalendarEvent>    events;
    private ArrayList<Task>             tasks;
    private int                         theMonth;
    private int                         theYear;

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

        viewEventButton = (Button) findViewById(R.id.view_EventButton);
        viewEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEventView();
            }
        });
        /*
        searchButton = (Button) findViewById(R.id.action_favorite);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSearch();
            }
        });
        */

        prevMonthButton = (TextView) findViewById(R.id.month_view_previous_month);
        prevMonthButton.setText("<");
        prevMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(theMonth == Calendar.JANUARY) {
                    theMonth = Calendar.DECEMBER;
                    theYear = theYear - 1;
                } else
                    theMonth = theMonth - 1;
                setupMonth();
            }
        });

        nextMonthButton = (TextView) findViewById(R.id.month_view_next_month);
        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (theMonth == Calendar.DECEMBER) {
                    theMonth = Calendar.JANUARY;
                    theYear = theYear + 1;
                } else
                    theMonth = theMonth + 1;
                setupMonth();
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
        theMonth = Calendar.getInstance().get(Calendar.MONTH);
        theYear = Calendar.getInstance().get(Calendar.YEAR);
        makeEventsList();
        setupIDvalues();
        setupMonth();
    }

    //instantiates the database and recovers User_ID from the shared preference file
    private void openDB() {
        //User_ID = getSharedPreferences("loginPrefs", MODE_PRIVATE).getInt("ID", -1);
        //Log.d("User ID ContactList", "" + User_ID);

        db = new Database(this);
        db.open();
    }

    private void setupIDvalues() {
        dayIDs = new int[]{R.id.weekly_sunday, R.id.weekly_monday, R.id.weekly_tuesday,
                R.id.weekly_wednesday, R.id.weekly_thursday, R.id.weekly_friday, R.id.weekly_saturday};

        weekIDs = new int[]{R.id.monthly_week_1, R.id.monthly_week_2, R.id.monthly_week_3,
                R.id.monthly_week_4, R.id.monthly_week_5, R.id.monthly_week_6};
    }

    private void setupMonth() {
        monthName = (TextView) findViewById(R.id.monthly_view_month_name);


        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, theYear);
        c.set(Calendar.MONTH, theMonth);
        c.set(Calendar.DAY_OF_MONTH, 1);
        int daysInPreviousMonth =
                CalendarDates.values()[
                        (theMonth==Calendar.JANUARY? Calendar.DECEMBER : theMonth-1)
                    ].getNumberOfDays(
                            (theMonth==Calendar.JANUARY? theYear-1 : theYear)
                        );

        //set to calendar current month name
        monthName.setText(CalendarDates.values()[theMonth].toString() + " " + theYear);

        //so now we have the day that the month starts on, the amount of days in the previous month
        //and the days in current month.

        //amount of days previous to what should be first day
        int dayShift;
        if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            dayShift = 1;
        } else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            dayShift = 2;
        } else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            dayShift = 3;
        } else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            dayShift = 4;
        } else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            dayShift = 5;
        } else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            dayShift = 6;
        } else
            dayShift = 0; //2 for hard coding later

        int     totalDaysInMonth  = CalendarDates.values()[theMonth].getNumberOfDays(theYear);
        int     setupDayNumber    = 1;
        int     subtractDay       = dayShift - 1;
        int     weekNumber        = 0;
        boolean nextMonthsDays    = false;
        int     dayNumber;
        View    workingDay;
        GridLayout dayFace;

        //This loop goes over any days of previous month on the first week
        for( ; weekNumber < 6; weekNumber++) {
            dayNumber = 0;

            //only first week
            if(weekNumber == 0) {
                for (; dayNumber < dayShift; dayNumber++) {
                    workingDay = findViewById(weekIDs[weekNumber]).findViewById(dayIDs[dayNumber]);
                    dayFace = (GridLayout)workingDay.findViewById(R.id.dayFace);
                    dayFace.setBackgroundColor(Color.LTGRAY);

                    TextView dayName = (TextView) workingDay.findViewById(R.id.day_name);
                    dayName.setText(Integer.toString(daysInPreviousMonth - subtractDay));

                    subtractDay--;
                }
            }

            for ( ; dayNumber < 7; dayNumber++) {
                workingDay = findViewById(weekIDs[weekNumber]).findViewById(dayIDs[dayNumber]);
                dayFace = (GridLayout)workingDay.findViewById(R.id.dayFace);
                if(nextMonthsDays)
                    dayFace.setBackgroundColor(Color.LTGRAY);
                else {
                    for(CalendarEvent event : events)
                        if (event.getDay() == dayNumber) {
                            toast("event found");
                            TextView eventText = (TextView) workingDay.findViewById(R.id.day_event_1);
                            eventText.setText(event.getTitle());
                            eventText.setTextColor(Color.BLUE);
                        }
                    dayFace.setBackgroundColor(Color.WHITE);
                }
                TextView dayName = (TextView) workingDay.findViewById(R.id.day_name);
                dayName.setText("" + setupDayNumber);

                setupDayNumber++;
                if (setupDayNumber - 1 == totalDaysInMonth){
                    setupDayNumber = 1;
                    nextMonthsDays = true;
                }
            }
        }

        //PHEW
    }

    private void makeEventsList() {
        Cursor c = db.getAllEventRows();
        this.events = new ArrayList<>();
        if (c != null && c.moveToFirst()) {
            do {
                boolean alarm1 = c.getInt(6) != 0;

                boolean alarm2 = c.getInt(7) != 0;

                boolean alarm3 = c.getInt(8) != 0;

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
                /*String[] month_day_year = c.getString(2).split("/");
                int month = new Integer(month_day_year[0]);
                int day = new Integer(month_day_year[1]);
                if(c.getString(4).contains("" + theMonth)) {

                    View dayView = findViewById(weekIDs[]).findViewById(dayIDs)
                }*/

                if (c.isLast()) return;

                c.moveToNext();

            } while (true);
        } else
            toast("Testing: c is null");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent = null;

        switch (id){
            case(R.id.action_favorite):
                intent = new Intent(this,Search.class);
                break;
        }

        if(intent != null)
            startActivity(intent);
        return true;
    }

    /**
     * day the day the event is on
     * month the month the event is on
     */
    private void goToEventView() {
        if (events.isEmpty()) {
            toast("No event found");
            return;
        }
        startActivity(
                new Intent(this, EventList.class).putExtra("Event", events)
        );
    }

    //ADD EVENT
    public void goToAddEventView() {
        startActivity(
                new Intent(this, AddEventController.class)
        );
    }
    /*
    public void goToSearch() {
        startActivity(
                new Intent(this, Search.class)
        );
    }
    */
    private void toast(String description) {
        Toast.makeText(getApplicationContext(), description, Toast.LENGTH_LONG).show();
    }

}
