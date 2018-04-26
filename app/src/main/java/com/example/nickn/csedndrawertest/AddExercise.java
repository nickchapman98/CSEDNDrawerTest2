package com.example.nickn.csedndrawertest;

import android.app.AlertDialog;
import android.app.Fragment;
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

public class AddExercise extends Fragment {
    TextInputEditText calorieBox;
    View myView;
    Spinner spinner;
    UI theUI = new UI();
    TextInputEditText exerciseEntry;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_add_exercise, container, false);
        calorieBox = (TextInputEditText) myView.findViewById(R.id.calorieBox);
        exerciseEntry = (TextInputEditText) myView.findViewById(R.id.ExerciseEntry);
        Button SubmitExercise = (Button) myView.findViewById(R.id.addExerciseButton);
        SubmitExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int calories = 0;
                if (exerciseEntry.getText().toString().equals("")) {
                    calories = Integer.parseInt(calorieBox.getText().toString());
                    theUI.addFood(calories * -1);
                    Toast.makeText(getContext(), "Exercise added.", Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "Existing exercise used.", Toast.LENGTH_SHORT).show();
                } else {
                    calories = Integer.parseInt(calorieBox.getText().toString());
                    theUI.addFood(calories * -1);
                    theUI.addExercise(exerciseEntry.getText().toString(), calories);
                    Toast.makeText(getContext(), "Exercise added.", Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "New exercise saved for future use.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<Meal> exercises = new UI().Exercises();
        ArrayList<String> theExercises = new ArrayList<String>();

        for (int i = 0; i < exercises.size(); i++) {
            theExercises.add(exercises.get(i).getName());
        }

        spinner = (Spinner) getView().findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, theExercises);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String mselection = spinner.getSelectedItem().toString();
                Toast.makeText(getContext(), "Selected " + mselection, Toast.LENGTH_SHORT).show();
                int calorieAmount = exercises.get(spinner.getSelectedItemPosition()).getCalories();
                calorieBox.setText(String.valueOf(calorieAmount));
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