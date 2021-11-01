package com.example.dynamicapps.MealInfo.Data;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamicapps.MealInfo.Model.Food;
import com.example.dynamicapps.R;

import java.util.ArrayList;

public class CustomDataAdapter extends ArrayAdapter<Food> {
    private int layoutResource;
    private Activity activity;
    private ArrayList<Food> foodArrayList;

    public CustomDataAdapter(@NonNull Activity act, int resource, ArrayList<Food> data) {
        super(act, resource, data);
        layoutResource = resource;
        activity = act;
        foodArrayList = data;

        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return foodArrayList.size();
    }

    @Nullable
    @Override
    public Food getItem(int position){
        return foodArrayList.get(position);
    }

    @Override
    public int getPosition(@Nullable Food item){
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position){
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View row = convertView;
        ViewHolder holder;
        if(row == null || (row.getTag()) == null){
            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource, null);
            holder = new ViewHolder();
            holder.foodItems = row.findViewById(R.id.foodList);
            holder.foodCalories = row.findViewById(R.id.calories);
            holder.foodChol = row.findViewById(R.id.cholesterol);
            holder.foodProtein = row.findViewById(R.id.protein);
            holder.foodDate = row.findViewById(R.id.dateText);

            row.setTag(holder);
        }else{
            holder = (ViewHolder) row.getTag();
        }

        holder.food = getItem(position);
        assert holder.food != null;
        holder.foodItems.setText(String.valueOf(holder.food.getFoodItems()));
        holder.foodCalories.setText(String.valueOf(holder.food.getCalories()));
        holder.foodChol.setText(String.valueOf(holder.food.getCholesterol()));
        holder.foodProtein.setText(String.valueOf(holder.food.getProtein()));
        holder.foodDate.setText(String.valueOf(holder.food.getRecordDate()));

        return row;
    }

    public class ViewHolder{
        Food food;
        TextView foodItems, foodCalories, foodChol, foodProtein, foodDate;
    }
}
