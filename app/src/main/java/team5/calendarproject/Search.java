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

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class Search extends AppCompatActivity {
    private Database db;
    private Button searchButton;
    private EditText searchBox;
    private String returnId;
    private String findByThisString;
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
                if (searchForEvent())
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
        displayList.clear();
        searchBox = (EditText) findViewById(R.id.et_search);
        String searchValue = searchBox.getText().toString();
        boolean number = false;
        Integer searchValueAsInt = -1;
        try {
            searchValueAsInt = new Integer(searchValue);
            //search through all the events
            //for(CalendarEvent e : events)
            //   look at all of the attributes and compare.
            //    if any matches, add to displayList, set foundEvents true;
        } catch (NumberFormatException nfe) {

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
            else if (e.getHour() == searchValueAsInt || e.getMinute() == searchValueAsInt) {
                displayList.add(e);
                foundEvents = true;
            } // Date
            else if (e.getDay() == searchValueAsInt || e.getMonth() == searchValueAsInt
                    || e.getYear() == searchValueAsInt) {
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

                    //String itemSelected = "You Selected " + String.valueOf(parent.getItemAtPosition(position));
                    //Toast.makeText(EventList.this, itemSelected, Toast.LENGTH_SHORT).show();
                    Intent next = new Intent(getApplicationContext(), EventViewController.class);
                    findByThisString = String.valueOf(parent.getItemAtPosition(position));
                    for (int i = 0; i < displayList.size(); i++) {
                        if (findByThisString.equals(displayList.get(i).getTitle())) {
                            returnId = Integer.toString(displayList.get(i).getDbIDNumber());
                            next.putExtra("Event", displayList.get(i));
                        }
                    }
                    next.putExtra("id", returnId);
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





}
