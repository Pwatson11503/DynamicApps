package com.example.dynamicapps.MealInfo.Model;


import java.io.Serializable;

public class Food implements Serializable{
    private String foodItems, recordDate;
    private int calories;
    private int carbs;
    private int protein;
    private int foodId;
    private int mealNumber;
    private static final long serialVersionID = 10L;

    public Food(){}

    public Food(String foodItems, String recordDate, int calories, int carbs, int protein, int foodId, int mealNumber){
        this.foodItems = foodItems;
        this.recordDate = recordDate;
        this.calories = calories;
        this.carbs = carbs;
        this.protein = protein;
        this.foodId = foodId;
        this.mealNumber = mealNumber;
    }
    public int getMealNumber() {
        return mealNumber;
    }

    public void setMealNumber(int mealNumber) {
        this.mealNumber = mealNumber;
    }

    public static long getSerialVersionID() {
        return serialVersionID;
    }

    public String getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(String foodItems) {
        this.foodItems = foodItems;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }
}
