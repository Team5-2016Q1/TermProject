package team5.calendarproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class Search extends AppCompatActivity {
    private Database db;
    private Button searchButton;
    private EditText searchBox;
    private ArrayList<CalendarEvent> events = null;
    private ArrayList<CalendarEvent> displayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        openDB();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        events = new ArrayList<>();
        displayList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            events = (ArrayList)extras.getSerializable("Events");
        }

        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchForEvent())
                    listEvents();
                else
                    toast("No events found");

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

        displayList.clear();
        listEvents();
    }

    private boolean searchForEvent () {
        boolean foundEvents = false;

        displayList.clear();
        listEvents();

        searchBox = (EditText) findViewById(R.id.et_search);
        String searchValue = searchBox.getText().toString();

        Integer searchValueAsInt = -1;
        try {
            searchValueAsInt = new Integer(searchValue);
        } catch (NumberFormatException nfe) {

        }

        for(CalendarDates c : CalendarDates.values()) {
            if(c.name().equalsIgnoreCase(searchValue)) {
                for(CalendarEvent e : events) {
                    if (CalendarDates.values()[e.getMonth()-1] == c) {
                        displayList.add(e);
                        foundEvents = true;
                    }
                }
                if(foundEvents) return foundEvents;
            }
        }

        for (CalendarEvent e : events) {
            // Title
            if (e.getTitle().equalsIgnoreCase(searchValue)) {
                displayList.add(e);
                foundEvents = true;
            } // Location
            else if (e.getLocation().equalsIgnoreCase(searchValue)) {
                displayList.add(e);
                foundEvents = true;
            } // Participants
            else if (e.getParticipantsAsString().contains(searchValue)) {
                displayList.add(e);
                foundEvents = true;
            } // Time
            else if (e.getHour() == searchValueAsInt.intValue()
                    || e.getMinute() == searchValueAsInt.intValue()) {
                displayList.add(e);
                foundEvents = true;
            } // Date
            else if (e.getDay() == searchValueAsInt.intValue()
                    || e.getMonth() == searchValueAsInt.intValue()
                    || e.getYear() == searchValueAsInt.intValue()) {
                displayList.add(e);
                foundEvents = true;
            }
        }
        return foundEvents;
    }

    private void listEvents() {

        //list events inside of list view;
        if(displayList != null) {

            ListAdapter theAdapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
            ListView theListView = (ListView) findViewById(R.id.result_list);

            theListView.setAdapter(theAdapter);
            theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent next = new Intent(getApplicationContext(), EventViewController.class);
                    next.putExtra("Event", displayList.get(position));
                    startActivity(next);

                }
            });
        }
    }



    //instantiates the database and recovers User_ID from the shared preference file
    private void openDB() {
        db = new Database(this);
        db.open();
    }

    private void toast(String description) {
        Toast.makeText(getApplicationContext(), description, Toast.LENGTH_LONG).show();
    }

}
