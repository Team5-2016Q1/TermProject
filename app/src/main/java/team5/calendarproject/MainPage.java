package team5.calendarproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

//Android Studio Comment time.
public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    LinearLayout week_1 = (LinearLayout) findViewById(R.id.monthly_week_1);
    LinearLayout week_2 = (LinearLayout) findViewById(R.id.monthly_week_2);
    LinearLayout week_3 = (LinearLayout) findViewById(R.id.monthly_week_3);
    LinearLayout week_4 = (LinearLayout) findViewById(R.id.monthly_week_4);
    LinearLayout week_5 = (LinearLayout) findViewById(R.id.monthly_week_5);

}
