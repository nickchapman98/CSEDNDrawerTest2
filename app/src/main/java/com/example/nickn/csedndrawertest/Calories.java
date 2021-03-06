package com.example.nickn.csedndrawertest;//used for calculating calories needed to maintain, gain or loose weight and how many left to eat in a given week
//test

import java.io.*;
public class Calories {

    public static int maintain(User user){
        double bmr = BMR(user.getWeight(), user.getHeight(), user.getAge(), user.getSex());
        double caloriesPerDay = dailyIntake(1,bmr);
        return (int) caloriesPerDay;
    }

    public static double BMR(int weight, int height, int age, String sex){
        double BMR = 0;
        if (sex.contains("female")){
            BMR = 447.593 + (9.247*weight) + (3.098*height) - (4.330*age);
        }
        else if (sex.contains("male")){
            BMR = 88.362 + (13.397*weight) + (4.799*height) - (5.677*age);
        }
        else{
            //if they prefer not to put a gender then averages the 2
            BMR = 447.593 + (9.247*weight) + (3.098*height) - (4.330*age);
            BMR += 88.362 + (13.397*weight) + (4.799*height) - (5.677*age);
            BMR = BMR/2;
        }
        return BMR;
    }

    //using exercise multiplier passed as argument which is based on amount of exercise a person does
    public static double dailyIntake(int exercise, double BMR){
        return BMR*exercise;
    }


}
