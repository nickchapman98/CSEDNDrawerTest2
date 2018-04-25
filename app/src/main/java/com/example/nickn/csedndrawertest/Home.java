package com.example.nickn.csedndrawertest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.math.*;

public class Home extends Fragment{
    FragmentManager fragmentManager = getFragmentManager();
    UI theUI = new UI();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        TextView GoalLabel = (TextView) view.findViewById(R.id.CalorieGoal);
        TextView CaloriesIn = (TextView) view.findViewById(R.id.CaloriesIn);
        TextView CaloriesLeft = (TextView) view.findViewById(R.id.CaloriesLeft);

        GoalLabel.setText(String.valueOf(theUI.getDailyCalories()));
        CaloriesIn.setText(String.valueOf(theUI.caloriesToday()));
        CaloriesLeft.setText(String.valueOf((theUI.caloriesToday() - theUI.getDailyCalories())*-1));

        Button AddFoodButton = (Button) view.findViewById(R.id.addFoodButton);
        AddFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFood fragment = new AddFood();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragment).commit();
            }
        });

        Button AddExerciseButton = (Button) view.findViewById(R.id.addExerciseButton);
        AddExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddExercise fragment = new AddExercise();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragment).commit();
            }
        });

        return view;
    }
}