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

import java.util.ArrayList;

public class EventList extends AppCompatActivity {

    private Database db;
    private String findByThisString;
    private String returnId;
    private final ArrayList<String> data = new ArrayList<>();
    //private final ArrayList<String>idInfo = new ArrayList<>();
    private ArrayList<CalendarEvent>events = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        openDB();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            events = new ArrayList<>( (ArrayList)extras.getSerializable("Event") );
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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

        /*Cursor cursor = db.getAllEventRows();

        if(cursor != null){
            do{
                data.add(cursor.getString(1)); //This will be Event's Title
                idInfo.add(new Integer(cursor.getInt(0)).toString()); //This will be the database RowID
            }while( cursor.moveToNext() );
        }
        cursor.close();*/

        ListAdapter theAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,events);
        ListView theListView = (ListView) findViewById(R.id.listView);

        theListView.setAdapter(theAdapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //String itemSelected = "You Selected " + String.valueOf(parent.getItemAtPosition(position));
                //Toast.makeText(EventList.this, itemSelected, Toast.LENGTH_SHORT).show();
                Intent next = new Intent(getApplicationContext(), EventViewController.class);
                findByThisString = String.valueOf(parent.getItemAtPosition(position));
                for (int i = 0; i < events.size(); i++) {
                    if (findByThisString.equals(events.get(i).getTitle())) {
                        returnId = Integer.toString(events.get(i).getDbIDNumber());
                    }
                }
                next.putExtra("id", returnId);
                startActivity(next);

            }
        });


    }

}
