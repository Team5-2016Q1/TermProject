package team5.calendarproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EventList extends AppCompatActivity {

    private Database db;
    private ArrayList<CalendarEvent>events = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        openDB();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            events = (ArrayList)extras.getSerializable("Event");
        }

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
        ListEvents();
    }

    //instantiates the database and recovers User_ID from the shared preference file
    private void openDB() {
        db = new Database(this);
        db.open();
    }

    //Needs to be changed
    public void ListEvents() {

        ListAdapter theAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, events);
        ListView theListView = (ListView) findViewById(R.id.listView);

        theListView.setAdapter(theAdapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent next = new Intent(getApplicationContext(), EventViewController.class);

                next.putExtra("Event", events.get(position));
                startActivity(next);
                finish();
            }
        });

    }

    private void toast(String description) {
        Toast.makeText(getApplicationContext(), description, Toast.LENGTH_LONG).show();
    }


}
