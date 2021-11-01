package com.example.dynamicapps.MealInfo;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicapps.MainActivity;
import com.example.dynamicapps.MealInfo.Data.DatabaseHandler;
import com.example.dynamicapps.MealInfo.Model.Food;
import com.example.dynamicapps.R;

public class MealInfoMain extends AppCompatActivity {

    private EditText foodItems, caloriesInput, proteinInput, cholInput;
    private Button submitButton;
    private DatabaseHandler dba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_info_main);

        Button mHomePageButton;

        mHomePageButton = findViewById(R.id.to_home_page);
        mHomePageButton.setOnClickListener(view -> openHomePage());

        dba = new DatabaseHandler(MealInfoMain.this);
        foodItems = findViewById(R.id.FoodItems);
        caloriesInput = findViewById(R.id.CaloriesInput);
        proteinInput = findViewById(R.id.ProteinInput);
        cholInput = findViewById(R.id.CholInput);
        submitButton = findViewById(R.id.SubmitButton);

        submitButton.setOnClickListener(view -> saveDataToDatabase());
    }

    private void openTrackerDetails(){
        Intent intent = new Intent(this, TrackerDetails.class);
        startActivity(intent);
    }

    private void saveDataToDatabase(){
        Food food = new Food();
        String foodString = foodItems.getText().toString().trim();
        String calString = caloriesInput.getText().toString().trim();
        String cholString = cholInput.getText().toString().trim();
        String proteinString = proteinInput.getText().toString().trim();

        if(foodString.isEmpty() || calString.isEmpty() || cholString.isEmpty() || proteinString.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a value.", Toast.LENGTH_LONG).show();
        }else{
            int calInt = Integer.parseInt(calString);
            int cholInt = Integer.parseInt(cholString);
            int proteinInt = Integer.parseInt(proteinString);

            food.setFoodItems(foodString);
            food.setCalories(calInt);
            food.setCholesterol(cholInt);
            food.setProtein(proteinInt);

            dba.addFood(food);
            dba.close();

            foodItems.setText("");
            caloriesInput.setText("");
            cholInput.setText("");
            proteinInput.setText("");

            openTrackerDetails();

        }
    }

    public void openHomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}