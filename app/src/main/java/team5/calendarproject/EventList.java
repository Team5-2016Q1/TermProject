package team5.calendarproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EventList extends AppCompatActivity {

    Database eventdb;
    String findByThisString;
    String returnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //Needs to be changed
    public void ListEvents() {
        final ArrayList<String> data = new ArrayList<String>();
        final ArrayList<String>idInfo = new ArrayList<String>();

        Cursor cursor = eventdb.getEventRow(1);

        if(cursor.moveToFirst()){
            do{
                data.add(cursor.getString(1));
                idInfo.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();

        ListAdapter theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);
        ListView theListView = (ListView) findViewById(R.id.listView);

        theListView.setAdapter(theAdapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String itemSelected = "You Selected " + String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(EventList.this, itemSelected, Toast.LENGTH_SHORT).show();
                Intent next = new Intent(getApplicationContext(), EventViewController.class);
                findByThisString = String.valueOf(parent.getItemAtPosition(position));
                int i = 0;
                for (i = 0; i < data.size(); i++) {
                    if (findByThisString.equals(data.get(i))) {
                        returnId = idInfo.get(i);
                    }
                }
                next.putExtra("id", returnId);
                startActivity(next);

            }
        });


    }

}
