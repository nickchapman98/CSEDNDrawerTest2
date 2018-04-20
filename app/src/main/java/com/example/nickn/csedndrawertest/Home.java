package com.example.nickn.csedndrawertest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Home extends Fragment{
    FragmentManager fragmentManager = getFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

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