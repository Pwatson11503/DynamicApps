package com.example.dynamicapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class DrinkTimer extends AppCompatActivity {
    private EditText mDrinkEditTextInput;
    private TextView mTextViewDrinkTimer;

    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mDrinkCountDownTimer;

    private boolean mDrinkTimerRunning;

    private long mDrinkStartTimeInMillis;
    private long mDrinkTimeLeftInMillis;
    private long mDrinkEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_timer);
        Button mHomePageButton;


        mHomePageButton = (Button)findViewById(R.id.to_home_page);
        mHomePageButton.setOnClickListener(view -> openHomePage());

        mDrinkEditTextInput = findViewById(R.id.drink_edit_text_input);
        mTextViewDrinkTimer = findViewById(R.id.textDrinkTimer);

        mButtonSet = findViewById(R.id.drink_button_set);
        mButtonStartPause = findViewById(R.id.drink_button_start_pause);
        mButtonReset = findViewById(R.id.drink_button_reset);

        mButtonSet.setOnClickListener(view -> {
            String input = mDrinkEditTextInput.getText().toString();
            if(input.length() == 0){
                Toast.makeText(DrinkTimer.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            long millisInput = Long.parseLong(input) * 60000;
            if(millisInput == 0){
                Toast.makeText(DrinkTimer.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                return;
            }

            setTime(millisInput);
            mDrinkEditTextInput.setText("");
        });

        mButtonStartPause.setOnClickListener(view -> {
            if(mDrinkTimerRunning){
                pauseTimer();
            } else{
                startTimer();
            }
        });

        mButtonReset.setOnClickListener((view) -> resetTimer());
    }

    private void setTime(long milliseconds){
        mDrinkStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer(){
        mDrinkEndTime = System.currentTimeMillis() + mDrinkTimeLeftInMillis;
        mDrinkCountDownTimer = new CountDownTimer(mDrinkTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mDrinkTimeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                mDrinkTimerRunning = false;
                updateWatchInterface();
            }
        }.start();
        mDrinkTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer(){
        mDrinkCountDownTimer.cancel();
        mDrinkTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer(){
        mDrinkTimeLeftInMillis = mDrinkStartTimeInMillis;
        updateCountdownText();
        updateWatchInterface();
    }

    private void updateCountdownText(){
        int hours = (int) (mDrinkTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mDrinkTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mDrinkTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if(hours > 0){
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        }else{
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        mTextViewDrinkTimer.setText(timeLeftFormatted);
    }

    private void updateWatchInterface(){
        if (mDrinkTimerRunning){
            mDrinkEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        }else{
            mDrinkEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");

            if(mDrinkTimeLeftInMillis < 1000){
                mButtonStartPause.setVisibility(View.INVISIBLE);
            }
            else{
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if(mDrinkTimeLeftInMillis < mDrinkStartTimeInMillis){
                mButtonReset.setVisibility(View.VISIBLE);
            }else{
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void openHomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("drinkStartTimeInMillis", mDrinkStartTimeInMillis);
        editor.putLong("drinkMillisLeft", mDrinkTimeLeftInMillis);
        editor.putBoolean("drinkTimerRunning", mDrinkTimerRunning);
        editor.putLong("drinkEndTime", mDrinkEndTime);

        editor.apply();

        if(mDrinkCountDownTimer != null){
            mDrinkCountDownTimer.cancel();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mDrinkStartTimeInMillis = prefs.getLong("drinkStartTimeInMillis", 900000);
        mDrinkTimeLeftInMillis = prefs.getLong("drinkMillisLeft", mDrinkStartTimeInMillis);
        mDrinkTimerRunning = prefs.getBoolean("drinkTimerRunning", false);

        updateCountdownText();
        updateWatchInterface();

        if(mDrinkTimerRunning){
            mDrinkEndTime = prefs.getLong("drinkEndTime", 0);
            mDrinkTimeLeftInMillis = mDrinkEndTime - System.currentTimeMillis();
            if(mDrinkTimeLeftInMillis < 0){
                mDrinkTimeLeftInMillis = 0;
                mDrinkTimerRunning = false;
                updateCountdownText();
                updateWatchInterface();
            }else{
                startTimer();
            }
        }
    }
}

    /*
    This method gets reworked in the onStop method.
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", mTimeLeftInMillis);
        outState.putBoolean("timerRunning", mTimerRunning);
        outState.putLong("endTime", mEndTime);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
        updateCountdownText();
        updateButtons();

        if(mTimerRunning){
            mEndTime = savedInstanceState.getLong("endTime");
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            startTimer();
        }
    }*/