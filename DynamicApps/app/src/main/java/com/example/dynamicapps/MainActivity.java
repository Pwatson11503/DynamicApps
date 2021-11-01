package com.example.dynamicapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.dynamicapps.Calendar.MainCalendar;
import com.example.dynamicapps.MealInfo.MealInfoMain;
import com.example.dynamicapps.MealInfo.TrackerDetails;
import com.example.dynamicapps.Timer.DrinkTimer;
import com.example.dynamicapps.Timer.FoodTimer;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button drinkButton;
        Button foodButton;
        Button mealInputButton;
        Button mealInfoButton;
        Button calendarButton;

        drinkButton = (Button)findViewById(R.id.drinkTimerButton);
        drinkButton.setOnClickListener(view -> openDrinkTimer());
        foodButton = (Button)findViewById(R.id.foodTimerButton);
        foodButton.setOnClickListener(view -> openFoodTimer());
        mealInputButton = (Button)findViewById(R.id.mealInputButton);
        mealInputButton.setOnClickListener(view -> openMealTracker());
        mealInfoButton = (Button)findViewById(R.id.mealInfoButton);
        mealInfoButton.setOnClickListener(view -> openMealInfo());
        calendarButton = (Button)findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(view -> openCalendarReminder());

    }

    private void openCalendarReminder() {
        Intent intent = new Intent(this, MainCalendar.class);
        startActivity(intent);
    }

    public void openDrinkTimer(){
        Intent intent = new Intent(this, DrinkTimer.class);
        startActivity(intent);
    }
    public void openFoodTimer(){
        Intent intent = new Intent(this, FoodTimer.class);
        startActivity(intent);
    }

    public void openMealTracker(){
        Intent intent = new Intent(this, MealInfoMain.class);
        startActivity(intent);
    }

    public void openMealInfo(){
        Intent intent = new Intent(this, TrackerDetails.class);
        startActivity(intent);

    }

}