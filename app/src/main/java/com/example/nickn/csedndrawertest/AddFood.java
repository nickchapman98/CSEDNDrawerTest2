package com.example.nickn.csedndrawertest;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddFood extends Fragment{
    TextInputEditText calorieEntry;
    View myView;
    Spinner spinner;
    TextInputEditText foodEntry;
    UI theUI = new UI();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_add_food, container, false);

        foodEntry = (TextInputEditText) myView.findViewById(R.id.FoodEntry);
        calorieEntry = (TextInputEditText) myView.findViewById(R.id.calorieEntry);


        Button SubmitFood = (Button) myView.findViewById(R.id.submitFoodButton);
        SubmitFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int calories = 0;
                if (foodEntry.getText().toString().equals("")) {
                    calories = Integer.parseInt(calorieEntry.getText().toString());
                    theUI.addFood(calories);
                    Toast.makeText(getContext(), "Food added.", Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "Existing meal used.", Toast.LENGTH_SHORT).show();
                } else {
                    calories = Integer.parseInt(calorieEntry.getText().toString());
                    theUI.addFood(calories);
                    theUI.addMeal(foodEntry.getText().toString(), calories);
                    Toast.makeText(getContext(), "Food added.", Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "New meal saved for future use.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<Meal> meals = new UI().Meals();
        ArrayList<String> theMeals = new ArrayList<String>();

        for (int i=0; i<meals.size(); i++) {
            theMeals.add(meals.get(i).getName());
        }

        spinner = (Spinner) getView().findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, theMeals);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String  mselection=spinner.getSelectedItem().toString();
                Toast.makeText(getContext(), "Selected "+ mselection, Toast.LENGTH_SHORT).show();
                int calorieAmount = meals.get(spinner.getSelectedItemPosition()).getCalories();
                calorieEntry.setText(String.valueOf(calorieAmount));
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //
            }
        });

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
}