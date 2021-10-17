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

public class FoodTimer extends AppCompatActivity {
    private EditText mFoodEditTextInput;
    private TextView mTextViewFoodTimer;

    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mFoodCountDownTimer;

    private boolean mFoodTimerRunning;
    private boolean mFoodTimerFinished;

    private long mFoodStartTimeInMillis;
    private long mFoodTimeLeftInMillis;
    private long mFoodEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_timer);

        Button mHomePageButton;

        mHomePageButton = findViewById(R.id.to_home_page);
        mHomePageButton.setOnClickListener(view -> openHomePage());

        mFoodEditTextInput = findViewById(R.id.food_edit_text_input);
        mTextViewFoodTimer = findViewById(R.id.textFoodTimer);

        mButtonSet = findViewById(R.id.food_button_set);
        mButtonStartPause = findViewById(R.id.food_button_start_pause);
        mButtonReset = findViewById(R.id.food_button_reset);

        mButtonSet.setOnClickListener(view -> {
            String input = mFoodEditTextInput.getText().toString();
            if(input.length() == 0){
                Toast.makeText(FoodTimer.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            long millisInput = Long.parseLong(input) * 60000;
            if(millisInput == 0){
                Toast.makeText(FoodTimer.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                return;
            }

            setTime(millisInput);
            mFoodEditTextInput.setText("");
        });

        mButtonStartPause.setOnClickListener(view -> {
            if(mFoodTimerRunning){
                pauseTimer();
            } else{
                startTimer();
            }
        });

        mButtonReset.setOnClickListener((view) -> resetTimer());
    }

    private void setTime(long milliseconds){
        mFoodStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer(){
        mFoodEndTime = System.currentTimeMillis() + mFoodTimeLeftInMillis;
        mFoodCountDownTimer = new CountDownTimer(mFoodTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mFoodTimeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                mFoodTimerRunning = false;
                mFoodTimerFinished = true;
                updateWatchInterface();
            }
        }.start();
        mFoodTimerRunning = true;
        mFoodTimerFinished = false;
        updateWatchInterface();
    }

    private void pauseTimer(){
        mFoodCountDownTimer.cancel();
        mFoodTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer(){
        mFoodTimeLeftInMillis = mFoodStartTimeInMillis;
        mFoodTimerFinished = false;
        updateCountdownText();
        updateWatchInterface();
    }

    private void updateCountdownText(){
        int hours = (int) (mFoodTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mFoodTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mFoodTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if(hours > 0){
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        }else{
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        mTextViewFoodTimer.setText(timeLeftFormatted);
    }

    private void updateWatchInterface(){
        if (mFoodTimerRunning){
            mFoodEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        }else{
            mFoodEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");

            if(mFoodTimeLeftInMillis < 1000){
                mButtonStartPause.setVisibility(View.INVISIBLE);
            }
            else{
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if(mFoodTimeLeftInMillis < mFoodStartTimeInMillis){
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

        editor.putLong("foodStartTimeInMillis", mFoodStartTimeInMillis);
        editor.putLong("foodMillisLeft", mFoodTimeLeftInMillis);
        editor.putBoolean("foodTimerRunning", mFoodTimerRunning);
        editor.putLong("foodEndTime", mFoodEndTime);

        editor.apply();
        if(mFoodCountDownTimer != null){
            mFoodCountDownTimer.cancel();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mFoodStartTimeInMillis = prefs.getLong("foodStartTimeInMillis", 5400000);
        mFoodTimeLeftInMillis = prefs.getLong("foodMillisLeft", mFoodStartTimeInMillis);
        mFoodTimerRunning = prefs.getBoolean("foodTimerRunning", false);

        updateCountdownText();
        updateWatchInterface();
        if(mFoodTimerRunning){
            mFoodEndTime = prefs.getLong("foodEndTime", 0);
            mFoodTimeLeftInMillis = mFoodEndTime - System.currentTimeMillis();
            if(mFoodTimeLeftInMillis < 0){
                mFoodTimeLeftInMillis = 0;
                mFoodTimerRunning = false;

                updateCountdownText();
                updateWatchInterface();
            }else{
                startTimer();
            }
        }
    }
}