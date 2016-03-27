package team5.calendarproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;

//Android Studio Comment time.
public class MonthViewController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_view);
    }

    LinearLayout week_1 = (LinearLayout) findViewById(R.id.monthly_week_1);
    LinearLayout week_2 = (LinearLayout) findViewById(R.id.monthly_week_2);
    LinearLayout week_3 = (LinearLayout) findViewById(R.id.monthly_week_3);
    View week_4 = (View) findViewById(R.id.monthly_week_4);
    LinearLayout week_5 = (LinearLayout) findViewById(R.id.monthly_week_5);

}
