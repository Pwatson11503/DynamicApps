package com.example.dynamicapps.Calendar.Weekly;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dynamicapps.Calendar.Utils.CalendarUtils;
import com.example.dynamicapps.R;

import java.time.LocalTime;
import java.util.Locale;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameEditText;
    private TextView eventDateTextView, eventTimeTextView;
    private Button timeButton;
    int eventHour, eventMinute;

    private LocalTime time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        initWidgets();
        eventDateTextView.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
    }

    private void initWidgets() {
        eventNameEditText = findViewById(R.id.eventNameEditText);
        eventDateTextView = findViewById(R.id.eventDateTextView);
        eventTimeTextView = findViewById(R.id.eventTimeTextView);
        timeButton = findViewById(R.id.eventTimeButton);
    }

    public void saveEventAction(View view) {
        String eventName = eventNameEditText.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener listener = (timePicker, selectedHour, selectedMinute) -> {
            eventHour = selectedHour;
            eventMinute = selectedMinute;
            time = LocalTime.of(eventHour, eventMinute);
            eventTimeTextView.setText("Time: " + time);

        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, listener, eventHour, eventMinute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}