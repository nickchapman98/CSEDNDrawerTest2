package com.example.nickn.csedndrawertest;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WeightTracker extends Fragment{
    View myView;
    GraphView graph;
    UI theUI = new UI();
    TextView currentWeight;
    Button changeGraph;
    Spinner typeSpinner;
    Spinner scaleSpinner;
    LineGraphSeries<DataPoint> series;
    LineGraphSeries<DataPoint> series2;
    TextView MotivationText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_weight_tracker, container, false);

        currentWeight = (TextView) myView.findViewById(R.id.currentWeight);
        currentWeight.setText("Current weight: " + String.valueOf(theUI.getProfile().getWeight()) + "kg");
        changeGraph = (Button) myView.findViewById(R.id.updateGraph);
        changeGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentWeight.setText("Current weight: " + String.valueOf(theUI.getProfile().getWeight()) + "kg");
                boolean isWeightType = false;
                if (typeSpinner.getSelectedItemPosition() == 0) {
                    isWeightType = true;
                    MotivationText.setVisibility(View.INVISIBLE);
                } else if (typeSpinner.getSelectedItemPosition() == 1) {
                    isWeightType = false;
                    MotivationText.setVisibility(View.VISIBLE);
                }

                int noOfDays = 0;
                if (scaleSpinner.getSelectedItemPosition() == 0) {
                    noOfDays = 7;
                } else if (scaleSpinner.getSelectedItemPosition() == 1) {
                    noOfDays = 31;
                }

                ArrayList<Meal> graphPoints = theUI.graph(isWeightType, noOfDays);
                graph.removeAllSeries();

                int[] items = new int[noOfDays];
                DataPoint[] dp = new DataPoint[noOfDays];

                for (int i = graphPoints.size()-1; i >= 0; i--) {
                    items[i] = graphPoints.get(i).getCalories();
                    dp[graphPoints.size()-i-1] = new DataPoint(graphPoints.size()-i-1, items[i]);
                }

                series = new LineGraphSeries<>(dp);

                int target;
                if (isWeightType) {
                    target = theUI.getProfile().getTargetWeight();
                } else {
                    target = theUI.getDailyCalories();
                }

                series2 = new LineGraphSeries<>(new DataPoint[] {
                        new DataPoint(0, target),
                        new DataPoint(noOfDays-1, target)
                });

                series2.setColor(Color.GREEN);

                graph.addSeries(series);
                graph.addSeries(series2);

                GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Days");

                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(noOfDays-1);

                graph.getViewport().setXAxisBoundsManual(true);

                boolean losingWeight = false;

                if (theUI.getProfile().getWeight() > theUI.getProfile().getTargetWeight()) {
                    losingWeight = true;
                }

                int noOfGoalsExceeded = 0;

                for (int i = 0; i<7; i++) {
                    System.out.println(items[i]);
                    if (items[i] > theUI.getProfile().getTargetWeight()) {
                        noOfGoalsExceeded++;
                    }
                }

                String feedback = "";

                if (losingWeight) {
                    if (noOfGoalsExceeded > 3) {
                        feedback = "Well done! Keep it up.";
                    } else {
                        feedback = "Almost there... aim to stay under your calorie goal more often.";
                    }
                } else {
                    if ((7-noOfGoalsExceeded) > 3) {
                        feedback = "Well done! Keep it up.";
                    } else {
                        feedback = "Almost there... aim to exceed your calorie goal more often.";
                    }
                }

                if (losingWeight) {
                    MotivationText.setText("You've acheived your daily target of staying under " + theUI.getDailyCalories() + " calories " + noOfGoalsExceeded + "/7 times in the last week.\n \n" + feedback);
                } else {
                    MotivationText.setText("You've acheived your daily target of exceeding " + theUI.getDailyCalories() + " calories " + (7 - noOfGoalsExceeded) + "/7 times in the last week.\n \n" + feedback);
                }

                int mostCals = 0;
                int[] sortedItems = new int[items.length];
                sortedItems = items;
                if (!isWeightType) {
                    for (int i=0;i<sortedItems.length -1;i++) {
                        for (int j=0;j<sortedItems.length-i-1;j++) {
                            if (sortedItems[i] > sortedItems[i+1]) {
                                int temp = sortedItems[i];
                                sortedItems[i] = sortedItems[i+1];
                                sortedItems[i+1] = temp;
                            }
                        }
                    }
                    mostCals = sortedItems[sortedItems.length-1];
                    currentWeight.setText("The most calories you had in a single day is: " + mostCals);
                    currentWeight.setGravity(Gravity.CENTER_HORIZONTAL);
                }
            }
        });

        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        typeSpinner = (Spinner) getView().findViewById(R.id.changeType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.graphTypes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        typeSpinner.setAdapter(adapter);

        scaleSpinner = (Spinner) getView().findViewById(R.id.changeScale);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.graphScales, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        scaleSpinner.setAdapter(adapter2);

        graph = (GraphView) getView().findViewById(R.id.graph);

        ArrayList<Meal> graphPoints = theUI.graph(true, 7);

        int[] weights = new int[7];
        DataPoint[] dp = new DataPoint[7];
        for (int i = graphPoints.size()-1; i >= 0; i--) {
            weights[i] = graphPoints.get(i).getCalories();
            dp[graphPoints.size()-i-1] = new DataPoint(graphPoints.size()-i-1, weights[i]);
        }

        series = new LineGraphSeries<>(dp);

        int target = theUI.getProfile().getTargetWeight();
        series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, target),
                new DataPoint(6, target)
        });

        series2.setColor(Color.GREEN);

        graph.addSeries(series);
        graph.addSeries(series2);

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Days");

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(6);

        graph.getViewport().setXAxisBoundsManual(true);

        MotivationText = (TextView) getView().findViewById(R.id.motivationText);
        MotivationText.setVisibility(View.INVISIBLE);

    }
}