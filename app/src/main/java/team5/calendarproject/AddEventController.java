package team5.calendarproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddEventController extends AppCompatActivity {
    private Button btnCreate, btnCancel;
    private EditText etEventTitle, etEventDate, etEventLocation, etEventEndTime, etEventStartTime,
            etEmail1, etEmail2;
    private CheckBox alarm1, alarm2, alarm3;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        openDB();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_view);

        makeButtonsWork();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createNewEvent();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openDB() {
        db = new Database(this);
        db.open();
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


    private void makeButtonsWork() {

        btnCancel = (Button)findViewById(R.id.cancelButton);
        btnCreate = (Button)findViewById(R.id.createButton);
        etEventTitle = (EditText) findViewById(R.id.et_EventTilte);
        etEventDate = (EditText) findViewById(R.id.et_EventDate);
        etEventLocation = (EditText) findViewById(R.id.et_Location);
        etEventStartTime = (EditText) findViewById(R.id.et_EventStartTime);
        etEventEndTime = (EditText) findViewById(R.id.et_AddEvent_EndTime);
        etEmail1 = (EditText) findViewById(R.id.et_email1);
        etEmail2 = (EditText) findViewById(R.id.et_email2);
        alarm1 = (CheckBox) findViewById(R.id.checkBoxEmail);
        alarm2 = (CheckBox) findViewById(R.id.checkBoxAlarm);
        alarm3 = (CheckBox) findViewById(R.id.checkBoxSilent);

    }

    private void Cancel() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), MonthViewController.class);
                startActivity(back);
            }
        });
    }

    public void createNewEvent() {


        String title = etEventTitle.getText().toString();
        String date = etEventDate.getText().toString();

        int time = 0;
        if (!etEventStartTime.getText().toString().isEmpty())
            time = new Integer(etEventStartTime.getText().toString());

        int endTime = 0;
        if (!etEventEndTime.getText().toString().isEmpty())
            endTime = new Integer(etEventEndTime.getText().toString());

        String color = "blue";
        int alarm1;
        int alarm2;
        int alarm3;
        int repeating = 0;
        String location = etEventLocation.getText().toString();
        String participants= etEmail1.getText().toString() + " " + etEmail2.getText().toString();


        //TODO: initialize each item, uncomment db.insertEventRow

        if(this.alarm1.isChecked() == true) {
            alarm1 = 1;
        } else {
            alarm1 = 0;
        }
        if(this.alarm2.isChecked() == true) {
            alarm2 = 1;
        } else {
            alarm2 = 0;
        }
        if(this.alarm3.isChecked() == true) {
            alarm3 = 1;
        } else {
            alarm3 = 0;
        }

        db.insertEventRow(title, date, time, endTime, color, alarm1, alarm2, alarm3, repeating,
                location, participants);

        //Maybe go to event view
        toast("Event " + title + " saved.");
    }

    private void toast(String description) {
        Toast.makeText(getApplicationContext(), description, Toast.LENGTH_LONG).show();
    }
}
