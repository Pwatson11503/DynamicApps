package com.example.dynamicapps.MealInfo;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamicapps.MainActivity;
import com.example.dynamicapps.MealInfo.Data.CustomDataAdapter;
import com.example.dynamicapps.MealInfo.Data.DatabaseHandler;
import com.example.dynamicapps.MealInfo.Model.Food;
import com.example.dynamicapps.R;

import java.util.ArrayList;

public class TrackerDetails extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<Food> foodArrayList = new ArrayList<>();
    private CustomDataAdapter foodAdapter;
    private ListView listView;
    private Food myFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker_details);
        Button mHomePageButton;

        mHomePageButton = findViewById(R.id.to_home_page);
        mHomePageButton.setOnClickListener(view -> openHomePage());
        listView = findViewById(R.id.listView);

        refreshData();
    }

    private void refreshData() {
        foodArrayList.clear();
        dba = new DatabaseHandler(getApplicationContext());
        ArrayList<Food> foodsFromDB = dba.getAllFood();

        for (int i = 0; i < foodsFromDB.size(); i++) {
            int mealNumber = foodsFromDB.get(i).getMealNumber();
            String foodItems = foodsFromDB.get(i).getFoodItems();
            int calories = foodsFromDB.get(i).getCalories();
            int carbs = foodsFromDB.get(i).getCarbs();
            int protein = foodsFromDB.get(i).getProtein();
            String date = foodsFromDB.get(i).getRecordDate();
            int foodId = foodsFromDB.get(i).getFoodId();

            myFood = new Food();
            myFood.setMealNumber(mealNumber);
            myFood.setFoodItems(foodItems);
            myFood.setCalories(calories);
            myFood.setCarbs(carbs);
            myFood.setProtein(protein);
            myFood.setRecordDate(date);
            myFood.setFoodId(foodId);

            foodArrayList.add(myFood);
        }
        dba.close();

        //setup Adapter
        foodAdapter = new CustomDataAdapter(TrackerDetails.this, R.layout.list_row, foodArrayList);
        listView.setAdapter(foodAdapter);
        foodAdapter.setNotifyOnChange(true);
    }

    public void openHomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
