package team5.calendarproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class AddEventController extends AppCompatActivity {
    private Button btnCreate, btnCancel;
    private EditText etEventTitle, etEventDate, etEventLocation, etEventEndTime, etEventStartTime,
            etEmail1, etEmail2;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_view);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        makeButtonsWork();

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //Cancel();
        btnCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createNewEvent();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(),MonthViewController.class);
                startActivity(back);
            }
        });
    }

    private void makeButtonsWork() {

        btnCancel = (Button)findViewById(R.id.cancelButton);
        btnCreate = (Button)findViewById(R.id.createButton);
        etEventTitle = (EditText) findViewById(R.id.et_EventTilte);
        etEventDate = (EditText) findViewById(R.id.et_EventDate);
        etEventLocation = (EditText) findViewById(R.id.et_Location);
        etEventStartTime = (EditText) findViewById(R.id.et_EventStartTime);
        etEventEndTime = (EditText) findViewById(R.id.et_EventEndTime);
        etEmail1 = (EditText) findViewById(R.id.et_email1);
        etEmail2 = (EditText) findViewById(R.id.et_email2);

    }

    private void Cancel() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(),MonthViewController.class);
                startActivity(back);
            }
        });
    }




    public void createNewEvent() {
        Database db = new Database(this);

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
        String participants;

        //TODO: initialize each item, uncomment db.insertEventRow

        //db.insertEventRow(title, date, time, endTime, color, alarm1, alarm2, alarm3, repeating,
        //        location, participants);

        //Maybe go to event view
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddEventController Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://team5.calendarproject/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddEventController Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://team5.calendarproject/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
