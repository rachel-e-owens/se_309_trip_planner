package com.coms309.a309front_end;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.util.Pair;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Class to allow users to pick a Date Range
 * @author Rachel Owens
 * utilizes Material IO DateRangePicker
 */
public class DatePicker extends AppCompatActivity {

    Calendar calendar;
    int year, month, day;
    static String dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        //get Calendar instance current
        calendar = Calendar.getInstance(TimeZone.getTimeZone("CST"));
        calendar.clear();



        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //Long today = MaterialDatePicker.todayInUtcMilliseconds();
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();
        MaterialDatePicker picker = builder.build();
        builder.setTitleText("Select Trip Dates");
        picker.show(getSupportFragmentManager(), picker.toString());

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long,Long>>() {

            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                Date startDate = new Date(selection.first);
                Date endDate = new Date(selection.second);

                startDate.setMinutes(startDate.getMinutes() + startDate.getTimezoneOffset()); // https://github.com/angular-ui/bootstrap/issues/2628
                endDate.setMinutes(endDate.getMinutes() + endDate.getTimezoneOffset());

                String startDateString = DateFormat.format("MM/dd/yy", startDate).toString();
                String endDateString = DateFormat.format("MM/dd/yy", endDate).toString();

                //edittext.setText(startDateString + " - " + endDateString);
                dates = startDateString + " - " + endDateString;

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", dates);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                //Toast.makeText(getApplicationContext(), startDateString, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static void getDate(EditText editText) {
        editText.setText(dates);
    }
}