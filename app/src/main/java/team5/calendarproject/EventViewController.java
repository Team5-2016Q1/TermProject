package team5.calendarproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EventViewController extends AppCompatActivity {

    private Button btnEdit, btnDelete, shareEventButton;
    private Database db;
    private CalendarEvent event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        openDB();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int id = (int)extras.getSerializable("id");
            Cursor c = db.getEventRow(id);
            makeEvent(c);
            Log.d("Event received", event.getTitle());
            setUpTextBoxes();
        }

        btnDelete = (Button) findViewById(R.id.deleteButton);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteInstance(view);
            }
        });

        btnEdit = (Button) findViewById(R.id.editButton);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateInstance(view);
            }
        });

        shareEventButton = (Button)findViewById(R.id.invitePartButtons);
        shareEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendEmail();
            }
        });
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }


    //Added By Edward
    private void openDB() {
        db = new Database(this);
        db.open();
        //User_ID = getSharedPreferences("loginPrefs", MODE_PRIVATE).getInt("ID", -1);
        //Log.d("ID in ViewContact", "" + User_ID);
    }


    private void updateInstance(View v) {
        updateEvent();
        toast("Event Updated");
        finish();
    }

    private void sendEmail() {
        updateEvent(); //why are we updating event here?
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");

        i.putExtra(Intent.EXTRA_EMAIL, new String[]{
                event.getParticipants().get(0), event.getParticipants().get(1)});
        i.putExtra(Intent.EXTRA_SUBJECT, "You're invited to " + event.getTitle() +"!");
        String emailText;
        emailText = "What?: " + event.getTitle() + "\nWhere?: " + event.getLocation() +
                "\nWhen?: " + event.getTime() + " until " + event.getEndTime() +
                "\nIf you would like to join, please email me back before " +
                event.getDate() + "!";
        i.putExtra(Intent.EXTRA_TEXT, emailText);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            toast("No Email Client Found");
        }
    }

    private void updateEvent() {
        ArrayList<String> participants = new ArrayList<>();

        EditText text = (EditText) findViewById(R.id.et_EventTitle);
        event.setTitle(text.getText().toString());
        text = (EditText) findViewById(R.id.et_EventDate);
        event.setDate(text.getText().toString());
        text = (EditText) findViewById(R.id.et_EventStartTime);
        event.setTime(0);
        if (!text.getText().toString().isEmpty())
            event.setTime(new Integer(text.getText().toString()));
        //event.setTime(new Integer(text.getText().toString()));
        text = (EditText) findViewById(R.id.et_ViewEvent_EndTime);
        event.setEndTime(0);
        if (!text.getText().toString().isEmpty())
            event.setEndTime( new Integer(text.getText().toString()));
        text = (EditText) findViewById(R.id.et_Location);
        event.setLocation(text.getText().toString());
        text = (EditText) findViewById(R.id.et_email1);
        if (!text.getText().toString().isEmpty())
            participants.add(text.getText().toString());
        text = (EditText) findViewById(R.id.et_EventView_Email2);
        if (!text.getText().toString().isEmpty())
            participants.add(text.getText().toString());
        event.setParticipants(participants);
        //adding for push

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBoxEmail);
        event.setAlarm(checkBox.isChecked());
        checkBox = (CheckBox) findViewById(R.id.checkBoxAlarm);
        event.setSecondAlarm(checkBox.isChecked());
        checkBox = (CheckBox) findViewById(R.id.checkBox3);
        event.setThirdAlarm(checkBox.isChecked());
        int alarm = (event.isAlarmSet()? 1 : 0);
        int alarm2 = (event.isSecondAlarmSet()? 1 : 0);
        int alarm3 = (event.isThirdAlarmSet()? 1 : 0);
        //follow the Database method inputs passed from event. whatever. Fill out completely.
        db.updateEventRow(event.getDbIDNumber(), event.getTitle(), event.getDate(), event.getTime(), event.getEndTime(),
                event.getColor(), alarm, alarm2, alarm3, event.getRepeats(), event.getLocation(), event.getParticipantsAsString());
    }

    private void setUpTextBoxes() {
        if(event != null) {
            EditText text = (EditText) findViewById(R.id.et_EventTitle);
            text.setText(event.getTitle());
            text = (EditText) findViewById(R.id.et_EventDate);
            text.setText(event.getDate());
            text = (EditText) findViewById(R.id.et_EventStartTime);
            text.setText("" + event.getTime());
            if (event.getTime() == 0)
                text.setText("");
            text = (EditText) findViewById(R.id.et_ViewEvent_EndTime);
            text.setText("" + event.getEndTime());
            if (event.getEndTime() == 0)
                text.setText("");
            text = (EditText) findViewById(R.id.et_Location);
            text.setText(event.getLocation());

            //having no participants does not fully work...
            if (event.getParticipants().isEmpty()) {
                text = (EditText) findViewById(R.id.et_email1);
                text.setText("");
                text = (EditText) findViewById(R.id.et_EventView_Email2);
                text.setText("");
            } else {
                if (!event.getParticipants().get(0).isEmpty()) {
                    text = (EditText) findViewById(R.id.et_email1);
                    text.setText(event.getParticipants().get(0));
                } else {
                    text = (EditText) findViewById(R.id.et_email1);
                    text.setText("");
                }
                if (!event.getParticipants().get(1).isEmpty()) {
                    text = (EditText) findViewById(R.id.et_EventView_Email2);
                    text.setText(event.getParticipants().get(1));
                } else {
                    text = (EditText) findViewById(R.id.et_email1);
                    text.setText("");
                }
            }


            CheckBox checkBox = (CheckBox) findViewById(R.id.checkBoxEmail);
            checkBox.setChecked(event.isAlarmSet());
            checkBox = (CheckBox) findViewById(R.id.checkBoxAlarm);
            checkBox.setChecked(event.isSecondAlarmSet());
            checkBox = (CheckBox) findViewById(R.id.checkBox3);
            checkBox.setChecked(event.isThirdAlarmSet());

        }
    }

    private void makeEvent(Cursor c) {
        boolean alarm1 = false;
        if (c.getInt(6) != 0) {
            alarm1 = true;
        }

        boolean alarm2 = false;
        if (c.getInt(7) != 0) {
            alarm2 = true;
        }

        boolean alarm3 = false;
        if (c.getInt(8) != 0) {
            alarm3 = true;
        }

        ArrayList<String> participants = new ArrayList<>();
        if (!c.getString(11).isEmpty()) {
            String[] result = c.getString(11).split(" ");
            for (int x = 0; x < result.length; x++) {
                participants.add(result[x]);
                System.out.println(result[x]);
            }
        }


                /*  0     1     2     3       4        5      6       7       8
                 * _ID, TITLE, DATE, TIME, END_TIME, COLOR, ALARM1, ALARM2, ALARM3,
                 *                   9         10          11         12
                 *               REPEATING, LOCATION, PARTICIPANTS, APP_ID
                 */
        // int idNumber, int time, int endTime, String date, String title,
        // String color, Boolean alarm1, Boolean alarm2, Boolean alarm3,
        // ArrayList<String> participants, String location, int repeats
        event = new CalendarEvent(
                        c.getInt(0), c.getInt(3), c.getInt(4), c.getString(2),
                        c.getString(1), c.getString(5), alarm1, alarm2, alarm3,
                        participants, c.getString(10), c.getInt(9));
    }

    //we should be editing event this way. Pass details to AddEventController and "save" there.
    public void editInstance(View v) {
        startActivity(new Intent(this, AddEventController.class).putExtra("Event", event));
    }

    public void deleteInstance(View v) {
        if(db.deleteEventRow(event.getDbIDNumber())) {
            toast("event removed");
                if(db.getAllEventRows() == null)  //Goes to the monthview if no events exist
                    startActivity(new Intent(this, MonthViewController.class));
            finish();
        } else
            toast("Event already removed");
    }

    private void toast(String description) {
        Toast.makeText(getApplicationContext(), description, Toast.LENGTH_LONG).show();
    }

}
