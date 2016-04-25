package team5.calendarproject;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class Search extends AppCompatActivity {
    private Database db;
    private Button searchButton;
    private ArrayList<CalendarEvent> events = null;
    private ArrayList<CalendarEvent> displayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        openDB();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            events = new ArrayList<>( (ArrayList)extras.getSerializable("events") );
        }

        displayList = new ArrayList<>();

        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchForEvent())
                    listEvents();

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
    }

    private boolean searchForEvent () {
        boolean foundEvents = false;

        //search through all the events
        //for(CalendarEvent e : events)
        //   look at all of the attributes and compare.
        //    if any matches, add to displayList, set foundEvents true;

        return foundEvents;
    }

    private void listEvents() {
        //list events inside of list view;
    }

    //instantiates the database and recovers User_ID from the shared preference file
    private void openDB() {
        db = new Database(this);
        db.open();
    }





}
