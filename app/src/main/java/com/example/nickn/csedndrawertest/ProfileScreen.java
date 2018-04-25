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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ProfileScreen extends Fragment{

    View myView;
    TextInputEditText nameInput;
    TextInputEditText dobInput;
    TextInputEditText heightInput;
    TextInputEditText weightInput;
    TextInputEditText goalWeightInput;
    Spinner genderSpinner;
    User userProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_profile, container, false);
        userProfile = new UI().getProfile();
        nameInput = (TextInputEditText) myView.findViewById(R.id.nameInput);
        dobInput = (TextInputEditText) myView.findViewById(R.id.dobInput);
        heightInput = (TextInputEditText) myView.findViewById(R.id.heightInput);
        weightInput = (TextInputEditText) myView.findViewById(R.id.weightInput);
        goalWeightInput = (TextInputEditText) myView.findViewById(R.id.goalWeightInput);

        Button SaveProfileButton = (Button) myView.findViewById(R.id.saveChangesButton);
        SaveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UI().addProfile(nameInput.getText().toString(), genderSpinner.getSelectedItem().toString().toLowerCase(), Integer.parseInt(heightInput.getText().toString()),
                        Integer.parseInt(weightInput.getText().toString()), dobInput.getText().toString(), Integer.parseInt(goalWeightInput.getText().toString()));
                Toast.makeText(getContext(), "Profile updated.", Toast.LENGTH_SHORT).show();
            }
        });

        nameInput.setText(userProfile.getName().toString());
        dobInput.setText(userProfile.getDOB().toString());
        heightInput.setText(String.valueOf(userProfile.getHeight()));
        weightInput.setText(String.valueOf(userProfile.getWeight()));
        goalWeightInput.setText(String.valueOf(userProfile.getTargetWeight()));

        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        genderSpinner = (Spinner) getView().findViewById(R.id.spinner3);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.GenderOptions, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        genderSpinner.setAdapter(adapter);

        if (userProfile.getSex().toString().equalsIgnoreCase("male")) {
            genderSpinner.setSelection(0);
            System.err.println("Get male");
        } else if (userProfile.getSex().toString().equalsIgnoreCase("female")) {
            genderSpinner.setSelection(1);
            System.err.println("Get female");
        } else {
            genderSpinner.setSelection(2);
            System.err.println("wtf");
        }

    }
}