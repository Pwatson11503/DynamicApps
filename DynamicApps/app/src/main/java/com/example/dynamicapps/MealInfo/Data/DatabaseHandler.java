package com.example.dynamicapps.MealInfo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.dynamicapps.MealInfo.Model.Food;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final ArrayList<Food> foodList = new ArrayList<>();

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = " CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.MEAL_NUMBER + " INT, "
                + Constants.FOOD + " TEXT, "
                + Constants.CALORIES + " INT, "
                + Constants.CARBS + " INT, "
                + Constants.PROTEIN + " INT, "
                + Constants.DATE_ADDED + " LONG );";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //Add Food Method
    public void addFood(Food food){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.MEAL_NUMBER, food.getMealNumber());
        values.put(Constants.FOOD, food.getFoodItems());
        values.put(Constants.CALORIES, food.getCalories());
        values.put(Constants.CARBS, food.getCarbs());
        values.put(Constants.PROTEIN, food.getProtein());
        values.put(Constants.DATE_ADDED, System.currentTimeMillis());

        database.insert(Constants.TABLE_NAME, null, values);
        Log.d("Added Food Item", "YES");
        database.close();
    }

    public ArrayList<Food> getAllFood(){
        foodList.clear();
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.MEAL_NUMBER, Constants.FOOD,
                        Constants.CALORIES, Constants.CARBS, Constants.PROTEIN, Constants.DATE_ADDED},
                null, null, null, null, Constants.DATE_ADDED + " DESC ");

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                int meal_number = cursor.getColumnIndex(Constants.MEAL_NUMBER);
                food.setMealNumber(cursor.getInt(meal_number));
                //food.setMealNumber(cursor.getInt(cursor.getColumnIndex(Constants.MEAL_NUMBER)));

                int food_items = cursor.getColumnIndex(Constants.FOOD);
                food.setFoodItems(cursor.getString(food_items));
                //food.setFoodItems(cursor.getString(cursor.getColumnIndex(Constants.FOOD)));

                int calories = (cursor.getColumnIndex(Constants.CALORIES));
                food.setCalories(cursor.getInt(calories));
                //food.setCalories(cursor.getInt(cursor.getColumnIndex(Constants.CALORIES)));

                int carbs = cursor.getColumnIndex(Constants.CARBS);
                food.setCarbs(cursor.getInt(carbs));
                //food.setCarbs(cursor.getInt(cursor.getColumnIndex(Constants.CARBS)));

                int protein = cursor.getColumnIndex(Constants.PROTEIN);
                food.setProtein(cursor.getInt(protein));
                //food.setProtein(cursor.getInt(cursor.getColumnIndex(Constants.PROTEIN)));

                int keyId = cursor.getColumnIndex(Constants.KEY_ID);
                food.setFoodId(cursor.getInt(keyId));
                //food.setFoodId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                int dateColIndex = cursor.getColumnIndex(Constants.DATE_ADDED);
                String date = dateFormat.format(new Date(cursor.getLong(dateColIndex)).getTime());
                food.setRecordDate(date);


                foodList.add(food);

            } while (cursor.moveToNext());
        }
        cursor.close();
        dba.close();
        return foodList;
    }

}
