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

        btnDelete = (Button) findViewById(R.id.deleteButton);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteInstance(view);
            }
        });

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
            text.setText(event.getParticipants().get(0));
            text = (EditText) findViewById(R.id.et_email2);
            text.setText(event.getParticipants().get(1));

            CheckBox checkBox = (CheckBox) findViewById(R.id.checkBoxEmail);
            checkBox.setActivated(event.isAlarmSet());
            checkBox = (CheckBox) findViewById(R.id.checkBoxAlarm);
            checkBox.setActivated(event.isSecondAlarmSet());
            checkBox = (CheckBox) findViewById(R.id.checkBoxSilent);
            checkBox.setActivated(event.isThirdAlarmSet());

        }
    }

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
