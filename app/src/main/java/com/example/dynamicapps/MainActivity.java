package com.example.dynamicapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button drinkButton;
    private Button foodButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drinkButton = (Button)findViewById(R.id.drinkTimerButton);
        drinkButton.setOnClickListener(view -> openDrinkTimer());
        foodButton = (Button)findViewById(R.id.foodTimerButton);
        foodButton.setOnClickListener(view -> openFoodTimer());
    }

    public void openDrinkTimer(){
        Intent intent = new Intent(this, DrinkTimer.class);
        startActivity(intent);
    }
    public void openFoodTimer(){
        Intent intent = new Intent(this, FoodTimer.class);
        startActivity(intent);
    }

}