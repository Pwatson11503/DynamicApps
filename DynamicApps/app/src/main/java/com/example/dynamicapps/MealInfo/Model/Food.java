package com.example.dynamicapps.MealInfo.Model;


import java.io.Serializable;

public class Food implements Serializable{
    private String foodItems, recordDate;
    private int calories;
    private int cholesterol;
    private int protein;
    private int foodId;
    private static final long serialVersionID = 10L;

    public Food(){}

    public Food(String foodItems, String recordDate, int calories, int cholesterol, int protein, int foodId){
        this.foodItems = foodItems;
        this.recordDate = recordDate;
        this.calories = calories;
        this.cholesterol = cholesterol;
        this.protein = protein;
        this.foodId = foodId;
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

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
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
