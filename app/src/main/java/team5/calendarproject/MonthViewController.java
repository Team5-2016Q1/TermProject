package team5.calendarproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

//Android Studio Comment time.
public class MonthViewController extends AppCompatActivity {

    //private Button MontlyAddEventButton = (Button) findViewById(R.id.monthly_add_event_button);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_view);
        setupMonth();
    }

    private void setupMonth() {
        Date today = new Date();
        int totalDaysInMonth = 30;
        int setupDayNumber = 1;

        int dayIDs[] = {R.id.weekly_sunday, R.id.weekly_monday, R.id.weekly_tuesday,
                R.id.weekly_wednesday, R.id.weekly_thursday, R.id.weekly_friday, R.id.weekly_saturday};

        int weekIDs[] = {R.id.monthly_week_1, R.id.monthly_week_2, R.id.monthly_week_3,
                R.id.monthly_week_4, R.id.monthly_week_5};

        String dayNames[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        View workingDay;
        TextView monthName = (TextView)findViewById(R.id.monthly_view_month_name);
        monthName.setText("April");

        for(int weekNumber = 0; weekNumber < 5; weekNumber++) {
            for(int dayNumber = 0; dayNumber < 7; dayNumber++) {
                workingDay = (View)findViewById(weekIDs[weekNumber]).findViewById(dayIDs[dayNumber]);

                TextView dayName = (TextView)workingDay.findViewById(R.id.day_name);
                dayName.setText(dayNames[dayNumber] + " " + setupDayNumber);
                setupDayNumber++;
                if(setupDayNumber == totalDaysInMonth) setupDayNumber = 1;
            }
        }

    }

}
