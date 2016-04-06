package team5.calendarproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class EventViewController extends AppCompatActivity {

    private Button btnEdit, btnDelete;
    private Database db;
    private CalendarEvent event = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        openDB();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            db = new Database(this);
            db.open();
            event = (CalendarEvent)extras.getSerializable("Event");
            Log.d("Event received", event.getTitle());

            setUpTextBoxes();
        }

        final Button shareEventButton = (Button)findViewById(R.id.invitePartButtons);
        shareEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{event.getParticipantsAsString()});
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    toast("No Email Client Found");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    /* THIS IS FOR MAKING A MENU, LEAVE FOR NOW
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } */

    //Added By Edward
    private void openDB() {
        db = new Database(this);
        db.open();
        //User_ID = getSharedPreferences("loginPrefs", MODE_PRIVATE).getInt("ID", -1);
        //Log.d("ID in ViewContact", "" + User_ID);
    }

    private void setUpTextBoxes() {
        if(event != null) {
            EditText text = (EditText) findViewById(R.id.et_EventTitle);
            text.setText(event.getTitle());
            text = (EditText) findViewById(R.id.et_EventDate);
            text.setText(event.getDate());
            text = (EditText) findViewById(R.id.et_EventStartTime);
            text.setText(event.getTime());
            text = (EditText) findViewById(R.id.et_EventEndTime);
            text.setText(event.getEndTime());
            text = (EditText) findViewById(R.id.et_Location);
            text.setText(event.getLocation());
            text = (EditText) findViewById(R.id.et_email1);
            text.setText(event.getParticipantsAsString());
            text = (EditText) findViewById(R.id.et_email2);
            text.setText(event.getParticipantsAsString());

            CheckBox checkBox = (CheckBox) findViewById(R.id.checkBoxEmail);
            checkBox = (CheckBox) findViewById(R.id.checkBoxAlarm);
            checkBox = (CheckBox) findViewById(R.id.checkBoxSilent);



            //TODO finish this. populate each R.id in event_view using event.get
        }
    }

    /*

    Going to be using this for CalendarViewController

    public void PopulateEventView(int id) {


        int idNum;
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
        ArrayList<String> participants = new ArrayList<String>();

        /*  0     1     2     3       4        5      6       7       8
         * _ID, TITLE, DATE, TIME, END_TIME, COLOR, ALARM1, ALARM2, ALARM3,
         *                   9         10          11         12
         *               REPEATING, LOCATION, PARTICIPANTS, APP_ID
         *
        Cursor dbInfo = db.getEventRow(id);

        idNum = dbInfo.getInt(0); //_ID is 0

        title = dbInfo.getString(1); //TITLE is 1 and so on...

        date = dbInfo.getString(2);

        time = dbInfo.getInt(3);

        endTime = dbInfo.getInt(4);

        color = dbInfo.getString(5);

        //alarm1
        if (dbInfo.getInt(6) == 0)
            alarm1 = false;
        else
            alarm1 = true;

        //alarm2
        if (dbInfo.getInt(7) == 0)
            alarm2 = false;
        else
            alarm2 = true;

        //alarm3
        if (dbInfo.getInt(8) == 0)
            alarm3 = false;
        else
            alarm3 = true;

        repeating = dbInfo.getInt(9);

        location = dbInfo.getString(10);

        StringTokenizer st = new StringTokenizer(dbInfo.getString(10), " ");
        while (st.hasMoreTokens()) {
            participants.add(st.nextToken());
        }



        CalendarEvent event = new CalendarEvent(idNum, time, endTime, date, title, color, alarm1,
                alarm2, alarm3, participants, location, repeating);

    } */

    public void editInstance(View v) {
        startActivity(new Intent(this, AddEventController.class).putExtra("Event", event));
    }

    public void deleteInstance(View v) {
        if(db.deleteEventRow(event.getDbIDNumber())) {
            toast("event removed");
            finish();
        } else
            toast("Event already removed");
    }

    private void toast(String description) {
        Toast.makeText(getApplicationContext(), description, Toast.LENGTH_LONG).show();
    }


}
